import { Component } from '@angular/core';
import { ViewController, NavParams, ToastController } from 'ionic-angular';
import { PortfolioTypeRequest } from '../';
import { HTTPService } from '../../../providers/';

/*
  Generated class for the Reason code page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-discussion-brand-modal',
  templateUrl: 'discussion-brand-modal.html'
})
export class DiscussionBrandModalPage {
  private items: any;
  private type: string;
  private request: PortfolioTypeRequest;
  constructor(public viewCtrl: ViewController, private navParam: NavParams, 
  private http: HTTPService, private toastCtrl: ToastController) {
    this.type = this.navParam.get("type");
  }

   ionViewDidEnter(){
    this.request = new PortfolioTypeRequest();
    this.request.lookupType = "salesportfoliokey";//dont know what is this????
    this.request.additionalParam = this.type;
    this.http.processCustomUrlServerRequest("currentpage=Lookup","post",this.request, true).subscribe(
                     res =>  {
                       this.processResponse(res);
                      },
                     error =>  {
                       this.processError(error);
                     });
  }

  processResponse(res){
     this.items = res.dataObject;
  }
  processError(error){
    this.showErrorToast();
  }

  onOptionSelected(i){
    this.viewCtrl.dismiss(this.items[i]);
  }

  showErrorToast():any{
     let toast = this.toastCtrl.create({
      message: 'Unable to fetch lists',
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

  onDismiss():void{
    this.viewCtrl.dismiss();
  }

}
