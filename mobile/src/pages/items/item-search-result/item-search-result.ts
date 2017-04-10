import { Component } from '@angular/core';
import { NavParams, NavController } from 'ionic-angular';

import { HTTPService } from '../../../providers/';
import { Item, ItemSearchRequest, ItemSearchResponse } from '../';

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
  private hasMoreResults:boolean;
  private request: ItemSearchRequest;
  private response: ItemSearchResponse;
  private pageNumber: number;
  private filter:Array<Object>;

  constructor(private params: NavParams,private http:HTTPService,
              private navCtrl: NavController) {
    this.hasMoreResults = true;
    this.items = this.params.get('items');  
    this.filter = this.params.get('filter');
    this.pageNumber = 0;
  }

  ionViewDidLoad() {
     
  }

  doSearchMoreItems(infiniteScroll) {
      if(!this.hasMoreResults){
        infiniteScroll.complete();
        return;
      }
      this.request = new ItemSearchRequest();
      this.request.currentmode = 'READ';
      this.request.fixedAction = "FixedAction.NAV_NEXTPAGE";
      this.request.hdnPage = ++this.pageNumber;
      this.request.pageID = "items";
      this.request.filter = this.filter;
      this.http.processServerRequest("post",this.request, true).subscribe(
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
      this.hasMoreResults = false;
      infiniteScroll.complete();
       return ;
    }
    this.items = this.items.concat(this.response.dataObject);
    infiniteScroll.complete();
  }
 

  itemSearchError(error,infiniteScroll){
    infiniteScroll.complete();
    this.http.setAuthToken(null);
  }

}
