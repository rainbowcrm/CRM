import { Component } from '@angular/core';
import { NavController, ToastController , NavParams, ModalController} from 'ionic-angular';

import { NewDiscussion, CreateDiscussionRequest, Topic, PortFolioType, DiscussionBrandModalPage } from '../';
import { HTTPService, ReasonCodeProvider } from '../../../providers/';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-discussion-add',
  templateUrl: 'discussion-add.html'
})
export class DiscussionAddPage {
  private model:NewDiscussion;
  private response: CreateDiscussionRequest;
  private errorMessage:string;
  private reasonCodes: Array<any>;

  constructor(public navCtrl: NavController,private http:HTTPService, private toastCtrl: ToastController,
    private params: NavParams,  private rcp: ReasonCodeProvider, private modalCtrl: ModalController) {
      this.model = new NewDiscussion();
      this.model.NewTopic = new Topic();
      this.model.NewTopic.PortfolioType = new PortFolioType();
      this.rcp.reasonCodeSource$.subscribe(res => {this.updateReasonCodes(res)});
      this.rcp.getReasonCode();
    }

  updateReasonCodes(reasonCodes){
     this.reasonCodes =  reasonCodes.SPFTYPE;
  }
  ionViewDidLoad() {
    console.log('Hello ContactAdd Page');
  }
  
  fetchBrands(){
   let modal = this.modalCtrl.create(DiscussionBrandModalPage,{type:this.model.NewTopic.PortfolioType.Code});
   modal.present();
  }
  /*addContact():void{
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
    if(response.result == "failure"){
       this.errorMessage = "Failed to create contact"; 
       return ;
    }
    this.response = response.dataObject;
    this.navCtrl.popToRoot();
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
    this.errorMessage = "Failed to create contact";
  }*/

}
