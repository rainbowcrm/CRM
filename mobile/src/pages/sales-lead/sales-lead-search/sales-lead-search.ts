import { Component } from '@angular/core';
import { NavController, ToastController, NavParams } from 'ionic-angular';
import { SalesLeadSearchRequest, SalesLeadSearchFilter, SalesLeadSearchResult, ContextParameters } from '../';
import { HTTPService } from '../../../providers/';
import { HomePage } from '../../home/home';

/*
  Generated class for the Sales Lead page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-search-lead-search',
  templateUrl: 'sales-lead-search.html'
})
export class SalesLeadSearch {
  private searchString:string;
  private request: SalesLeadSearchRequest;
  private response: any;
  private errorMessage:string;
  private isAssociateItems: Boolean;
  

  constructor(public navCtrl: NavController, private http:HTTPService
  ,  private toastCtrl: ToastController) {
  }


  goHome():void{
      this.navCtrl.setRoot(HomePage);
  }

  NoSalesLeadFoundToast():any{
     let toast = this.toastCtrl.create({
      message: 'No Leads found',
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

  done(){
    this.navCtrl.popToRoot();
  }

  doSearch():void{
      this.errorMessage = null;
      this.request = new SalesLeadSearchRequest();
      this.request.currentmode = 'READ';
      this.request.fixedAction = "FixedAction.NAV_FIRSTPAGE";
      this.request.pageID = "saleslead";
      this.request.filter = [];
      this.request.hdnPage = 0;
      let filter = new SalesLeadSearchFilter();
      filter.field = "status.description";
      filter.operator = "EQUALS";
      filter.value = this.searchString;
      this.request.filter.push(filter);
      let context = new ContextParameters();
      context.workableleads = "true";
      this.request.contextParameters = context;

      this.http.processServerRequest("post",this.request, true).subscribe(
                     res => this.salesLeadSearchSuccess(res, this.request.filter.slice(0)),
                     error =>  this.salesLeadSearchError(error));  
  }


  salesLeadSearchSuccess(response, filter):void{
    this.response = response;
    if(this.response.result == "failure"){
       this.errorMessage = "Failed to search leads"; 
       return ;
    }
    if(this.response.dataObject.length == 0){
       this.NoSalesLeadFoundToast();
       return ;
    }
    this.navCtrl.push(SalesLeadSearchResult, {leads:this.response.dataObject, filter:filter,
      fetchedResults: this.response.fetchedRecords, numberOfResults:this.response.availableRecords});
  }
 

  salesLeadSearchError(error){
    this.http.setAuthToken(null);
    this.errorMessage = "Failed to search leads";
  }

}
