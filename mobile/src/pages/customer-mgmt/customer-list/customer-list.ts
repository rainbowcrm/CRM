import { Component } from '@angular/core';
import { NavParams, NavController, ToastController, AlertController } from 'ionic-angular';

import { Customer, CustomerAddPage, CustomerSearchRequest, CustomerSearchResponse } from '../';
import { HTTPService, Loader } from '../../../providers/';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-customer-list',
  templateUrl: 'customer-list.html'
})
export class CustomerListPage {
  private customers:Array<Customer>;
  private request: CustomerSearchRequest;
  private response: CustomerSearchResponse;
  private filter: Array<Object>;
  private pageNumber: number;

  constructor(private params: NavParams,private http:HTTPService,
              private navCtrl: NavController, private loader:Loader,
              private toastCtrl: ToastController, private alertCtrl:AlertController) {
    this.customers = this.params.get('customers');
    this.filter = this.params.get('filter');
    this.pageNumber = 1;
  }

  ionViewDidLoad() {
     
  }

  getCustomerAddress(customer){
     let customerAddr = [];
     var keys = ["Address1","Address2","City","ZipCode","Landmark"];
     keys.forEach(function(value, key){
       if(customer[value] && customer[value].length > 0){
           customerAddr.push(customer[value])
         }
     })
     if(customerAddr.length == 0){
     return "no information available";
     }
    else{
      return customerAddr.join(" ,");
    }

  }

  doSearchMoreCustomer(infiniteScroll) {
      this.request = new CustomerSearchRequest();
      this.request.currentmode = 'READ';
      this.request.fixedAction = "FixedAction.NAV_NEXTPAGE";
      this.request.pageNumber = ++this.pageNumber;
      this.request.pageID = "customers";
      this.request.filter = this.filter;
      this.http.processServerRequest("post",this.request, true).subscribe(
                     res => this.customerSearchSuccess(res, infiniteScroll),
                     error =>  this.customerSearchError(error, infiniteScroll)); 
  }

  customerSearchSuccess(response, infiniteScroll):void{
    this.response = response;
    if(this.response.result == "failure"){
       infiniteScroll.complete();
       return ;
    }
    if(this.response.dataObject.length == 0){
      infiniteScroll.complete();
       return ;
    }
    this.customers = this.customers.concat(this.response.dataObject);
    infiniteScroll.complete();
  }
 

  customerSearchError(error,infiniteScroll){
    infiniteScroll.complete();
    this.http.setAuthToken(null);
  }

  editCustomer(customer: Customer){
     this.navCtrl.push(CustomerAddPage,{customer: customer});
  }

  deleteCustomer(customer: Customer){
    let alert = this.alertCtrl.create({
    title: 'Delete Customer',
    message: 'Do you want to delete the customer?',
    buttons: [
      {
        text: 'Cancel',
        role: 'cancel',
        handler: data => {
          console.log('Cancel clicked');
        }
      },
      {
        text: 'Delete',
        handler: data => {
          this.deleteCustomerRequest(customer);
        }
      }
    ]
  });
  alert.present();
  }

  deleteCustomerRequest(customer: Customer){
      this.loader.dismissLoader();
      this.request = new CustomerSearchRequest();
      this.request.currentmode = 'DELETE';
      this.request.dataObject = customer;
      this.request.fixedAction = "FixedAction.ACTION_DELETE";
      this.request.pageID = "deletecustomer";
      this.http.processServerRequest("post",this.request, true).subscribe(
                     res => this.customerDeleteSuccess(customer),
                     error =>  this.customerDeleteError(error)); 
  }
  customerDeleteSuccess(response):void{
    this.loader.dismissLoader();
    this.response = response;
    if(this.response.result == "failure"){
      this.failedToDeleteToast();
    }
    else{
      this.customerDeletedToast();
    }
  }

  customerDeleteError(error){
    this.http.setAuthToken(null);
    this.failedToDeleteToast();
    this.loader.dismissLoader();
  }

  failedToDeleteToast():any{
     let toast = this.toastCtrl.create({
      message: 'Unable to delete customer',
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

  customerDeletedToast():any{
     let toast = this.toastCtrl.create({
      message: 'Cutomer detail deleted',
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

}
