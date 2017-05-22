import { Component } from '@angular/core';
import { NavController, ToastController, NavParams } from 'ionic-angular';
import { Item, Product, ItemSearchRequest, ItemSearchResponse, ItemSearchFilter } from '../';
import { HTTPService } from '../../../providers/';
import { HomePage } from '../../home/home';
import { ItemSearchResult } from '../';
import { BarcodeScanner } from '@ionic-native/barcode-scanner';

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
  private isAssociateItems: Boolean;
  

  constructor(public navCtrl: NavController, private http:HTTPService
  ,  private toastCtrl: ToastController, private barcodeScanner: BarcodeScanner,
  private params: NavParams) {
    this.model.Product = new Product();
    this.isAssociateItems = this.params.get("isAssociateItems");
  }

  ionViewDidLoad() {
    console.log('Hello ItemSearch Page');
  }

  goHome():void{
      this.navCtrl.setRoot(HomePage);
  }
  scanItem(): void{
    var self = this;
    this.barcodeScanner.scan().then((barcodeData) => {
        self.searchString = barcodeData.text;
        self.doSearch();
     }, (err) => {
       let toast = self.toastCtrl.create({
          message: 'Scan was not complete. Kindly retry',
          duration: 2000,
          position: 'top'
        });
       toast.present();
    });
  }

  NoItemsFoundToast():any{
     let toast = this.toastCtrl.create({
      message: 'No Items found',
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
      this.request = new ItemSearchRequest();
      this.request.currentmode = 'READ';
      this.request.fixedAction = "FixedAction.NAV_FIRSTPAGE";
      this.request.pageID = "skucompletelist";
      this.request.filter = [];
      this.request.hdnPage = 0;
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
    this.response = response;
    if(this.response.result == "failure"){
       this.errorMessage = "Failed to search items"; 
       return ;
    }
    if(this.response.dataObject.length == 0){
       this.NoItemsFoundToast();
       return ;
    }
    this.navCtrl.push(ItemSearchResult, {items:this.response.dataObject, filter:filter,
      fetchedResults: this.response.fetchedRecords, numberOfResults:this.response.availableRecords,
      isAssociateItems:this.isAssociateItems});
  }
 

  itemSearchError(error){
    this.http.setAuthToken(null);
    this.errorMessage = "Failed to search items";
  }

}
