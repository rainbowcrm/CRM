import { Component } from '@angular/core';
import { ViewController } from 'ionic-angular';
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
  constructor(public viewCtrl: ViewController) {
    this.model = new ReasonCodeItem();
  }

  onDismiss():void{
    this.viewCtrl.dismiss(this.model);
  }

}
