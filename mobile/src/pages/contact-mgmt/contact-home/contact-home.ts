import { Component } from '@angular/core';
import { NavController, ToastController } from 'ionic-angular';

import { HomePage } from '../../home/home';
import { ContactAddPage} from '../'
import { HTTPService, Loader } from '../../../providers/';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-contact-home',
  templateUrl: 'contact-home.html'
})
export class ContactHomePage {
  private filterName: string;
  constructor(public navCtrl: NavController) {}

  ionViewDidLoad() {
    console.log('Hello Contact Home Page');
  }

  goHome():void{
      this.navCtrl.setRoot(HomePage);
  }

 
  addContact():void{
     this.navCtrl.push(ContactAddPage);
  }

}
