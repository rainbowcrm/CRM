import { Component } from '@angular/core';
import { NavController, ToastController , NavParams} from 'ionic-angular';

import { Contact, ContactAddRequest, ContactAddResponse, ContactType } from '../';
import { HTTPService, Loader } from '../../../providers/';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-contact-add',
  templateUrl: 'contact-add.html'
})
export class ContactAddPage {
  private model:Contact;
  private response: ContactAddResponse;
  private errorMessage:string;

  constructor(public navCtrl: NavController,private http:HTTPService,
    private loader:Loader, private toastCtrl: ToastController,
    private params: NavParams) {
      this.model = new Contact();
      this.model.ContactType = new ContactType();
    }

  ionViewDidLoad() {
    console.log('Hello ContactAdd Page');
  }

  addContact():void{
    this.loader.presentLoader();
    this.errorMessage = null;
    let addContactReq = new ContactAddRequest();
    addContactReq.fixedAction = "FixedAction.ACTION_CREATE";
    addContactReq.pageID = 'newcontact';
    addContactReq.currentmode = "CREATE";
    addContactReq.dataObject = this.model;
    this.http.processServerRequest("post",addContactReq, true).subscribe(
                     res => this.contactAddSuccess(res),
                     error =>  this.contactAddError(error));  
  }

  contactAddSuccess(response):void{
    this.loader.dismissLoader();
    if(response.result == "failure"){
       this.errorMessage = "Failed to create contact"; 
       return ;
    }
    this.response = response.dataObject;
    this.showSuccessToast();
    
  }

  showSuccessToast():any{
     let toast = this.toastCtrl.create({
      message: 'Contact was added successfully',
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

  contactAddError(error){
    this.http.setAuthToken(null);
    this.loader.dismissLoader();
    this.errorMessage = "Failed to create contact";
  }

}
