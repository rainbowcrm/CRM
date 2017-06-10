import { Component } from '@angular/core';
import { NavParams, NavController, ToastController, PopoverController } from 'ionic-angular';

import { HTTPService } from '../../../providers/';
import { SalesLeads, SalesLeadSearchRequest, SalesLeadSearchResponse, SalesLeadDetails, ContextParameters } from '../';
import { SortPopOverPage }   from '../../../common/sort-helper/sort-popover';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-sales-lead-search-result',
  templateUrl: 'sales-lead-search-result.html'
})
export class SalesLeadSearchResult {
  private leads:Array<SalesLeads>;
  private request: SalesLeadSearchRequest;
  private response: SalesLeadSearchResponse;
  private pageNumber: number;
  private fetchedResults: number;
  private numberOfResults: number;
  private filter:Array<Object>;
  private sortCondition: any;

  constructor(private params: NavParams,private http:HTTPService,
              private navCtrl: NavController, private toastCtrl: ToastController,
              private popoverCtrl: PopoverController) {
    this.leads = this.params.get('leads');  
    this.filter = this.params.get('filter');
    this.pageNumber = 0;
    this.fetchedResults = this.params.get('fetchedResults');
    this.numberOfResults = this.params.get('numberOfResults');
  }

  ionViewDidLoad() {
     
  }

  onSort(){
    var sortType = [{key: "docNumber", value: "Doc Number"},
                    {key: "refDate", value: "Date"},
                    {key: "customer.phone", value: "Phone"},
                    {key:"status", value:"Status"}]
    let popover = this.popoverCtrl.create(SortPopOverPage, {sortConditions: sortType});
    popover.present({});
    popover.onDidDismiss(this.dismissSortPopover.bind(this))
  }

  dismissSortPopover(data){
    if(data){
      this.sortCondition = {};
      this.sortCondition.sortCondt = data.selectedValue;
      this.sortCondition.isAscending = data.isAscending;
      this.pageNumber = -1;
      this.fetchedResults = 0;
      this.leads = [];
      this.doSearchMoreItems({complete:function(){}});
    }
  }

  doSearchMoreItems(infiniteScroll) {
     if(this.fetchedResults >= this.numberOfResults ){
        infiniteScroll.complete();
        return;
      }
      this.request = new SalesLeadSearchRequest();
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
      this.request.pageID = "saleslead";
      if(this.filter){
         this.request.filter = this.filter;
      }
      let context = new ContextParameters();
      context.workableleads = "true";
      this.request.contextParameters = context;
      this.http.processServerRequest("post",this.request, true, this.pageNumber != 0).subscribe(
                     res => this.salesLeadSearchSuccess(res, infiniteScroll),
                     error =>  this.salesLeadSearchError(error, infiniteScroll)); 
  }

  salesLeadSearchSuccess(response, infiniteScroll):void{
    this.response = response;
    if(this.response.result == "failure"){
       infiniteScroll.complete();
       return ;
    }
    if(this.response.dataObject.length == 0){
      infiniteScroll.complete();
       return ;
    }
    this.leads = this.leads.concat(this.response.dataObject);
    this.fetchedResults += this.response.fetchedRecords;
    infiniteScroll.complete();
  }
 

  salesLeadSearchError(error,infiniteScroll){
    infiniteScroll.complete();
    this.http.setAuthToken(null);
  }


  onItemSelect(lead:SalesLeads):void{
    this.navCtrl.push(SalesLeadDetails, {lead: lead});
  }

  onCustomerCall(lead: SalesLeads):void{
    window.location.href = "tel:"+lead.Customer.Phone;
  }

}
