import { Component, ViewChild } from '@angular/core';
import { NavController, ToastController , NavParams, ModalController, Slides} from 'ionic-angular';
import { DatePipe } from '@angular/common';
import { ExpenseVoucher,ExpenseVoucherLine, Division, SalesAssoicate, FetchExpenseHead, AddExpenseVoucherRequest, ExpensesLineAddPage } from '../';
import { HTTPService, ReasonCodeProvider, SharedService } from '../../../providers/';
import { Storage } from '@ionic/storage';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-expenses-add',
  templateUrl: 'expenses-add.html'
})
export class ExpensesAddPage {
  private model:ExpenseVoucher;
  private errorMessage:string;
  private expenseLines: Array<ExpenseVoucherLine>;
  @ViewChild(Slides) slides: Slides;

  constructor(public navCtrl: NavController,private http:HTTPService, private toastCtrl: ToastController,
    private params: NavParams,private sharedData: SharedService,
    private datePipe: DatePipe, private storage: Storage) {
      this.model = new ExpenseVoucher();
      this.model.Division = new Division();
      this.model.SalesAssoicate= new SalesAssoicate();
      this.expenseLines = new Array();  
      this.model.ExpenseVoucherLines = this.expenseLines;
      this.model.AssociateComments = ""; 
    }

  ionViewDidEnter() {

     this.storage.ready().then(() => {
       // Or to get a key/value pair
       this.storage.get('expenseLines').then((val) => {
         if(val){
           this.expenseLines =  val;
         }
         let expenseLine = this.sharedData.getData("expenseLine");
         if(expenseLine){
           this.processExpenseLine(expenseLine);
           this.sharedData.removeData("expenseLine");
         }
         this.model.RequestedTotal = this.getTotalFor("RequestedAmount");
       })
      this.storage.get('user').then(val => this.model.SalesAssoicate.UserId = val);
      this.storage.get('division').then(val => this.model.Division = val);
     });
  }

  

  private processExpenseLine(expense: ExpenseVoucherLine){
    if(!expense.LineNumber){
      expense.LineNumber = this.expenseLines.length+1 + "";
      this.expenseLines.push(expense);
    }
    else{
      var index = parseInt(expense.LineNumber);
      this.expenseLines.splice(index-1, 1, expense);
    }
    this.storage.set("expenseLines",this.expenseLines);
  }

  deleteExpenseLine(lineNumber){
    this.expenseLines.splice(lineNumber-1, 1);
    //rearrange line numbers
    for(let i=0;i<this.expenseLines.length;i++){
      this.expenseLines[i].LineNumber = i+1 + "";
    }
    if(this.slides.isEnd()){
      //temp hack
      this.slides.slidePrev();
    }
    this.rearrageLineNumbers();
    this.model.RequestedTotal = this.getTotalFor("RequestedAmount");
    this.storage.set("expenseLines",this.expenseLines);
    
  }

  private rearrageLineNumbers(){
      for(let i=0; i< this.expenseLines.length;i++){
        this.expenseLines[i].LineNumber = i+1+"";
      }
  }

  private getTotalFor(key: string){
     let total = 0;
      for(let i=0; i< this.expenseLines.length;i++){
        total = total+parseFloat(this.expenseLines[i][key])
      }
      return total;
  }
  
  addExpense():void{
       this.errorMessage = null;
       let addExpenseReq = new AddExpenseVoucherRequest();
       this.model.ExpenseVoucherLines = this.expenseLines;
       this.model.ExpenseDate = this.datePipe.transform(new Date(),"yyyy-MM-dd");
       this.model.Open = "false";
       addExpenseReq.fixedAction = "FixedAction.ACTION_CREATE";
       addExpenseReq.pageID = 'newexpensevoucher';
       addExpenseReq.currentmode = "CREATE";
       addExpenseReq.dataObject = this.model;
       this.http.processServerRequest("post",addExpenseReq, true).subscribe(
                     res => this.expenseAddSuccess(res),
                     error =>  this.expenseAddError(error));  
  }

  expenseAddSuccess(response):void{
    if(response.result == "failure"){
       this.errorMessage = "Failed to create new expense"; 
       return ;
    }
    this.navCtrl.popToRoot();
    this.showSuccessToast();
    
  }

  showSuccessToast():any{
     let toast = this.toastCtrl.create({
      message: 'New expense voucher was added successfully',
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

  expenseAddError(error){
    this.errorMessage = "Failed to create new expense voucher";
  }
  addExpenseLine(){
    this.navCtrl.push(ExpensesLineAddPage);
  }
  editExpenseLine(line: ExpenseVoucherLine){
    this.navCtrl.push(ExpensesLineAddPage, {"expenseLine": line});
  }

}
