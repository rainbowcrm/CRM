import { Component } from '@angular/core';
import { NavParams, NavController, ToastController } from 'ionic-angular';

import { HTTPService } from '../../../providers/';
import { Item, ItemSearchRequest, ItemSearchResponse, ItemDetails, ItemDetailsRequest } from '../';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-item-search-result',
  templateUrl: 'item-search-result.html'
})
export class ItemSearchResult {
  private items:Array<Item>;
  private request: ItemSearchRequest;
  private itemDetailsRequest: ItemDetailsRequest;
  private response: ItemSearchResponse;
  private pageNumber: number;
  private fetchedResults: number;
  private numberOfResults: number;
  private isAssociateItems:Boolean;
  private filter:Array<Object>;

  constructor(private params: NavParams,private http:HTTPService,
              private navCtrl: NavController, private toastCtrl: ToastController) {
    this.items = this.params.get('items');  
    this.filter = this.params.get('filter');
    this.pageNumber = 0;
    this.fetchedResults = this.params.get('fetchedResults');
    this.numberOfResults = this.params.get('numberOfResults');
    this.isAssociateItems = this.params.get('isAssociateItems');
  }

  ionViewDidLoad() {
     
  }

  doSearchMoreItems(infiniteScroll) {
     if(this.fetchedResults >= this.numberOfResults ){
        infiniteScroll.complete();
        return;
      }
      this.request = new ItemSearchRequest();
      this.request.currentmode = 'READ';
      this.request.fixedAction = "FixedAction.NAV_NEXTPAGE";
      this.request.hdnPage = ++this.pageNumber;
      this.request.pageID = "skucompletelist";
      this.request.filter = this.filter;
      this.http.processServerRequest("post",this.request, true, true).subscribe(
                     res => this.itemSearchSuccess(res, infiniteScroll),
                     error =>  this.itemSearchError(error, infiniteScroll)); 
  }

  itemSearchSuccess(response, infiniteScroll):void{
    this.response = response;
    if(this.response.result == "failure"){
       infiniteScroll.complete();
       return ;
    }
    if(this.response.dataObject.length == 0){
      infiniteScroll.complete();
       return ;
    }
    this.items = this.items.concat(this.response.dataObject);
    this.fetchedResults += this.response.fetchedRecords;
    infiniteScroll.complete();
  }
 

  itemSearchError(error,infiniteScroll){
    infiniteScroll.complete();
    this.http.setAuthToken(null);
  }

  itemDetailsSearchSuccess(response):void{
    this.navCtrl.push(ItemDetails,{item:response.dataObject, isAssociateItems:this.isAssociateItems});
  }

  noItemDetailsFoundToast():any{
     let toast = this.toastCtrl.create({
      message: 'No Details Found',
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }
 

  itemDetailsSearchError(error){
    this.noItemDetailsFoundToast();
    this.http.setAuthToken(null);
  }

  onItemSelect(item:Item):void{
    this.request = new ItemDetailsRequest();
    this.request.currentmode = 'READ';
    this.request.fixedAction = "FixedAction.ACTION_READ";
    this.request.hdnPage = ++this.pageNumber;
    this.request.pageID = "skucomplete";
    this.request.dataObject = {};
    this.request.dataObject["Name"] = item.Name;
    this.http.processServerRequest("post",this.request, true, true).subscribe(
                     res => this.itemDetailsSearchSuccess(res),
                     error =>  this.itemDetailsSearchError(error));
  }

}
