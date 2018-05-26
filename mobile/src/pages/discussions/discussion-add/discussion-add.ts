import { Component } from '@angular/core';
import { NavController, ToastController , NavParams, ModalController} from 'ionic-angular';
import { DatePipe } from '@angular/common';
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
  private errorMessage:string;
  private reasonCodes: Array<any>;

  constructor(public navCtrl: NavController,private http:HTTPService, private toastCtrl: ToastController,
    private params: NavParams,  private rcp: ReasonCodeProvider, private modalCtrl: ModalController,
    private datePipe: DatePipe) {
      this.model = new NewDiscussion();
      this.model.NewTopic = new Topic();
      this.model.NewTopic.PortfolioType = new PortFolioType();
      this.rcp.finiteValueSource$.subscribe(res => {this.updateReasonCodes(res)});
      this.rcp.getFiniteValues(true);
    }

  updateReasonCodes(reasonCodes){
     this.reasonCodes =  reasonCodes.SPFTYPE;
  }
  ionViewDidLoad() {
    console.log('Hello ContactAdd Page');
  }
  
  fetchBrands(){
   let modal = this.modalCtrl.create(DiscussionBrandModalPage,{type:this.model.NewTopic.PortfolioType.Code});
   modal.onDidDismiss(data => {
     if(data && data.value)
       this.model.NewTopic.PortfolioValue = data.value;
   });
   modal.present();
  }
  addDiscussion():void{
    this.errorMessage = null;
    let addTopicReq = new CreateDiscussionRequest();
    addTopicReq.submitAction = "newTopic";
    addTopicReq.pageID = 'topic';
    addTopicReq.currentmode = "CREATE";
    this.model.NewTopic.TopicDate = this.datePipe.transform(new Date(),"yyyy-MM-dd");
    addTopicReq.dataObject = this.model;
    this.http.processServerRequest("post",addTopicReq, true).subscribe(
                     res => this.discussionAddSuccess(res),
                     error =>  this.discussionAddError(error));  
  }

  discussionAddSuccess(response):void{
    if(response.result == "failure"){
       this.errorMessage = "Failed to create topic"; 
       return ;
    }
    this.navCtrl.popToRoot();
    this.showSuccessToast();
    
  }

  showSuccessToast():any{
     let toast = this.toastCtrl.create({
      message: 'New topic was added successfully',
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

  discussionAddError(error){
    this.errorMessage = "Failed to create new topic";
  }

}
