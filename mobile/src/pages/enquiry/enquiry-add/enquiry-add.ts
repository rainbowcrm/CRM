import { Component, ViewChild } from '@angular/core';
import { NavController, ToastController} from 'ionic-angular';
import { DatePipe } from '@angular/common';
import { NewEnquiry, Enquiry, Code } from '../';
import { HTTPService, ReasonCodeProvider, SharedService } from '../../../providers/';
import { Storage } from '@ionic/storage';
import { HomePage } from '../../home/home';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-enquiry-add',
  templateUrl: 'enquiry-add.html'
})
export class EnquiryAddPage {
  private model:Enquiry;
  private newEnquiryObject: NewEnquiry;
  private errorMessage:string;
  private enquiryTypes: Array<any>;
  private enquirySources: Array<any>;

  constructor(public navCtrl: NavController,private http:HTTPService, private toastCtrl: ToastController,private sharedData: SharedService,
    private datePipe: DatePipe, private storage: Storage, private rcp: ReasonCodeProvider) {
      this.model = new Enquiry();
      this.model.Enquiry = ""; 
      this.model.EnquirySource = new Code();
      this.model.EnquiryType = new Code();
      this.newEnquiryObject = new NewEnquiry();
      this.rcp.finiteValueSource$.subscribe(res => {this.updateReasonCodes(res)});
      this.rcp.getFiniteValues();
    }

  updateReasonCodes(reasonCodes){
     this.enquiryTypes =  reasonCodes.ENQTYPE;
     this.enquirySources =  reasonCodes.ENQSRC;
  }

  ionViewDidEnter() {

     this.storage.ready().then(() => {
      this.storage.get('user').then(val => this.newEnquiryObject.user = val);
     });
  }

  goHome():void{
      this.navCtrl.setRoot(HomePage);
  }
  
  
  addEnquiry():void{
       this.errorMessage = null;
       this.model.EnqDate = this.datePipe.transform(new Date(),"yyyy-MM-dd");
       this.newEnquiryObject.Enquiry = this.model;
       this.http.processCustomUrlServerRequest("ajxService=createEnquiry","post",this.newEnquiryObject, false, false).subscribe(
                     res =>  {
                       this.expenseAddSuccess(res);
                      },
                     error =>  {this.expenseAddSuccess(error)});
  }

  expenseAddSuccess(response):void{
    if(response.result == "failure"){
       this.errorMessage = "Failed to create enquiry"; 
       return ;
    }
    this.goHome();
    this.showSuccessToast();
    
  }

  showSuccessToast():any{
     let toast = this.toastCtrl.create({
      message: 'Your enquiry submitted successfully',
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

  expenseAddError(error){
    this.errorMessage = "Failed to create enquiry";
  }
}
