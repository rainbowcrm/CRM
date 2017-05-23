import { Component } from '@angular/core';
import { NavController, PopoverController } from 'ionic-angular';
import { Customer, CustomerHomePage } from '../customer-mgmt/';
import { Inventory, ItemSearch } from '../items/';
import { HomePage } from '../home/home';
import { SharedService } from '../../providers/';
import { Storage } from '@ionic/storage';
import { ReasonCodeItemPopOverPage, ReasonCodeItem } from './';


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
  private items: Inventory;
  constructor(public navCtrl: NavController, private sharedData: SharedService,
              private storage: Storage, private popoverCtrl: PopoverController) {
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
         if(val){
            let popover = this.popoverCtrl.create(ReasonCodeItemPopOverPage, {}, {cssClass:"wishlistReasonCode"});
             popover.present({});
             popover.onDidDismiss(this.dismissReasonCodePopover)
         }
       })
     });
  }

  dismissReasonCodePopover(data: ReasonCodeItem){
       debugger
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

}
