import { Component } from '@angular/core';
import { NavController, ToastController } from 'ionic-angular';

import { HomePage } from '../../home/home';
import { Alert, AlertSearchRequest, Owner, AlertListPage } from '../'
import * as _ from 'lodash';
import { HTTPService, ReasonCodeProvider,  FilterProvider, AllFilter, FilterDetails, PromptService } from '../../../providers/';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-alerts-home',
  templateUrl: 'alerts-home.html'
})
export class AlertsHomePage {
  private filterName: string;
  private model: Alert;
  private errorMessage:string;
  private request: AlertSearchRequest;
  private typeReasonCodes: Array<any>;
  private statusReasonCodes: Array<any>;
  private availableFilters: Array<AllFilter>;

  constructor(public navCtrl: NavController, private toastCtrl: ToastController
  , private http: HTTPService, private rcp: ReasonCodeProvider,
  private filter: FilterProvider, private promptCtrl: PromptService) {
     this.model = new Alert();
     this.model.Owner = new Owner();
     this.rcp.finiteValueSource$.subscribe(res => {this.updateReasonCodes(res)});
     this.rcp.getFiniteValues();
     this.filter.filtersForPage$.subscribe(res => {this.updateFilters(res)});
     this.filter.filtersDetails$.subscribe(res => {this.updateFilterValues(res)});
     this.filter.filtersSave$.subscribe(res => {this.updateFilterAfterSave()});
     this.promptCtrl.prompt$.subscribe(res => {this.saveFilterValue(res)});       
     this.filterName = "0";
     this.filter.getAllFiltersForPage("com.rainbow.crm.alert.controller.AlertListController");
  }

  updateReasonCodes(reasonCodes){
     this.typeReasonCodes =  reasonCodes.ALTTYPE;
     this.statusReasonCodes =  reasonCodes.ALTSTATUS;
  }

  ionViewDidLoad() {
  }

  goHome():void{
      this.navCtrl.setRoot(HomePage);
  }

  showToast(msg: string):any{
     let toast = this.toastCtrl.create({
      message: msg,
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

  doSearch():void{
      this.errorMessage = null;
      this.request = new AlertSearchRequest();
      this.request.currentmode = 'READ';
      this.request.fixedAction = "FixedAction.NAV_FIRSTPAGE";
      this.request.pageID = "alerts";
      this.request.filter = this.getFilters();
      this.http.processServerRequest("post",this.request, true).subscribe(
                     res => this.alertSearchSuccess(res),
                     error =>  this.alertSearchError(error));  
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
  alertSearchSuccess(response):void{
    if(response.result == "failure"){
       this.errorMessage = "Failed to search Alerts"; 
       return ;
    }
    if(response.dataObject.length == 0){
       this.showToast("No Results found");
       return ;
    }
    this.navCtrl.push(AlertListPage, {alerts:response.dataObject, 
                                          filter:this.request.filter, fetchedResults: response.fetchedRecords,
                                        numberOfResults:response.availableRecords});
  }
 

  alertSearchError(error){
    this.errorMessage = "Failed to search Alerts";
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
      this.filter.saveFilter( this.getFilters(),"alerts");
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
    this.model = new Alert();
    this.model.Owner = new Owner();
    if(this.filterName && this.filterName != "0"){
      this.filter.getFilterDetails("com.rainbow.crm.alert.controller.AlertListController", this.filterName);
    }
  }

  onReset(){
    this.model = new Alert();
    this.model.Owner = new Owner();
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
      this.filter.saveFilter( this.getFilters(),"alerts");
    }
  }

}
