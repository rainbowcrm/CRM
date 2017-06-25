import { Component, ViewChild } from '@angular/core';
import { NavParams, Slides, ToastController, NavController } from 'ionic-angular';
import { SalesLeads, SalesLeadEmailRequest} from '../';
import { Storage } from '@ionic/storage';
import { HTTPService } from '../../../providers/';


/*
  Generated class for the ItemDetails page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-sales-lead-details',
  templateUrl: 'sales-lead-details.html'
})
export class SalesLeadDetails {
  private lead:SalesLeads;
  private request: SalesLeadEmailRequest;

  constructor(private params: NavParams,private navCtrl: NavController, private http: HTTPService,
              private toastCtrl: ToastController) {
    this.lead = this.params.get('lead');
  }

  sendMail(){
      this.request = new SalesLeadEmailRequest();
      this.request.currentmode = 'READ';
      this.request.fixedAction = "FixedAction.NAV_FIRSTPAGE";
      this.request.hdnPage = 0;
      this.request.pageID = "saleslead";
      this.request.submitAction = "sendemail";
      this.request.rds_selectedids = this.lead.Id;
      this.http.processServerRequest("post",this.request, true).subscribe(
                     res => this.salesLeadEmailSuccess(res),
                     error =>  this.salesLeadEmailError(error)); 
  }

  salesLeadEmailSuccess(response):void{
    if(response.result == "failure"){
       this.emailFailed();
       return ;
    }
    if(response.dataObject.length == 0){
       this.emailFailed();
       return ;
    }
    let toast = this.toastCtrl.create({
      message: 'Email sent',
      duration: 2000,
      position: 'top'
     });
    toast.present();
    
  }

  emailFailed():any{
     let toast = this.toastCtrl.create({
      message: 'Failed to send mail',
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }
 

  salesLeadEmailError(error){
    this.emailFailed();
  }

  
 

}
