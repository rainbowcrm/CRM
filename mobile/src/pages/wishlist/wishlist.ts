import { Component } from '@angular/core';
import { DatePipe } from '@angular/common';
import { NavController, PopoverController, ToastController } from 'ionic-angular';
import { Customer, CustomerHomePage } from '../customer-mgmt/';
import { Inventory, ItemSearch } from '../items/';
import { HomePage } from '../home/home';
import { SharedService, HTTPService } from '../../providers/';
import { Storage } from '@ionic/storage';
import { ReasonCodeItemPopOverPage, ReasonCodeItem,WishList, WishlistLineItem, Sku, NewWishListRequest } from './';


/*
  Generated class for the Home page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-wishlist',
  templateUrl: 'wishlist.html'
})
export class WishListPage {
  private customer: Customer;
  private errorMessage;
  private items: Array<WishlistLineItem>;
  private associatedItem: Inventory;
  constructor(public navCtrl: NavController, private sharedData: SharedService,
              private storage: Storage, private popoverCtrl: PopoverController,
              private http: HTTPService, private datePipe: DatePipe,
              private toastCtrl: ToastController) {
       this.items = []; 
  }

  goHome():void{
      this.navCtrl.setRoot(HomePage);
  }

  ionViewDidEnter() {
    this.customer = this.sharedData.getData("customer");
    //var self = this;
    this.storage.ready().then(() => {
       // Or to get a key/value pair
       this.storage.get('associateItem').then((val) => {
         this.associatedItem = val;
         if(val){
            let popover = this.popoverCtrl.create(ReasonCodeItemPopOverPage, {item:val.Sku}, {cssClass:"wishlistReasonCode"});
             popover.present({});
             popover.onDidDismiss(this.dismissReasonCodePopover.bind(this))
         }
       })
       this.storage.get('wishlist').then((val) => {
         if(val){
            this.items = val;
         }
       })
     });
  }

  dismissReasonCodePopover(data: ReasonCodeItem){
       this.storage.remove('associateItem');
       this.createWishlistItem(data, this.associatedItem);
  }

  associateCustomer():void{
    this.navCtrl.push(CustomerHomePage,{isAssociateCustomer: true});
  }

  addItem():void{
    this.navCtrl.push(ItemSearch,{isAssociateItems: true});
  }

  deAssociateCustomer():void{
    this.sharedData.removeData("customer");
    this.customer = undefined;
  }

  createWishlist(): void{
    this.storage.get('division').then((val) => {
      let wishlistRequest = new NewWishListRequest();
      this.errorMessage = null; 
      wishlistRequest.fixedAction = "FixedAction.ACTION_CREATE";
      wishlistRequest.currentmode = "CREATE";
      wishlistRequest.dataObject = new WishList();
      wishlistRequest.dataObject.Division = val;
      var date = new Date();
      wishlistRequest.dataObject.WishListDate = this.datePipe.transform(date,"yyyy-MM-dd");
      wishlistRequest.dataObject.Customer = {Phone:this.customer.Phone};
      wishlistRequest.dataObject.WishListLines = this.items;
      wishlistRequest.pageID = 'newwishlist';
      this.http.processServerRequest("post",wishlistRequest, true).subscribe(
                       res => {this.wishlistSuccess(res)},
                       error =>  this.wishlistError(error));  
      });
    
  }

  wishlistSuccess(res){
    this.storage.remove('wishlist');
    let toast = this.toastCtrl.create({
      message: 'Wishlisted created',
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

  wishlistError(res){
     this.errorMessage = "Failed to create wishlist"; 
  }

  removeItem(index):void{
     var itemindex = index;
     this.items.splice(itemindex,1);
     //save to DB
     this.storage.set("wishlist",this.items);
  }

  private createWishlistItem(reason: ReasonCodeItem, item: Inventory){
    var wishlist = new WishlistLineItem();
    wishlist.LineNumber = this.items.length+1+"";
    wishlist.Comments = reason.comments;
    wishlist.ReasonCode = reason.reasonCode;
    wishlist.DesiredDate = reason.desiredDate;
    wishlist.DesiredPrice = reason.desiredPrice;
    wishlist.SalesLeadGenerated = "false";
    wishlist.Qty = reason.quantity;
    wishlist.Sku = new Sku();
    wishlist.Sku.Name = item.Sku.Name;
    this.items.push(wishlist);
    //save to DB
    this.storage.set("wishlist",this.items);
  }

}
