import { Injectable } from '@angular/core';
import { Storage } from '@ionic/storage';
import { HTTPService, BaseSearchRequest } from './';
import { Subject }    from 'rxjs/Subject';
import { Observable }    from 'rxjs/Observable';
import { ToastController} from 'ionic-angular';
import 'rxjs/add/observable/throw';
/*
  Generated class for the Loader provider.

  See https://angular.io/docs/ts/latest/guide/dependency-injection.html
  for more info on providers and Angular 2 DI.
*/
export class AllFilter{
  filterId: string;
  filterValue: string;
}

export class FilterDetails{
  field: string;
  value: string;
}

@Injectable()
export class FilterProvider {
  private filtersForPage= new Subject<any>();
  private filtersDetails= new Subject<any>();
  private filtersSave= new Subject<any>();
  filtersForPage$ = this.filtersForPage.asObservable();
  filtersDetails$ = this.filtersDetails.asObservable();
  filtersSave$ = this.filtersSave.asObservable();

  constructor(private http: HTTPService, private toastCtrl: ToastController) { }
  getAllFiltersForPage(page: string){
      let request = new BaseSearchRequest();
      var query = ["page="+page,"ajxService=allFilters"];
      this.http.processCustomUrlServerRequest(query.join('&'),"post",request, true).subscribe(
                     res =>  {
                       this.processAllFilter(res);
                      },
                     error =>  {
                       this.showToast("Failed to fetch filters");
                     });
  }

  private processAllFilter(res): void{
    let availableFilters = [];
    let allFiltersKeys = Object.keys(res);
     if(res){
       for(let i=0; i<allFiltersKeys.length;i++){
         let filter = new AllFilter();
         filter.filterId = allFiltersKeys[i];
         filter.filterValue = res[allFiltersKeys[i]];
         availableFilters.push(filter)
       }
     }    
     this.filtersForPage.next(availableFilters);
  }

  getFilterDetails(page: string, filterName: string){
      let request = new BaseSearchRequest();
      var query = ["page="+page,"ajxService=filterSearch","filterName="+filterName];
      this.http.processCustomUrlServerRequest(query.join('&'),"post",request, true).subscribe(
                     res =>  {
                       this.processFilterDetails(res);
                      },
                     error =>  {
                       this.showToast("Failed to fetch filter values");
                     });
  }

  processFilterDetails(res){
    this.filtersDetails.next(res);
  }

  

  saveFilter(page: string, data: any, pageId: string){
    let request = new BaseSearchRequest();
    request.fixedAction = "FixedAction.ACTION_FILTERSAVE";
    request.hdnPage = 0;
    request.pageID = pageId;
    request.currentmode = "READ";
    request.filter = data;
    this.http.processServerRequest("post",request, true).subscribe(
                     res => this.filterSaveSuccess(res),
                     error =>  this.filterSaveError(error)); 
  }

  filterSaveSuccess(response):void{
    this.showToast("Filter saved");
  }
 

  filterSaveError(error){
    this.showToast("Error saving filter, Kindly try again later");
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

