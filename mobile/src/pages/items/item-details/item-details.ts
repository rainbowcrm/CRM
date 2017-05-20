import { Component, ViewChild } from '@angular/core';
import { NavParams, Slides, ToastController, NavController } from 'ionic-angular';
import { Item} from '../';
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
  private item:Item;
  private slideImg: Array<String>=[];
  private isAssociateItems:Boolean;
   @ViewChild(Slides) slides: Slides;
  

  constructor(private params: NavParams, private storage: Storage, 
  private toastCtrl: ToastController, private navCtrl: NavController) {
    this.item = this.params.get('item');
    this.isAssociateItems = this.params.get('isAssociateItems');
    this.processImageUrl();
  }

  addToCart(){
      this.storage.get('wishList').then((val) => {
         var items = val; 
         if(!val){
           items = [];
         }
         items.push(this.item);
         this.storage.set("wishList",items);
         let toast = this.toastCtrl.create({
          message: 'Item added to cart',
          duration: 2000,
          position: 'top'
         });
        toast.present();
        this.navCtrl.pop();
      })
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
