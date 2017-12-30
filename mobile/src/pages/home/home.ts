import { Component } from '@angular/core';
import { NavController, AlertController } from 'ionic-angular';
import { CustomerHomePage } from '../customer-mgmt';
import { ContactHomePage } from '../contact-mgmt';
import { LoginPage } from '../login/login';
import { AlertsHomePage } from '../alerts';
import { ItemSearch } from '../items';
import { PushService} from '../../plugins/';
import { HTTPService} from '../../providers/';
import { PushObject } from '@ionic-native/push';
import { WishListPage } from '../wishlist';
import { SalesLeadSearch } from '../sales-lead';
import { DiscussionsList } from '../discussions';
import { ExpensesAddPage } from '../expenses';
import { FollowUpAddPage } from '../follow-up';
import { EnquiryAddPage } from '../enquiry';
import { ProductsList } from '../products-faq';
import { AppointemntHomePage } from '../appointments';
import { RegTokenRequest, LogoutRequest, Token } from './home.model';
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
  private rootMenus = ["Item Search", "Appointments","Customer Management","Contacts","Alerts", "Wishlist", "Sales Leads",
                       "Discussions", "Expense Vouchers", "Follow Up", "Enquiry", "Products FAQ"];
  private submenuList;
  private menuTitle = "Menu";
  private subMenus = {
                    "Item Search":ItemSearch,
                    "Appointments": AppointemntHomePage,
                    "Customer Management":CustomerHomePage,
                    "Contacts":ContactHomePage,
                    "Wishlist": WishListPage,
                    "Sales Leads": SalesLeadSearch,
                    "Discussions": DiscussionsList,
                    "Alerts": AlertsHomePage,
                    "Expense Vouchers": ExpensesAddPage,
                    "Follow Up": FollowUpAddPage,
                    "Enquiry": EnquiryAddPage,
                    "Products FAQ": ProductsList
                   };
  constructor(public navCtrl: NavController, private pushService: PushService
                , private http: HTTPService, private storage: Storage,
                private alertCtrl: AlertController) {
    this.isRoot = true;
  }

  ionViewDidLoad() {
    this.pushService.init();
    this.pushService.onNotification.subscribe(notification => this.onNotification(notification));
    this.pushService.onNotificationRegistered.subscribe(registration => this.onNotificationRegistered(registration));
  }

  onNotification(notification){
    //TO DO may be page navigation
  }

  onNotificationRegistered(registration){
    console.log(registration)
    var self = this;
    this.storage.get('user').then((val) => {
      self.registerDeviceToken(registration.registrationId,val);
    });
  }

  registerDeviceToken(regId: string, userName: string):void{
     var request = new RegTokenRequest();
     request.pageID = "login";
     request.submitAction = "regMobileNotificationID";
     request.dataObject = new Token();
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

  private logoutFromBackend(): void{
    this.storage.get('name').then((val) => {
      var request = new LogoutRequest();
       request.pageID = "login";
       request.submitAction = "logout";
       request.dataObject = {};
       request.dataObject.AuthToken = this.http.getAuthToken();
       request.dataObject.username = val;
       this.http.processServerRequest("post",request, true, true).subscribe(
                      res => {
                         this.navCtrl.setRoot(LoginPage);
                         this.http.setAuthToken("")
                       },
                      error =>  console.log("Error logging out")); 
    });
     
  }

  displayMainmenu(index, menu):void{
    this.isRoot = true;
    this.menuTitle = "Menu";
  }

  logout(){
    let alert = this.alertCtrl.create({
    title: 'Logout',
    message: 'Are you sure you want to logout?',
    buttons: [
      {
        text: 'Cancel',
        role: 'cancel',
        handler: data => {
          console.log('Cancel clicked');
        }
      },
      {
        text: 'Confirm',
        handler: data => {
          this.logoutFromBackend();
        }
      }
    ]
   });
   alert.present();
  }

  navigateToPage(index, menu):void{
    this.isRoot = true;
    this.menuTitle = "Menu";
    this.navCtrl.setRoot(menu.page);
  }

}
