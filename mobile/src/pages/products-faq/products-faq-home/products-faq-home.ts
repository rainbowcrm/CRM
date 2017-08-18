import { Component } from '@angular/core';
import { NavController, PopoverController, ToastController } from 'ionic-angular';
import { Product, GetProductsRequest, GetProductDetailsRequest, ProductsDetailsList } from '../';
import { HTTPService } from '../../../providers/';
import { HomePage } from '../../home/home';
import { Storage } from '@ionic/storage';
import { SortPopOverPage }   from '../../../common/sort-helper/sort-popover';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-products-faq-home',
  templateUrl: 'products-faq-home.html'
})
export class ProductsList {
  private products:Array<Product> = [];
  private request: GetProductsRequest;
  private pageNumber: number;
  private fetchedResults: number;
  private numberOfResults: number;
  private sortCondition: any;

  constructor(private http:HTTPService,
              private navCtrl: NavController, private popoverCtrl: PopoverController, private toastCtrl: ToastController) {
    this.pageNumber = -1;
    this.fetchedResults = 0;
    this.numberOfResults = 1;
  }

  ionViewDidEnter() {
     this.getProducts({complete:function(){}});
  }

  goHome():void{
      this.navCtrl.setRoot(HomePage);
  }

  onSort(){
    var sortType = [{key: "name", value: "Name"}];
    let popover = this.popoverCtrl.create(SortPopOverPage, {sortConditions: sortType});
    popover.present({});
    popover.onDidDismiss(this.dismissSortPopover.bind(this))
  }

  dismissSortPopover(data){
    if(data){
      this.sortCondition = {};
      this.sortCondition.sortCondt = data.selectedValue;
      this.sortCondition.isAscending = data.isAscending;
      this.pageNumber = -1;
      this.fetchedResults = 0;
      this.products = [];
      this.getProducts({complete:function(){}});
    }
  }


  getProducts(infiniteScroll) {
      if(this.fetchedResults >= this.numberOfResults ){
        infiniteScroll.complete();
        return;
      }
      this.request = new GetProductsRequest();
      this.request.currentmode = 'READ';
      if(this.pageNumber < 0){
        this.request.fixedAction = "FixedAction.NAV_FIRSTPAGE";
      }else{
        this.request.fixedAction = "FixedAction.NAV_NEXTPAGE";
      }
      if(this.sortCondition){
        this.request.rds_sortdirection = this.sortCondition.isAscending ? "ASCENDING":"DESCENDING";
        this.request.rds_sortfield = this.sortCondition.sortCondt.key;
      }
      this.request.hdnPage = ++this.pageNumber;
      this.request.pageID = "products";
      this.http.processServerRequest("post",this.request, true, this.pageNumber != 0).subscribe(
                     res => this.getProductsSuccess(res, infiniteScroll),
                     error =>  this.getProductsError(error, infiniteScroll));
  }

  getProductsSuccess(response, infiniteScroll):void{
    if(response.result == "failure"){
       infiniteScroll.complete();
       return ;
    }
    if(response.dataObject.length == 0){
      infiniteScroll.complete();
       return ;
    }
    this.products = this.products.concat(response.dataObject);
    this.fetchedResults += response.fetchedRecords;
    this.numberOfResults = response.availableRecords;
    infiniteScroll.complete();
    
  }
 

  getProductsError(error, infiniteScroll){
    infiniteScroll.complete();
  }

  itemSelected(product: Product){
    this.getProductDetails(product);
  }

  getProductDetails(product: Product) {
      let detailsRequest = new GetProductDetailsRequest();
      detailsRequest.currentmode = 'READ';
      detailsRequest.fixedAction = "FixedAction.ACTION_READ";
      detailsRequest.pageID = "productfaqs";
      detailsRequest.dataObject = {Product: {Name:product.Name}};
      this.http.processServerRequest("post",detailsRequest, true).subscribe(
                     res => this.getProductDetailsSuccess(res),
                     error =>  this.getProductDetailsError(error));
  }

  getProductDetailsSuccess(response):void{
    if(response.dataObject.ProductFAQs.length == 0){
      this.showToast("No FAQ's found");
      return;
    }
   this.navCtrl.push(ProductsDetailsList, {productDetails: response.dataObject}); 
  }

  getProductDetailsError(error){
    this.showToast("Failed to fetch product details");
  }

  showToast(message: string):any{
     let toast = this.toastCtrl.create({
      message: message,
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

}
