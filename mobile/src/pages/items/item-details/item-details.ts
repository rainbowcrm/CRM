import { Component, ViewChild } from '@angular/core';
import { NavParams, Slides, ToastController, NavController } from 'ionic-angular';
import { ItemWithDetails, Inventory} from '../';
import { Storage } from '@ionic/storage';


/*
  Generated class for the ItemDetails page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-item-details',
  templateUrl: 'item-details.html'
})
export class ItemDetails {
  private item:ItemWithDetails;
  private inventory: Inventory;
  private slideImg: Array<String>=[];
  private isAssociateItems:Boolean;
   @ViewChild(Slides) slides: Slides;
  

  constructor(private params: NavParams, private storage: Storage, 
  private toastCtrl: ToastController, private navCtrl: NavController) {
    this.item = this.params.get('item');
    this.inventory = this.item.Inventory[0];
    this.isAssociateItems = this.params.get('isAssociateItems');
    this.processImageUrl();
  }

  addToCart(){
      this.storage.get('associateItem').then((val) => {
        this.storage.remove('associateItem');
        this.storage.set("associateItem",this.inventory);
        this.navCtrl.popToRoot();
      })
  }
  
  inventoryChanged(index){
    this.inventory = this.item.Inventory[index];
  }

  processImageUrl(){
    var imageUrls = ['Image1URL','Image2URL','Image3URL'];
    for(var l=0;l<imageUrls.length;l++){
      var image = this.item[imageUrls[l]];
      if(image){
         this.slideImg.push(image);
      }
    }
  }

  ionViewDidLoad() {
    this.slides.pager = true;
  }


 

}
