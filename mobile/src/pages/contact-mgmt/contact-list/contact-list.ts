import { Component } from '@angular/core';
import { NavParams, NavController, ToastController, AlertController } from 'ionic-angular';
import { Contact, ContactSearchRequest, ContactSearchResponse } from '../';
import { HTTPService, SharedService } from '../../../providers/';
import { ContactService } from '../../../plugins/';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-contact-list',
  templateUrl: 'contact-list.html'
})
export class ContactListPage {
  private contacts:Array<Contact>;
  private request: ContactSearchRequest;
  private response: ContactSearchResponse;
  private filter: Array<Object>;
  private pageNumber: number;
  private fetchedResults: number;
  private numberOfResults: number;

  constructor(private params: NavParams,private http:HTTPService,
              private navCtrl: NavController,
              private toastCtrl: ToastController, private alertCtrl:AlertController,
              private contactService: ContactService, private sharedData: SharedService
             ) {
    this.contacts = this.params.get('contacts');
    this.filter = this.params.get('filter');
    this.pageNumber = 0;
    this.fetchedResults = this.params.get('fetchedResults');
    this.numberOfResults = this.params.get('numberOfResults');
  }

  ionViewDidLoad() {
     
  }

  getContactAddress(contact){
     let contactAddr = [];
     var keys = ["Address1","Address2","City","ZipCode","Landmark"];
     keys.forEach(function(value, key){
       if(contact[value] && contact[value].length > 0){
           contactAddr.push(contact[value])
         }
     })
     if(contactAddr.length == 0){
     return "no information available";
     }
    else{
      return contactAddr.join(" ,");
    }

  }
  callTel(tel): void{
            window.location.href = 'tel:'+ tel;
  }

  doSearchMoreContact(infiniteScroll) {
      if(this.fetchedResults >= this.numberOfResults ){
        infiniteScroll.complete();
        return;
      }
      this.request = new ContactSearchRequest();
      this.request.currentmode = 'READ';
      this.request.fixedAction = "FixedAction.NAV_NEXTPAGE";
      this.request.hdnPage = ++this.pageNumber;
      this.request.pageID = "contacts";
      this.request.filter = this.filter;
      this.http.processServerRequest("post",this.request, true, true).subscribe(
                     res => this.contactSearchSuccess(res, infiniteScroll),
                     error =>  this.contactSearchError(error, infiniteScroll)); 
  }

  contactSearchSuccess(response, infiniteScroll):void{
    this.response = response;
    if(this.response.result == "failure"){
       infiniteScroll.complete();
       return ;
    }
    if(this.response.dataObject.length == 0){
      infiniteScroll.complete();
       return ;
    }
    this.contacts = this.contacts.concat(this.response.dataObject);
    this.fetchedResults += this.response.fetchedRecords;
    infiniteScroll.complete();
  }
 

  contactSearchError(error,infiniteScroll){
    infiniteScroll.complete();
    this.http.setAuthToken(null);
  }

  saveContact(contact: Contact){
     this.contactService.saveContact(contact);
  }

}
