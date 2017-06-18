import { Injectable } from '@angular/core';
import { Storage } from '@ionic/storage';
import { HTTPService, BaseSearchRequest } from './';
import { Subject }    from 'rxjs/Subject';
import { Observable }    from 'rxjs/Observable';
import 'rxjs/add/observable/throw';
/*
  Generated class for the Loader provider.

  See https://angular.io/docs/ts/latest/guide/dependency-injection.html
  for more info on providers and Angular 2 DI.
*/
@Injectable()
export class FilterProvider {
  private filtersForPage= new Subject<any>();
  private filtersDetails= new Subject<any>();
  filtersForPage$ = this.filtersForPage.asObservable();
  filtersDetails$ = this.filtersDetails.asObservable();

  constructor(private http: HTTPService) { }
  getAllFiltersForPage(page: string){
      let request = new BaseSearchRequest();
      var query = ["page="+page,"ajxService=allFilters"];
      this.http.processCustomUrlServerRequest(query.join('&'),"post",request, true).subscribe(
                     res =>  {
                       debugger;
                      },
                     error =>  {});
  }
}
