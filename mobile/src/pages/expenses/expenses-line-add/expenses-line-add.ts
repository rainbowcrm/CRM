import { Component } from '@angular/core';
import { NavController, ToastController , NavParams, ModalController} from 'ionic-angular';
import { DatePipe } from '@angular/common';
import {ExpenseVoucherLine, ExpenseHead, FetchExpenseHead} from '../';
import { HTTPService, SharedService } from '../../../providers/';
import { ImagePickerService } from '../../../plugins/';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-expenses-line-add',
  templateUrl: 'expenses-line-add.html'
})
export class ExpensesLineAddPage {
  private model:ExpenseVoucherLine;
  private errorMessage:string;
  private expenseHeads: Array<ExpenseHead>;
  private expenseImage: string = "";

  constructor(public navCtrl: NavController,private http:HTTPService, private toastCtrl: ToastController,
    private params: NavParams,private imagePicker: ImagePickerService, private sharedData: SharedService) {
      let expenseLine = params.get("expenseLine");
      if(expenseLine){
        this.model = expenseLine;
        if(this.model.File1Data){
          let base64Image = 'data:image/jpeg;base64,' + this.model.File1Data;
          this.expenseImage = base64Image;
        }
      }else{
         this.model = new ExpenseVoucherLine();
         this.model.ExpenseHead = new ExpenseHead(); 
      }
    }

  ionViewDidLoad() {
     this.http.processCustomUrlServerRequest("ajxService=allExpenseHeads","post",new FetchExpenseHead(), true).subscribe(
                     res =>  {
                       this.processExpenseHeads(res);
                      },
                   error =>  {this.showToast("Failed to fetch expenses heads")});
  }

  processExpenseHeads(heads){   
    this.expenseHeads = heads.allExpenseHeads;
  }
  
  addExpenseLine():void{
     this.model.Deleted = "false";
     this.sharedData.saveData("expenseLine",this.model);
     this.navCtrl.pop();
  }

  getImageFromLibrary():void{
     this.imagePicker.getPictureFromGallery(300, 300).then((result)=>{
      this.model.File1Data = result;
      let base64Image = 'data:image/jpeg;base64,' + result;
      this.expenseImage = base64Image;
     },(err)=>{
         let toast = this.toastCtrl.create({
         message: 'Failed to load the image',
         duration: 2000,
         position: 'top'
        });
        toast.present();
     })
  }

  getImageFromCamera():void{
     this.imagePicker.getOnePicture(300, 300).then((result)=>{
      this.model.File1Data = result;
      let base64Image = 'data:image/jpeg;base64,' + result;
      this.expenseImage = base64Image;
     },(err)=>{
         let toast = this.toastCtrl.create({
         message: 'Failed to load the image',
         duration: 2000,
         position: 'top'
        });
        toast.present();
     })
  }

  showToast(msg: string):any{
     let toast = this.toastCtrl.create({
      message: msg,
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

}
