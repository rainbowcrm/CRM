import { Component, ViewChild } from '@angular/core';
import { NavParams, Slides, ToastController, NavController } from 'ionic-angular';
import { SalesLeads} from '../';
import { Storage } from '@ionic/storage';


/*
  Generated class for the ItemDetails page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-sales-lead-details',
  templateUrl: 'sales-lead-details.html'
})
export class SalesLeadDetails {
  private lead:SalesLeads;

  constructor(private params: NavParams,private navCtrl: NavController) {
    this.lead = this.params.get('lead');
  }
 

}
