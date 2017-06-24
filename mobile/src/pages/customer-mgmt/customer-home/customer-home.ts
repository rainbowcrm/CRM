import { Component } from '@angular/core';
import { NavController, ToastController, NavParams, AlertController } from 'ionic-angular';

import { Customer, CustomerSearchRequest, CustomerSearchResponse } from '../';
import { HomePage } from '../../home/home';
import { CustomerAddPage, CustomerListPage} from '../'
import { HTTPService, FilterProvider, AllFilter, FilterDetails, PromptService } from '../../../providers/';

/*
  Generated class for the CustomerHome page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-customer-home',
  templateUrl: 'customer-home.html'
})
export class CustomerHomePage {
  private filterName: string;
  private model:Customer = new Customer();
  private request: CustomerSearchRequest;
  private response: CustomerSearchResponse;
  private errorMessage:string;
  private isAssociateCustomer: Boolean;
  private availableFilters: Array<AllFilter>;
  constructor(public navCtrl: NavController,private http:HTTPService,  private toastCtrl: ToastController
             ,private params: NavParams, private filter: FilterProvider, private promptCtrl: PromptService) {
       this.isAssociateCustomer = this.params.get('isAssociateCustomer'); 
       this.filter.filtersForPage$.subscribe(res => {this.updateFilters(res)});
       this.filter.filtersDetails$.subscribe(res => {this.updateFilterValues(res)});
       this.filter.filtersSave$.subscribe(res => {this.updateFilterAfterSave()});
       this.promptCtrl.prompt$.subscribe(res => {this.saveFilterValue(res)});
       this.filterName = "0";
       this.filter.getAllFiltersForPage("com.rainbow.crm.customer.controller.CustomerListController");  
  }

  updateFilters(res: Array<AllFilter>){
    this.availableFilters = res;
  }

  updateFilterValues(res: Array<FilterDetails>){
     for(let i=0; i< res.filter.length; i++){
       this.model[res.filter[i].field] = res.filter[i].value;
     }
  }

  saveFilterValue(filterName: string){
    if(filterName.length == 0){
      return;
    }else{
      this.filterName = filterName;
      this.model.FilterName = this.filterName;
      this.filter.saveFilter( this.getFilters(),"customers");
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
    this.model = new Customer();
    if(this.filterName && this.filterName != "0"){
      this.filter.getFilterDetails("com.rainbow.crm.customer.controller.CustomerListController", this.filterName);
    }
  }

  onReset(){
    this.model = new Customer();
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
      this.filter.saveFilter( this.getFilters(),"customers");
    }
  }

  ionViewDidLoad() {
    console.log('Hello CustomerHome Page');
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
      this.request = new CustomerSearchRequest();
      this.request.currentmode = 'READ';
      this.request.fixedAction = "FixedAction.NAV_FIRSTPAGE";
      this.request.pageID = "customers";
      this.request.filter = this.getFilters();
      this.http.processServerRequest("post",this.request, true).subscribe(
                     res => this.customerSearchSuccess(res),
                     error =>  this.customerSearchError(error));  
  }

  getFilters():Array<any>{
      let filters = [];
      for(let data in this.model){
        if(this.model[data])
           filters.push({
	      	  "field" :data ,
	   	      "operator" :"EQUALS",
	   	      "value":this.model[data]
	         });
      }
      return filters;
  }
  customerSearchSuccess(response):void{
    this.response = response;
    if(this.response.result == "failure"){
       this.errorMessage = "Failed to search customer"; 
       return ;
    }
    if(this.response.dataObject.length == 0){
       this.showToast("No customers found");
       return ;
    }
    this.navCtrl.push(CustomerListPage, {customers:this.response.dataObject, 
                                          filter:this.request.filter, fetchedResults: this.response.fetchedRecords,
                                        numberOfResults:this.response.availableRecords, isAssociateCustomer: this.isAssociateCustomer});
  }
 

  customerSearchError(error){
    this.errorMessage = "Failed to search customer";
  }
  addCustomer():void{
     this.navCtrl.push(CustomerAddPage);
  }

}
