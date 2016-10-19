import { Component } from '@angular/core';
import { NavController, ToastController } from 'ionic-angular';

import { Customer, CustomerSearchRequest, CustomerSearchResponse } from '../';
import { HomePage } from '../../home/home';
import { CustomerAddPage, CustomerListPage} from '../'
import { HTTPService, Loader } from '../../../providers/';

/*
  Generated class for the CustomerHome page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-customer-home',
  templateUrl: 'customer-home.html'
})
export class CustomerHomePage {
  private filterName: string;
  private model:Customer = new Customer();
  private request: CustomerSearchRequest;
  private response: CustomerSearchResponse;
  private errorMessage:string;
  constructor(public navCtrl: NavController,private http:HTTPService,
    private loader:Loader,  private toastCtrl: ToastController) {}

  ionViewDidLoad() {
    console.log('Hello CustomerHome Page');
  }

  goHome():void{
      this.navCtrl.setRoot(HomePage);
  }

  NoCustomersFoundToast():any{
     let toast = this.toastCtrl.create({
      message: 'No customers found',
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

  doSearch():void{
      this.errorMessage = null;
      this.request = new CustomerSearchRequest();
      this.request.currentmode = 'READ';
      this.request.fixedAction = "FixedAction.NAV_FIRSTPAGE";
      this.request.pageID = "customers";
      this.request.filter = this.getFilters();
      this.http.processServerRequest("post",this.request, true).subscribe(
                     res => this.customerSearchSuccess(res),
                     error =>  this.customerSearchError(error));  
  }

  getFilters():Array<any>{
      let filters = [];
      for(let data in this.model){
        if(this.model[data] && this.model[data].length>0)
           filters.push({
	      	  "field" :data ,
	   	      "operator" :"EQUALS",
	   	      "value":this.model[data]
	         });
      }
      return filters;
  }
  customerSearchSuccess(response):void{
    this.loader.dismissLoader();
    this.response = response;
    if(this.response.result == "failure"){
       this.errorMessage = "Failed to search customer"; 
       return ;
    }
    if(this.response.dataObject.length == 0){
       this.NoCustomersFoundToast();
       return ;
    }
    this.navCtrl.push(CustomerListPage, {customers:this.response.dataObject});
  }
 

  customerSearchError(error){
    this.http.setAuthToken(null);
    this.loader.dismissLoader();
    this.errorMessage = "Failed to search customer";
  }
  addCustomer():void{
     this.navCtrl.push(CustomerAddPage);
  }

}
