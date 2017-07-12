import { Component } from '@angular/core';
import { ViewController, NavParams } from 'ionic-angular';
import { ReasonCodeItem } from '../';
import { ReasonCodeProvider } from '../../../providers/';

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
  private reasonCodes: Array<any>;
  private item:any;
  constructor(public viewCtrl: ViewController, private navParam: NavParams,
              private rcp: ReasonCodeProvider) {
    this.model = new ReasonCodeItem();
    this.item = this.navParam.get("item");
    this.rcp.finiteValueSource$.subscribe(res => {this.updateReasonCodes(res)});
    this.rcp.getFiniteValues();
  }

   updateReasonCodes(reasonCodes){
     this.reasonCodes =  reasonCodes.WISHREASON;
  }

  onDismiss():void{
    this.viewCtrl.dismiss(this.model);
  }

}
