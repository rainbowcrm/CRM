import { Component } from '@angular/core';
import { NavController, ToastController } from 'ionic-angular';

import { HomePage } from '../../home/home';
import { ContactAddPage, Contact, ContactSearchRequest, ContactSearchResponse, ContactListPage} from '../'
import { HTTPService, FilterProvider, AllFilter, FilterDetails, PromptService  } from '../../../providers/';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-contact-home',
  templateUrl: 'contact-home.html'
})
export class ContactHomePage {
  private filterName: string;
  private model:Contact = new Contact();
  private request: ContactSearchRequest;
  private response: ContactSearchResponse;
  private errorMessage:string;
  private availableFilters: Array<AllFilter>;
  constructor(public navCtrl: NavController, private toastCtrl: ToastController, private http: HTTPService,
              private filter: FilterProvider, private promptCtrl: PromptService) {
      this.filter.filtersForPage$.subscribe(res => {this.updateFilters(res)});
       this.filter.filtersDetails$.subscribe(res => {this.updateFilterValues(res)});
       this.filter.filtersSave$.subscribe(res => {this.updateFilterAfterSave()});
       this.promptCtrl.prompt$.subscribe(res => {this.saveFilterValue(res)});
       this.filterName = "0";
       this.filter.getAllFiltersForPage("com.rainbow.crm.contact.controller.ContactListController");
 }

  ionViewDidLoad() {
    console.log('Hello Contact Home Page');
  }

  goHome():void{
      this.navCtrl.setRoot(HomePage);
  }

  NoContactsFoundToast():any{
     let toast = this.toastCtrl.create({
      message: 'No contacts found',
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

  doSearch():void{
      this.errorMessage = null;
      this.request = new ContactSearchRequest();
      this.request.currentmode = 'READ';
      this.request.fixedAction = "FixedAction.NAV_FIRSTPAGE";
      this.request.pageID = "contacts";
      this.request.filter = this.getFilters();
      this.http.processServerRequest("post",this.request, true).subscribe(
                     res => this.contactSearchSuccess(res),
                     error =>  this.contactSearchError(error));  
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
  contactSearchSuccess(response):void{
    this.response = response;
    if(this.response.result == "failure"){
       this.errorMessage = "Failed to search contacts"; 
       return ;
    }
    if(this.response.dataObject.length == 0){
       this.NoContactsFoundToast();
       return ;
    }
    this.navCtrl.push(ContactListPage, {contacts:this.response.dataObject, 
                                          filter:this.request.filter, fetchedResults: this.response.fetchedRecords,
                                        numberOfResults:this.response.availableRecords});
  }
 

  contactSearchError(error){
    this.errorMessage = "Failed to search contacts";
  }
 
  addContact():void{
     this.navCtrl.push(ContactAddPage);
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
      this.filter.saveFilter( this.getFilters(),"contacts");
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
    this.model = new Contact();
    if(this.filterName && this.filterName != "0"){
      this.filter.getFilterDetails("com.rainbow.crm.contact.controller.ContactListController", this.filterName);
    }
  }

  onReset(){
    this.model = new Contact();
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
      this.filter.saveFilter( this.getFilters(),"contacts");
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
