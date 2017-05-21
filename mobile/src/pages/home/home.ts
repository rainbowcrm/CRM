import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { CustomerHomePage } from '../customer-mgmt';
import { ContactHomePage } from '../contact-mgmt';
import { ItemSearch } from '../items';
import { PushService} from '../../plugins/';
import { HTTPService} from '../../providers/';
import { PushObject } from '@ionic-native/push';
import { WishListPage } from '../wishlist';
import { RegTokenRequest } from './home.model';
import { Storage } from '@ionic/storage';


/*
  Generated class for the Home page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-home',
  templateUrl: 'home.html'
})
export class HomePage {
  private rootTitle = "Menu";
  private isRoot;
  private rootMenus = ["Item Search","Customer Management","Contacts", "Wishlist"];
  private submenuList;
  private menuTitle = "Menu";
  private subMenus = {
                    "Item Search":ItemSearch,
                    "Customer Management":CustomerHomePage,
                    "Contacts":ContactHomePage,
                    "Wishlist": WishListPage
                   };
  constructor(public navCtrl: NavController, private pushService: PushService
                , private http: HTTPService, private storage: Storage) {
    this.isRoot = true;
  }

  ionViewDidLoad() {
    this.pushService.init();
    this.pushService.onNotification.subscribe(notification => this.onNotification(notification));
    this.pushService.onNotificationRegistered.subscribe(registration => this.onNotificationRegistered(registration));
  }

  onNotification(notification){
    //TO DO may be page navigation
    alert("onNotification");
  }

  onNotificationRegistered(registration){
    console.log(registration)
    var self = this;
    this.storage.get('name').then((val) => {
      self.registerDeviceToken(registration.registrationId,val);
    });
  }

  registerDeviceToken(regId: string, userName: string):void{
     var request = new RegTokenRequest();
     request.pageID = "login";
     request.submitAction = "regMobileNotificationID";
     request.dataObject.mobileNotificationId = regId;
     request.dataObject.AuthToken = this.http.getAuthToken();
     request.dataObject.username = userName;
     this.http.processServerRequest("post",request, true, true).subscribe(
                     res => console.log("Device Registered"),
                     error =>  console.log("Error registering device"));  
  }



  displaySubmenu(index, rootPage):void{
    this.submenuList = this.subMenus[rootPage];
    if(this.submenuList.constructor == Array)
    {
      this.isRoot = false;
      this.menuTitle = rootPage;
    }
    else{
      this.isRoot = true;
      this.menuTitle = "Menu";
      this.navigateToPage(index, {page:this.submenuList});
    }
  }

  displayMainmenu(index, menu):void{
    this.isRoot = true;
    this.menuTitle = "Menu";
  }

  navigateToPage(index, menu):void{
    this.isRoot = true;
    this.menuTitle = "Menu";
    this.navCtrl.setRoot(menu.page);
  }

}
