import { Component } from '@angular/core';
import { ViewController, NavParams } from 'ionic-angular';
import { ReasonCodeItem } from '../';

/*
  Generated class for the Reason code page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-rc-popover',
  templateUrl: 'rc-popover.html'
})
export class ReasonCodeItemPopOverPage {
  private model: ReasonCodeItem;
  private item:any;
  constructor(public viewCtrl: ViewController, private navParam: NavParams) {
    this.model = new ReasonCodeItem();
    this.item = this.navParam.get("item");
  }

  onDismiss():void{
    this.viewCtrl.dismiss(this.model);
  }

}
