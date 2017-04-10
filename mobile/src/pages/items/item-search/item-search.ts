import { Component } from '@angular/core';
import { NavController, ToastController } from 'ionic-angular';
import { Item, Product, ItemSearchRequest, ItemSearchResponse, ItemSearchFilter } from '../';
import { HTTPService, Loader } from '../../../providers/';
import { HomePage } from '../../home/home';
import { ItemSearchResult } from '../';

/*
  Generated class for the ItemSearch page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-item-search',
  templateUrl: 'item-search.html'
})
export class ItemSearch {
  private model:Item = new Item();
  private searchString:string;
  private request: ItemSearchRequest;
  private response: ItemSearchResponse;
  private errorMessage:string;
  

  constructor(public navCtrl: NavController, private http:HTTPService,
    private loader:Loader,  private toastCtrl: ToastController) {
    this.model.Product = new Product();
  }

  ionViewDidLoad() {
    console.log('Hello ItemSearch Page');
  }

  goHome():void{
      this.navCtrl.setRoot(HomePage);
  }

  NoItemsFoundToast():any{
     let toast = this.toastCtrl.create({
      message: 'No Items found',
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

  doSearch():void{
      this.errorMessage = null;
      this.request = new ItemSearchRequest();
      this.request.currentmode = 'READ';
      this.request.fixedAction = "FixedAction.NAV_FIRSTPAGE";
      this.request.pageID = "items";
      this.request.filter = [];
      let filter = new ItemSearchFilter();
      filter.field = "Product.Name";
      filter.operator = "EQUALS";
      filter.value = this.searchString;
      this.request.filter.push(filter);

      this.http.processServerRequest("post",this.request, true).subscribe(
                     res => this.itemSearchSuccess(res, this.request.filter.slice(0)),
                     error =>  this.itemSearchError(error));  
  }


  itemSearchSuccess(response, filter):void{
    this.loader.dismissLoader();
    this.response = response;
    if(this.response.result == "failure"){
       this.errorMessage = "Failed to search items"; 
       return ;
    }
    if(this.response.dataObject.length == 0){
       this.NoItemsFoundToast();
       return ;
    }
    this.navCtrl.push(ItemSearchResult, {items:this.response.dataObject, filter:filter});
  }
 

  itemSearchError(error){
    this.http.setAuthToken(null);
    this.loader.dismissLoader();
    this.errorMessage = "Failed to search items";
  }

}
