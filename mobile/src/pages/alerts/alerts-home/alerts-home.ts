import { Component } from '@angular/core';
import { NavController, ToastController } from 'ionic-angular';

import { HomePage } from '../../home/home';
import { Alert, AlertSearchRequest, Owner, AlertListPage } from '../'
import { HTTPService, ReasonCodeProvider } from '../../../providers/';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-alerts-home',
  templateUrl: 'alerts-home.html'
})
export class AlertsHomePage {
  private filterName: string;
  private model: Alert;
  private errorMessage:string;
  private request: AlertSearchRequest;
  private typeReasonCodes: Array<any>;
  private statusReasonCodes: Array<any>;

  constructor(public navCtrl: NavController, private toastCtrl: ToastController
  , private http: HTTPService, private rcp: ReasonCodeProvider) {
     this.model = new Alert();
     this.model.Owner = new Owner();
     this.rcp.reasonCodeSource$.subscribe(res => {this.updateReasonCodes(res)});
     this.rcp.getReasonCode();
  }

  updateReasonCodes(reasonCodes){
     this.typeReasonCodes =  reasonCodes.ALTTYPE;
     this.statusReasonCodes =  reasonCodes.ALTSTATUS;
  }

  ionViewDidLoad() {
  }

  goHome():void{
      this.navCtrl.setRoot(HomePage);
  }

  showToast(msg: string):any{
     let toast = this.toastCtrl.create({
      message: msg,
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

  doSearch():void{
      this.errorMessage = null;
      this.request = new AlertSearchRequest();
      this.request.currentmode = 'READ';
      this.request.fixedAction = "FixedAction.NAV_FIRSTPAGE";
      this.request.pageID = "alerts";
      this.request.filter = this.getFilters();
      this.http.processServerRequest("post",this.request, true).subscribe(
                     res => this.alertSearchSuccess(res),
                     error =>  this.alertSearchError(error));  
  }

  getFilters():Array<any>{
      let filters = [];
      for(let data in this.model){
        if(this.model[data] && typeof this.model[data] == "string" && this.model[data].length > 0){
          filters.push({
	      	  "field" :data ,
	   	      "operator" :"EQUALS",
	   	      "value":this.model[data]
	         });
        }
        else if(this.model[data] && Object.keys(this.model[data]).length > 0){
           var subFilters = this.getSubFilters(data, this.model[data]);
           if(subFilters.length > 0)
             filters = filters.concat(subFilters);
        }
      }
      return filters;
  }

  getSubFilters(key,model):Array<any>{
     let filters = [];
     for(let data in model){
        if(model[data] && model[data].length > 0){
          filters.push({
	      	  "field" :key+"."+data ,
	   	      "operator" :"EQUALS",
	   	      "value":model[data]
	         });
        }
      }
     return filters;
  }
  alertSearchSuccess(response):void{
    if(response.result == "failure"){
       this.errorMessage = "Failed to search contacts"; 
       return ;
    }
    if(response.dataObject.length == 0){
       this.showToast("No Results found");
       return ;
    }
    this.navCtrl.push(AlertListPage, {alerts:response.dataObject, 
                                          filter:this.request.filter, fetchedResults: response.fetchedRecords,
                                        numberOfResults:response.availableRecords});
  }
 

  alertSearchError(error){
    this.http.setAuthToken(null);
    this.errorMessage = "Failed to search contacts";
  }

}
