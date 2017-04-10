import { Component } from '@angular/core';
import { NavParams, NavController } from 'ionic-angular';

import { Item } from '../';
import { HTTPService } from '../../../providers/';

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
  constructor(private params: NavParams,private http:HTTPService,
              private navCtrl: NavController) {
    this.items = this.params.get('items');
  }

  ionViewDidLoad() {
     
  }

}
