import { Component } from '@angular/core';
import { NavParams, NavController } from 'ionic-angular';

import { Customer, CustomerAddPage } from '../';
import { HTTPService } from '../../../providers/';

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
  constructor(private params: NavParams,private http:HTTPService,
              private navCtrl: NavController) {
    this.customers = this.params.get('customers');
  }

  ionViewDidLoad() {
     
  }

  getCustomerAddress(customer){
     let customerAddr = [];
     var keys = ["Address1","Address2","City","ZipCode","Landmark"];
     for(let key in keys){
         if(customer[key]){
           customerAddr.push(customer[key])
         }
     }
     if(customerAddr.length == 0){
     return "no information available";
     }
    else{
      return customerAddr.join(" ,");
    }

  }

  editCustomer(customer: Customer){
     this.navCtrl.push(CustomerAddPage,{customer: customer});
  }

}
