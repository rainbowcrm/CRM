import { Component } from '@angular/core';
import { NavParams, NavController, ToastController, AlertController, PopoverController } from 'ionic-angular';
import { Alert, AlertSearchRequest, AcknowledgeAlertRequest } from '../';
import { HTTPService, SharedService } from '../../../providers/';
import { ContactService } from '../../../plugins/';
import { SortPopOverPage }   from '../../../common/sort-helper/sort-popover';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-alerts-list',
  templateUrl: 'alerts-list.html'
})
export class AlertListPage {
  private alerts:Array<Alert>;
  private request: AlertSearchRequest;
  private filter: Array<Object>;
  private pageNumber: number;
  private fetchedResults: number;
  private numberOfResults: number;
  private sortCondition: any;

  constructor(private params: NavParams,private http:HTTPService,
              private navCtrl: NavController,
              private toastCtrl: ToastController, private alertCtrl:AlertController,
              private contactService: ContactService,
              private popoverCtrl: PopoverController
             ) {
    this.alerts = this.params.get('alerts');
    this.filter = this.params.get('filter');
    this.pageNumber = 0;
    this.fetchedResults = this.params.get('fetchedResults');
    this.numberOfResults = this.params.get('numberOfResults');
  }

  onSort(){
    var sortType = [{key: "type", value: "Type"},
                    {key: "owner", value: "Owner"},
                    {key: "status", value: "Status"}]
    let popover = this.popoverCtrl.create(SortPopOverPage, {sortConditions: sortType});
    popover.present({});
    popover.onDidDismiss(this.dismissSortPopover.bind(this))
  }

  showToast(msg: string):any{
     let toast = this.toastCtrl.create({
      message: msg,
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

  dismissSortPopover(data){
    if(data){
      this.sortCondition = {};
      this.sortCondition.sortCondt = data.selectedValue;
      this.sortCondition.isAscending = data.isAscending;
      this.pageNumber = -1;
      this.fetchedResults = 0;
      this.alerts = [];
      this.doSearchMoreAlerts({complete:function(){}});
    }
  }

  viewDetails(url){
    this.showToast("Not supported yet");
  }

  doSearchMoreAlerts(infiniteScroll) {
      if(this.fetchedResults >= this.numberOfResults ){
        infiniteScroll.complete();
        return;
      }
      this.request = new AlertSearchRequest();
      this.request.currentmode = 'READ';
      if(this.pageNumber < 0){
        this.request.fixedAction = "FixedAction.NAV_FIRSTPAGE";
      }else{
        this.request.fixedAction = "FixedAction.NAV_NEXTPAGE";
      }
      if(this.sortCondition){
        this.request.rds_sortdirection = this.sortCondition.isAscending ? "ASCENDING":"DESCENDING";
        this.request.rds_sortfield = this.sortCondition.sortCondt.key;
      }
      this.request.hdnPage = ++this.pageNumber;
      this.request.pageID = "alerts";
      this.request.filter = this.filter;
      this.http.processServerRequest("post",this.request, true, this.pageNumber != 0).subscribe(
                     res => this.alertSearchSuccess(res, infiniteScroll),
                     error =>  this.alertSearchError(error, infiniteScroll)); 
  }

  alertSearchSuccess(response, infiniteScroll):void{
    if(response.result == "failure"){
       infiniteScroll.complete();
       return ;
    }
    if(response.dataObject.length == 0){
      infiniteScroll.complete();
       return ;
    }
    this.alerts = this.alerts.concat(response.dataObject);
    this.fetchedResults += response.fetchedRecords;
    infiniteScroll.complete();
  }
 

  alertSearchError(error,infiniteScroll){
    infiniteScroll.complete();
  }

  acknowledge(alert:Alert, index: number){
      let request = new AcknowledgeAlertRequest();
      request.submitAction = "acknowledge";
      request.currentmode = 'READ';
      request.hdnPage = 0;
      request.pageID = "alerts";
      request.rds_selectedids = alert.Id;

      this.http.processServerRequest("post",request, true).subscribe(
                     res => {this.showToast("Alert acknowledge");
                             this.alerts.splice(index, 1);                           
                            },
                     error =>  this.showToast("Failed to acknowledge. Kindly retry again")); 
  }

}
