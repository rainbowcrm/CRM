import { Component } from '@angular/core';
import { NavController, ToastController , NavParams} from 'ionic-angular';

import { Customer, CustomerAddRequest, CustomerAddResponse } from '../';
import { HTTPService, Loader } from '../../../providers/';

/*
  Generated class for the CustomerAdd page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-customer-add',
  templateUrl: 'customer-add.html'
})
export class CustomerAddPage {
  private model:Customer;
  private response: CustomerAddResponse;
  private errorMessage:string;
  private isEdit: boolean;
  private pageTitle: string;

  constructor(public navCtrl: NavController,private http:HTTPService,
    private loader:Loader, private toastCtrl: ToastController,
    private params: NavParams) {
      this.model = this.params.get('customer');
      if(this.model) {
        this.isEdit = true;
      }else{
        this.model = new Customer();
      }
    }

  ionViewDidLoad() {
    this.pageTitle = this.isEdit?"Edit Customer": "Add Customer";
  }

  addCustomer():void{
    this.loader.presentLoader();
    this.errorMessage = null;
    let addCustomerReq = new CustomerAddRequest();
    addCustomerReq.fixedAction = this.isEdit?"FixedAction.ACTION_UPDATE":"FixedAction.ACTION_CREATE";
    addCustomerReq.pageID = 'newcustomer';
    addCustomerReq.currentmode = this.isEdit?"UPDATE":"CREATE";
    addCustomerReq.dataObject = this.model;
    this.http.processServerRequest("post",addCustomerReq, true).subscribe(
                     res => this.customerAddSuccess(res),
                     error =>  this.customerAddError(error));  
  }

  customerAddSuccess(response):void{
    this.loader.dismissLoader();
    if(response.result == "failure"){
       this.errorMessage = "Failed to create customer"; 
       return ;
    }
    this.response = response.dataObject;
    this.showSuccessToast();
    if(this.isEdit){
      this.navCtrl.popToRoot();
      return;
    }
    this.navCtrl.pop();
  }

  showSuccessToast():any{
     let toast = this.toastCtrl.create({
      message: 'Customer was added successfully',
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

  customerAddError(error){
    this.http.setAuthToken(null);
    this.loader.dismissLoader();
    this.errorMessage = "Failed to create customer";
  }

}
