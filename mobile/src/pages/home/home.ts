import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { CustomerHomePage } from '../customer-mgmt';

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
  private rootMenus = ["Setup","Items","Customer Management","Sales & Marketing","Pricing & Promotions","Business Intelligence","Inventory"];
  private submenuList;
  private menuTitle = "Menu";
  private subMenus = {"Setup":[
                       {"label":"Configuration", "page":'config'},
                       {"label":"Data Setup", "page":'rdscontroller?page=setup'},
                       {"label":"Divisions", "page":'rdscontroller?page=divisions'},
                       {"label":"Users", "page":'rdscontroller?page=appUsers'},
                       {"label":"Vendors", "page":'rdscontroller?page=vendors'}
                     ],
                    "Items":[
                       {"label":"Categories", "page":'rdscontroller?page=categories'},
                       {"label":"Products", "page":'rdscontroller?page=products'},
                       {"label":"Items", "page":'rdscontroller?page=items'},
                       {"label":"Item Images", "page":'rdscontroller?page=itemimages'}
                    ],
                    "Customer Management":[
                       {"label":"Customers", "page":CustomerHomePage},
                       {"label":"Categories", "page":'customers'},
                       {"label":"Loyalty", "page":'customers'},
                       {"label":"Wish List", "page":'customers'}
                    ],
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
                    ],
                    "Inventory":[
                       {"label":"Stock", "page":'rdscontroller?page=inventory'},
                       {"label":"Purchases", "page":'rdscontroller?page=purchases'},
                       {"label":"Purchase Returns", "page":'rdscontroller?page=purchasereturns'},
                       {"label":"Sales", "page":'rdscontroller?page=sales'},
                       {"label":"Sales Returns", "page":'rdscontroller?page=salesreturns'}
                    ]};
  constructor(public navCtrl: NavController) {
    this.isRoot = true;
  }

  ionViewDidLoad() {
    console.log('Hello Home Page');
  }

  displaySubmenu(index, rootPage):void{
    this.isRoot = false;
    this.menuTitle = rootPage;
    this.submenuList = this.subMenus[rootPage];
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
