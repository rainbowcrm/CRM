import { Component } from '@angular/core';
import { NavController, ToastController } from 'ionic-angular';

import { HomePage } from '../../home/home';
import { ContactAddPage, Contact, ContactSearchRequest, ContactSearchResponse, ContactListPage} from '../'
import { HTTPService } from '../../../providers/';

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
  constructor(public navCtrl: NavController, private toastCtrl: ToastController, private http: HTTPService) {}

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
    this.http.setAuthToken(null);
    this.errorMessage = "Failed to search contacts";
  }
 
  addContact():void{
     this.navCtrl.push(ContactAddPage);
  }

}
