import { Component } from '@angular/core';
import { ViewController, NavParams } from 'ionic-angular';

/*
  Generated class for the Reason code page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-sort-popover',
  templateUrl: 'sort-popover.html'
})
export class SortPopOverPage {
  private sortTypes: Array<any>;
  private selectedIndex:number;
  private isAscending: boolean;
  constructor(public viewCtrl: ViewController, private navParam: NavParams) {
    this.sortTypes = this.navParam.get("sortConditions");
    this.isAscending = true;
  }

  onSortButtonClick(index){
    if((this.selectedIndex != undefined) && (index == this.selectedIndex)){
      this.isAscending = !this.isAscending;
    }else{
      this.selectedIndex = index;
      this.isAscending = true;
    }
  }

  onDismiss():void{
    var data={
      selectedValue:this.sortTypes[this.selectedIndex],
      isAscending: this.isAscending
    }
    this.viewCtrl.dismiss(data);
  }

}
