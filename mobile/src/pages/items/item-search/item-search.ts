import { Component } from '@angular/core';
import { NavController, ToastController, NavParams } from 'ionic-angular';
import { Item, Product, ItemSearchRequest, ItemSearchResponse} from '../';
import { HTTPService, FilterProvider, AllFilter, FilterDetails, PromptService } from '../../../providers/';
import { HomePage } from '../../home/home';
import { ItemSearchResult } from '../';
import { BarcodeScanner } from '@ionic-native/barcode-scanner';
import * as _ from 'lodash';

/*
  Generated class for the ItemSearch page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-item-search',
  templateUrl: 'item-search.html'
})
export class ItemSearch {
  private model:Item = new Item();
  private searchString:string;
  private request: ItemSearchRequest;
  private response: ItemSearchResponse;
  private errorMessage:string;
  private isAssociateItems: Boolean;
  private availableFilters: Array<AllFilter>;
  private filterName: string;
  

  constructor(public navCtrl: NavController, private http:HTTPService
  ,  private toastCtrl: ToastController, private barcodeScanner: BarcodeScanner,
  private params: NavParams, private filter: FilterProvider, private promptCtrl: PromptService) {
    this.model.Product = new Product();
    this.filter.filtersForPage$.subscribe(res => {this.updateFilters(res)});
    this.filter.filtersDetails$.subscribe(res => {this.updateFilterValues(res)});
    this.filter.filtersSave$.subscribe(res => {this.updateFilterAfterSave()});
    this.promptCtrl.prompt$.subscribe(res => {this.saveFilterValue(res)});
    this.filterName = "0";
    this.filter.getAllFiltersForPage("com.rainbow.crm.item.controller.SkuCompleteListController");
    this.isAssociateItems = this.params.get("isAssociateItems");
  }

  ionViewDidLoad() {
    console.log('Hello ItemSearch Page');
  }

  goHome():void{
      this.navCtrl.setRoot(HomePage);
  }

  scanItem(): void{
    var self = this;
    this.barcodeScanner.scan().then((barcodeData) => {
        self.searchString = barcodeData.text;
        self.doSearch();
     }, (err) => {
       let toast = self.toastCtrl.create({
          message: 'Scan was not complete. Kindly retry',
          duration: 2000,
          position: 'top'
        });
       toast.present();
    });
  }

  NoItemsFoundToast():any{
     let toast = this.toastCtrl.create({
      message: 'No Items found',
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

  done(){
    this.navCtrl.popToRoot();
  }

  doSearch():void{
      this.errorMessage = null;
      this.request = new ItemSearchRequest();
      this.request.currentmode = 'READ';
      this.request.fixedAction = "FixedAction.NAV_FIRSTPAGE";
      this.request.pageID = "skucompletelist";
      this.request.filter = [];
      this.request.hdnPage = 0;
      let filter = this.getFilters();
      this.request.filter.push(filter);

      this.http.processServerRequest("post",this.request, true).subscribe(
                     res => this.itemSearchSuccess(res, this.request.filter.slice(0)),
                     error =>  this.itemSearchError(error));  
  }


  itemSearchSuccess(response, filter):void{
    this.response = response;
    if(this.response.result == "failure"){
       this.errorMessage = "Failed to search items"; 
       return ;
    }
    if(this.response.dataObject.length == 0){
       this.NoItemsFoundToast();
       return ;
    }
    this.navCtrl.push(ItemSearchResult, {items:this.response.dataObject, filter:filter,
      fetchedResults: this.response.fetchedRecords, numberOfResults:this.response.availableRecords,
      isAssociateItems:this.isAssociateItems});
  }
 

  itemSearchError(error){
    this.errorMessage = "Failed to search items";
  }

  getFilters():Array<any>{
      let filters = [];
      for(let data in this.model){
        if(this.model[data] && typeof this.model[data] == "string" && this.model[data].length > 0){
          filters.push({
	      	  "field" :data ,
	   	      "operator" :"EQUALS",
	   	      "value":this.model[data]
	         });
        }
        else if(this.model[data] && Object.keys(this.model[data]).length > 0){
           var subFilters = this.getSubFilters(data, this.model[data]);
           if(subFilters.length > 0)
             filters = filters.concat(subFilters);
        }
      }
      return filters;
  }

  getSubFilters(key,model):Array<any>{
     let filters = [];
     for(let data in model){
        if(model[data] && model[data].length > 0){
          filters.push({
	      	  "field" :key+"."+data ,
	   	      "operator" :"EQUALS",
	   	      "value":model[data]
	         });
        }
      }
     return filters;
  }

  updateFilters(res: Array<AllFilter>){
    this.availableFilters = res;
  }

  updateFilterValues(res: Array<FilterDetails>){
     for(let i=0; i< res.filter.length; i++){
       _.set(this.model,res.filter[i].field, res.filter[i].value);
     }
  }

  saveFilterValue(filterName: string){
    if(filterName.length == 0){
      return;
    }else{
      this.filterName = filterName;
      this.model.FilterName = this.filterName;
      this.filter.saveFilter( this.getFilters(),"skucompletelist");
    }
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
    this.model = new Item();
    this.model.Product = new Product();
    if(this.filterName && this.filterName != "0"){
      this.filter.getFilterDetails("com.rainbow.crm.item.controller.SkuCompleteListController", this.filterName);
    }
  }

  onReset(){
    this.model = new Item();
    this.model.Product = new Product();
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
      this.filter.saveFilter( this.getFilters(),"skucompletelist");
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
