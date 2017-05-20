import { Component } from '@angular/core';
import { NavParams, NavController } from 'ionic-angular';

import { HTTPService } from '../../../providers/';
import { Item, ItemSearchRequest, ItemSearchResponse, ItemDetails } from '../';

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
  private response: ItemSearchResponse;
  private pageNumber: number;
  private fetchedResults: number;
  private numberOfResults: number;
  private isAssociateItems:Boolean;
  private filter:Array<Object>;

  constructor(private params: NavParams,private http:HTTPService,
              private navCtrl: NavController) {
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

  onItemSelect(item:Item):void{
    this.navCtrl.push(ItemDetails,{item:item, isAssociateItems:this.isAssociateItems});
  }

}
