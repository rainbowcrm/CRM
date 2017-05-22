import { Component } from '@angular/core';
import { NavController, ToastController , NavParams} from 'ionic-angular';

import { Customer, CustomerAddRequest, CustomerAddResponse } from '../';
import { HTTPService } from '../../../providers/';
import { ImagePickerService } from '../../../plugins/';

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
  private customerImage: string;

  constructor(public navCtrl: NavController,private http:HTTPService, private toastCtrl: ToastController,
    private params: NavParams, private imagePicker: ImagePickerService) {
      this.model = this.params.get('customer');
      if(this.model) {
        this.isEdit = true;
        if(this.model.Base64Image && this.model.Base64Image.length > 0){
           let base64Image = 'data:image/jpeg;base64,' + this.model.Base64Image;
           this.customerImage = base64Image;
        }
      }else{
        this.model = new Customer();
      }
      this.model.Filename = "test.jpg"
    }

  ionViewDidLoad() {
    this.pageTitle = this.isEdit?"Edit Customer": "Add Customer";
  }

  addCustomer():void{
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

  addCustomerImage():void{
    this.imagePicker.getOnePicture(100, 100).then((result)=>{
      let base64Image = 'data:image/jpeg;base64,' + result;
      this.customerImage = base64Image;
      this.model.Base64Image = result;
    },(err)=>{
        let toast = this.toastCtrl.create({
        message: 'Failed to load the image',
        duration: 2000,
        position: 'top'
       });
       toast.present();
    })
  }

  customerAddSuccess(response):void{
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
    this.errorMessage = "Failed to create customer";
  }

}
