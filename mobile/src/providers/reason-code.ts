import { Injectable } from '@angular/core';
import { Storage } from '@ionic/storage';
import { HTTPService, BaseSearchRequest } from './';
import { Subject }    from 'rxjs/Subject';
import { Observable }    from 'rxjs/Observable';

/*
  Generated class for the Loader provider.

  See https://angular.io/docs/ts/latest/guide/dependency-injection.html
  for more info on providers and Angular 2 DI.
*/
@Injectable()
export class ReasonCodeProvider {
  private reasonCodeSource= new Subject<any>();
  private request = new BaseSearchRequest();
  reasonCodeSource$ = this.reasonCodeSource.asObservable();

  constructor(private storage: Storage, private http: HTTPService) { }

  getReasonCode(){
     var reasonCode = this.getReasonCodeFromStorage();
     if(reasonCode){
       this.reasonCodeSource.next(reasonCode);
     }else{
      this.http.processCustomUrlServerRequest("ajxService=allReasoncodes","post",this.request, true).subscribe(
                     res =>  this.reasonCodeSource.next(reasonCode),
                     error =>   this.reasonCodeSource.next());
     }
  }

  private getReasonCodeFromStorage(): void{

  }

  private saveReasonCodeIntoStorage(): void{
    
  }

}
