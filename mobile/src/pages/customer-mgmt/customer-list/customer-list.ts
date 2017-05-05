import { Component } from '@angular/core';
import { NavParams, NavController, ToastController, AlertController } from 'ionic-angular';
import { Customer, CustomerAddPage, CustomerSearchRequest, CustomerSearchResponse } from '../';
import { HTTPService, Loader } from '../../../providers/';
import { ContactService } from '../../../plugins/';

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
  private fetchedResults: number;
  private numberOfResults: number;

  constructor(private params: NavParams,private http:HTTPService,
              private navCtrl: NavController, private loader:Loader,
              private toastCtrl: ToastController, private alertCtrl:AlertController,
              private contactService: ContactService) {
    this.customers = this.params.get('customers');
    this.filter = this.params.get('filter');
    this.pageNumber = 0;
    this.fetchedResults = this.params.get('fetchedResults');
    this.numberOfResults = this.params.get('numberOfResults');
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
      if(this.fetchedResults >= this.numberOfResults ){
        infiniteScroll.complete();
        return;
      }
      this.request = new CustomerSearchRequest();
      this.request.currentmode = 'READ';
      this.request.fixedAction = "FixedAction.NAV_NEXTPAGE";
      this.request.hdnPage = ++this.pageNumber;
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
    this.fetchedResults += this.response.fetchedRecords;
    infiniteScroll.complete();
  }
 

  customerSearchError(error,infiniteScroll){
    infiniteScroll.complete();
    this.http.setAuthToken(null);
  }

  editCustomer(customer: Customer){
    if(customer.Deleted == "true"){
      return;
    }
     this.navCtrl.push(CustomerAddPage,{customer: customer});
  }

  saveCustomer(customer: Customer){
     this.contactService.saveContact(customer);
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
      this.request.currentmode = 'READ';
      this.request.rds_selectedids = customer.Id;
      this.request.fixedAction = "FixedAction.ACTION_DELETE";
      this.request.pageID = "customers";
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
      this.navCtrl.pop();
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
