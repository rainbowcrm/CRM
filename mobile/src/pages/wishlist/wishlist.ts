import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { Customer, CustomerHomePage } from '../customer-mgmt/';
import { Item, ItemSearch } from '../items/';
import { HomePage } from '../home/home';
import { SharedService } from '../../providers/';
import { Storage } from '@ionic/storage';


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
  private items: Array<Item>;
  constructor(public navCtrl: NavController, private sharedData: SharedService,
              private storage: Storage) {
     this.items = [];
  }

  goHome():void{
      this.navCtrl.setRoot(HomePage);
  }

  ionViewDidEnter() {
    this.customer = this.sharedData.getData("customer");
    this.storage.ready().then(() => {
       // Or to get a key/value pair
       this.storage.get('wishList').then((val) => {
         this.items = val;
       })
     });
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
