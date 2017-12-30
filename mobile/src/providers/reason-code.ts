import { Injectable } from '@angular/core';
import { Storage } from '@ionic/storage';
import { HTTPService, BaseSearchRequest, SharedService } from './';
import { Subject }    from 'rxjs/Subject';
import { Observable }    from 'rxjs/Observable';
import 'rxjs/add/observable/throw';
/*
  Generated class for the Loader provider.

  See https://angular.io/docs/ts/latest/guide/dependency-injection.html
  for more info on providers and Angular 2 DI.
*/
@Injectable()
export class ReasonCodeProvider {
  private finiteValueSource= new Subject<any>();
  private reasonCodeSource= new Subject<any>();
  private request = new BaseSearchRequest();
  finiteValueSource$ = this.finiteValueSource.asObservable();
  reasonCodeSource$ = this.reasonCodeSource.asObservable();

  constructor(private storage: Storage, private http: HTTPService, private sharedService: SharedService) { }

  getFiniteValues(isSilent?:boolean){
     var reasonCode = this.getFiniteValuesFromStorage();
     if(reasonCode){
       this.finiteValueSource.next(reasonCode);
     }else{
      this.http.processCustomUrlServerRequest("ajxService=allFiniteValues","post",this.request, true, isSilent).subscribe(
                     res =>  {
                       this.processFiniteValues(res);
                      },
                     error =>  {});
     }
  }

  private processFiniteValues(res): void{
     let reasonCode = {};
     let allFiniteValues = res.allFiniteValues;
     if(allFiniteValues){
       for(let i=0; i<allFiniteValues.length;i++){
         if(!reasonCode[allFiniteValues[i]["Type"]]){
           reasonCode[allFiniteValues[i]["Type"]] = [];
         }
         reasonCode[allFiniteValues[i]["Type"]].push(allFiniteValues[i]);
       }
     }    
     this.saveFiniteValuesIntoStorage(reasonCode);
     this.finiteValueSource.next(reasonCode)
  }

  private getFiniteValuesFromStorage(): void{
    return this.sharedService.getData("finiteValues");
  }

  private saveFiniteValuesIntoStorage(rc): void{
    this.sharedService.saveData("finiteValues",rc);
  }

  getReasonCodes(type){
      this.http.processCustomUrlServerRequest("ajxService=searchReasonCode&type="+type,"post",this.request, true).subscribe(
                     res =>  {
                       this.processReasonCodeValues(res);
                      },
                     error =>  {});
  }

  private processReasonCodeValues(res): void{
     let allReasonCodes = res.reasonCodes;
     if(allReasonCodes){
       this.reasonCodeSource.next(allReasonCodes)
     }    
  }

}
