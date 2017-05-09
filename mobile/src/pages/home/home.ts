import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { CustomerHomePage } from '../customer-mgmt';
import { ContactHomePage } from '../contact-mgmt';
import { ItemSearch } from '../items';
import { PushService,SecureStorageService } from '../../plugins/';
import { PushObject } from '@ionic-native/push';


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
  private rootMenus = ["Item Search","Customer Management","Contacts","Pricing & Promotions","Business Intelligence","Inventory"];
  private submenuList;
  private menuTitle = "Menu";
  private subMenus = {
                    "Item Search":ItemSearch,
                    "Customer Management":CustomerHomePage,
                    "Contacts":ContactHomePage,
                    "Sales & Marketing":[
                       {"label":"Sales Target", "page":'rdscontroller?page=salesperiodlist'},
                       {"label":"Territories", "page":'rdscontroller?page=territories'},
                       {"label":"Expenses", "page":'rdscontroller?page=salesexpenses'},
                       {"label":"Sales Leads", "page":'rdscontroller?page=salesleads'},
                       {"label":"Sales Cycle", "page":'rdscontroller?page=salescycle'},
                       {"label":"Marketing Campaign", "page":'rdscontroller?page=campaign'},
                    ],
                   "Pricing & Promotions":[
                       {"label":"Pricing Workbench", "page":'rdscontroller?page=territories'},
                       {"label":"Promotions", "page":'rdscontroller?page=salesexpenses'}
                    ],
                    "Business Intelligence":[
                       {"label":"Manager Console", "page":'rdscontroller?page=managerconsole'},
                       {"label":"Sales Target Evaluation", "page":'rdscontroller?page=salesperiodAnalysis'},
                       {"label":"Product Analysis", "page":'rdscontroller?page=productanalysis'},
                       {"label":"Salesforce_Analysis", "page":'rdscontroller?page=salesforceanalysis'}
                    ]};
  constructor(public navCtrl: NavController, private pushService: PushService, private storageService: SecureStorageService) {
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
