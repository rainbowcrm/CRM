import { Component } from '@angular/core';
import { NavController, ToastController, NavParams } from 'ionic-angular';
import { SalesLeadSearchRequest, SalesLeadSearchFilter, SalesLeadSearchResult,Customer, ContextParameters, SalesLeads, Status } from '../';
import { HTTPService,  FilterProvider, AllFilter, FilterDetails, PromptService, ReasonCodeProvider } from '../../../providers/';
import { HomePage } from '../../home/home';
import * as _ from 'lodash';

/*
  Generated class for the Sales Lead page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-search-lead-search',
  templateUrl: 'sales-lead-search.html'
})
export class SalesLeadSearch {
  private searchString:string;
  private request: SalesLeadSearchRequest;
  private response: any;
  private errorMessage:string;
  private availableFilters: Array<AllFilter>;
  private filterName: string;
  private model: SalesLeads;
  private reasonCodes: Array<any>;
  

  constructor(public navCtrl: NavController, private http:HTTPService
  ,  private toastCtrl: ToastController, private filter: FilterProvider, 
     private promptCtrl: PromptService, private rcp: ReasonCodeProvider) {
     this.model = new SalesLeads();
     this.model.Status = new Status();
     this.model.Customer = new Customer();
     this.rcp.finiteValueSource$.subscribe(res => {this.updateReasonCodes(res)});
     this.rcp.getFiniteValues(true);
     this.filter.filtersForPage$.subscribe(res => {this.updateFilters(res)});
     this.filter.filtersDetails$.subscribe(res => {this.updateFilterValues(res)});
     this.filter.filtersSave$.subscribe(res => {this.updateFilterAfterSave()});
     this.promptCtrl.prompt$.subscribe(res => {this.saveFilterValue(res)});       
     this.filterName = "0";
     this.filter.getAllFiltersForPage("com.rainbow.crm.saleslead.controller.SalesLeadListController");
  }

  updateReasonCodes(reasonCodes){
     this.reasonCodes =  reasonCodes.SLCYCSTS;
  }

  goHome():void{
      this.navCtrl.setRoot(HomePage);
  }

  NoSalesLeadFoundToast():any{
     let toast = this.toastCtrl.create({
      message: 'No Leads found',
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

  done(){
    this.navCtrl.popToRoot();
  }

  doSearch():void{
      this.errorMessage = null;
      this.request = new SalesLeadSearchRequest();
      this.request.currentmode = 'READ';
      this.request.fixedAction = "FixedAction.NAV_FIRSTPAGE";
      this.request.pageID = "saleslead";
      this.request.filter = this.getFilters();
      this.request.hdnPage = 0;
      let context = new ContextParameters();
      context.workableleads = "true";
      this.request.contextParameters = context;
      this.http.processServerRequest("post",this.request, true).subscribe(
                     res => this.salesLeadSearchSuccess(res, this.request.filter.slice(0)),
                     error =>  this.salesLeadSearchError(error));  
  }


  salesLeadSearchSuccess(response, filter):void{
    this.response = response;
    if(this.response.result == "failure"){
       this.errorMessage = "Failed to search leads"; 
       return ;
    }
    if(this.response.dataObject.length == 0){
       this.NoSalesLeadFoundToast();
       return ;
    }
    this.navCtrl.push(SalesLeadSearchResult, {leads:this.response.dataObject, filter:filter,
      fetchedResults: this.response.fetchedRecords, numberOfResults:this.response.availableRecords});
  }
 

  salesLeadSearchError(error){
    this.errorMessage = "Failed to search leads";
  }

  getFilters():Array<any>{
      let filters = [];
      for(let data in this.model){
        if(this.model[data] && typeof this.model[data] == "string" && this.model[data].length > 0){
          filters.push({
	      	  "field" :data ,
	   	      "operator" :"EQUALS",
	   	      "value":this.model[data]
	         });
        }
        else if(this.model[data] && Object.keys(this.model[data]).length > 0){
           var subFilters = this.getSubFilters(data, this.model[data]);
           if(subFilters.length > 0)
             filters = filters.concat(subFilters);
        }
      }
      return filters;
  }

  getSubFilters(key,model):Array<any>{
     let filters = [];
     for(let data in model){
        if(model[data] && model[data].length > 0){
          filters.push({
	      	  "field" :key+"."+data ,
	   	      "operator" :"EQUALS",
	   	      "value":model[data]
	         });
        }
      }
     return filters;
  }

  updateFilters(res: Array<AllFilter>){
    this.availableFilters = res;
  }

  updateFilterValues(res: Array<FilterDetails>){
     for(let i=0; i< res.filter.length; i++){
       _.set(this.model,res.filter[i].field, res.filter[i].value);
     }
  }

  saveFilterValue(filterName: string){
    if(filterName.length == 0){
      return;
    }else{
      this.filterName = filterName;
      this.model.FilterName = this.filterName;
      this.filter.saveFilter( this.getFilters(),"saleslead");
    }
  }

  updateFilterAfterSave(){
    let newFilter = new AllFilter();
    if(this.isFilterExist(this.filterName)){
      return;
    }
    newFilter.filterId = this.filterName;
    newFilter.filterValue = this.filterName;
    this.availableFilters.push(newFilter);
  }

  isFilterExist(filterName: string): boolean{
    let filterExists = false;
    for(let i=0; i<this.availableFilters.length ; i++){
      if(this.availableFilters[i].filterValue == filterName){
        filterExists = true;
        break;
      }
    }
    return filterExists;
  }

  fetchFilterValues(){
    this.model = new SalesLeads();
    this.model.Status = new Status();
    this.model.Customer = new Customer();
    if(this.filterName && this.filterName != "0"){
      this.filter.getFilterDetails("com.rainbow.crm.saleslead.controller.SalesLeadListController", this.filterName);
    }
  }

  onReset(){
    this.model = new SalesLeads();
    this.model.Status = new Status();
    this.model.Customer = new Customer();
    this.filterName = "0";
  }

  onAdd(){
    if(!this.filterName){
       this.showToast("Please choose proper filter from drop down");
    }else if( this.filterName == "0"){
      let prompt = this.promptCtrl.displayPrompt("Filter Name","Choose a filter name","OK");
      prompt.present();
    }else{
      this.model.FilterName = this.filterName;
      this.filter.saveFilter( this.getFilters(),"saleslead");
    }
  }

  showToast(msg: string):any{
     let toast = this.toastCtrl.create({
      message: msg,
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

}
