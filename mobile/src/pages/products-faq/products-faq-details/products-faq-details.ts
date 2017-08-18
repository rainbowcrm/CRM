import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { ProductDetails } from '../';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-products-faq-details',
  templateUrl: 'products-faq-details.html'
})
export class ProductsDetailsList {
  private productDetails:Array<ProductDetails> = [];

  constructor(private navCtrl: NavController, private navParams: NavParams) {
    this.productDetails = this.navParams.get("productDetails");
  }

}
