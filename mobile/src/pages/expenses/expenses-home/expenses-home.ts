import { Component } from '@angular/core';
import { NavController, ToastController } from 'ionic-angular';
import { HTTPService,FilterProvider, AllFilter, FilterDetails, PromptService } from '../../../providers/';
import { HomePage } from '../../home/home';
import { ExpensesAddPage } from '../';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-expenses-home',
  templateUrl: 'expenses-home.html'
})
export class ExpenseHomePage {
  private user;
  private model: any;
  private errorMessage:string;
  private filterName: string;
  private availableFilters: Array<AllFilter>;

  constructor(private http:HTTPService,private toastCtrl: ToastController,
              private navCtrl: NavController, private filter: FilterProvider, private promptCtrl: PromptService) {
      this.model = new Object();
      this.filter.filtersForPage$.subscribe(res => {this.updateFilters(res)});
      this.filter.filtersDetails$.subscribe(res => {this.updateFilterValues(res)});
      this.filter.filtersSave$.subscribe(res => {this.updateFilterAfterSave()});
      this.promptCtrl.prompt$.subscribe(res => {this.saveFilterValue(res)});
      this.filterName = "0";
      this.filter.getAllFiltersForPage("com.rainbow.crm.expensevoucher.controller.ExpenseVoucherListController");
  }

  goHome():void{
      this.navCtrl.setRoot(HomePage);
  }

  addExpense(){
    this.navCtrl.push(ExpensesAddPage);
  }

  updateFilters(res: Array<AllFilter>){
    this.availableFilters = res;
  }

  updateFilterValues(res: Array<FilterDetails>){
     for(let i=0; i< res.filter.length; i++){
       this.model[res.filter[i].field] = res.filter[i].value;
     }
  }

  saveFilterValue(filterName: string){
    if(filterName.length == 0){
      return;
    }else{
      this.filterName = filterName;
      this.model.FilterName = this.filterName;
      this.filter.saveFilter( this.getFilters(),"expensevoucher");
    }
  }
  getFilters():Array<any>{
      let filters = [];
      for(let data in this.model){
        if(this.model[data])
           filters.push({
	      	  "field" :data ,
	   	      "operator" :"EQUALS",
	   	      "value":this.model[data]
	         });
      }
      return filters;
  }

  updateFilterAfterSave(){
    let newFilter = new AllFilter();
    if(this.isFilterExist(this.filterName)){
      return;
    }
    newFilter.filterId = this.filterName;
    newFilter.filterValue = this.filterName;
    this.availableFilters.push(newFilter);
  }

  isFilterExist(filterName: string): boolean{
    let filterExists = false;
    for(let i=0; i<this.availableFilters.length ; i++){
      if(this.availableFilters[i].filterValue == filterName){
        filterExists = true;
        break;
      }
    }
    return filterExists;
  }

  fetchFilterValues(){
    this.model = new Object();
    if(this.filterName && this.filterName != "0"){
      this.filter.getFilterDetails("com.rainbow.crm.expensevoucher.controller.ExpenseVoucherListController", this.filterName);
    }
  }

  onReset(){
    this.model = new Object();
    this.filterName = "0";
  }

  onAdd(){
    if(!this.filterName){
       this.showToast("Please choose proper filter from drop down");
    }else if( this.filterName == "0"){
      let prompt = this.promptCtrl.displayPrompt("Filter Name","Choose a filter name","OK");
      prompt.present();
    }else{
      this.model.FilterName = this.filterName;
      this.filter.saveFilter( this.getFilters(),"expensevoucher");
    }
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
