import { Component } from '@angular/core';
import { NavController, ToastController , NavParams} from 'ionic-angular';
import { HTTPService, ReasonCodeProvider, SharedService } from '../../../providers/';
import { Storage } from '@ionic/storage';
import { HomePage } from '../../home/home';
import { SalesLeadSearchResult } from '../../sales-lead';
import { FollowUp, Result, Lead,ResultReason, ConfidenceLevel,CommunicationMode, CreateFollowUpRequest } from '../';
import { DatePipe } from '@angular/common';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-follow-up-add',
  templateUrl: 'follow-up-add.html'
})
export class FollowUpAddPage {
  private model:FollowUp;
  private errorMessage:string;
  private communicationModes: Array<CommunicationMode>;
  private results: Array<Result>;
  private resultReasons: Array<ResultReason>
  private confidenceLevels: Array<ConfidenceLevel>;  
  
  constructor(public navCtrl: NavController,private http:HTTPService, private toastCtrl: ToastController,
    private params: NavParams, private rcp: ReasonCodeProvider, private sharedData: SharedService,
    private storage: Storage, private datePipe: DatePipe) {
      this.model = new FollowUp();
      this.model.ResultReason = new ResultReason();
      this.model.Result = new Result();
      this.model.Lead = new Lead();
      this.model.ConfidenceLevel = new ConfidenceLevel();
      this.model.CommunicationMode = new CommunicationMode();
      this.rcp.finiteValueSource$.subscribe(res => {this.updateFiniteValues(res)});
      this.rcp.getFiniteValues();
      this.rcp.reasonCodeSource$.subscribe(res => {this.updateReasonCodes(res)});
      this.rcp.getReasonCodes("SLSLDREAS");
    }

  ionViewDidEnter() {
     let lead: any = this.sharedData.getData("lead");
     if(lead){
         this.model.Lead.DocNumber = lead.DocNumber;
         this.sharedData.removeData("lead");
     }
     this.storage.ready().then(() => {
        this.storage.get('division').then(val => this.model.Division = val);
        this.storage.get('user').then(val => this.model.SalesAssociate = val);
     });
  }

  updateFiniteValues(finiteValues){
     this.communicationModes = finiteValues.COMMMODE;
     this.results = finiteValues.FLPRESULT;
     this.confidenceLevels = finiteValues.CONFLEVEL;
  }

  updateReasonCodes(reasonCodes){
    this.resultReasons = reasonCodes;
  }

  goHome():void{
      this.navCtrl.setRoot(HomePage);
  }

  associateSalesLead(){
     this.navCtrl.push(SalesLeadSearchResult,{isAssociate: true});
  }

  createFollowUp(){
      this.model.FinalFollowup = this.model.FinalFollowup?"true":"false";
      this.model.FollowupDate = this.datePipe.transform(new Date(),"yyyy-MM-dd");
      this.model.NextFollwup = this.datePipe.transform(new Date(this.model.NextFollwup),"yyyy-MM-dd");
      let createFUReq = new CreateFollowUpRequest();
      createFUReq.currentmode = "CREATE";
      createFUReq.dataObject = this.model;
      createFUReq.fixedAction = "FixedAction.ACTION_CREATE";
      createFUReq.pageID = "newfollowup";
      this.http.processServerRequest("post",createFUReq, true).subscribe(
                     res => this.createFollowUpSuccess(res),
                     error =>  this.createFollowUpError(error)); 
  }

  createFollowUpSuccess(response):void{
    if(response.result == "failure"){
       this.errorMessage = "Failed to create new follow up"; 
       return ;
    }
    this.showSuccessToast();
    this.resetData();
  }

  private resetData(){
    this.model = new FollowUp();
    this.model.ResultReason = new ResultReason();
    this.model.Result = new Result();
    this.model.Lead = new Lead();
    this.model.ConfidenceLevel = new ConfidenceLevel();
    this.model.CommunicationMode = new CommunicationMode();
  }

  showSuccessToast():any{
     let toast = this.toastCtrl.create({
      message: 'Follow up was created successfully',
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

  createFollowUpError(error){
    this.errorMessage = "Failed to create new follow up";
  }

}
