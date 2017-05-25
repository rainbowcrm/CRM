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
  private reasonCodeSource= new Subject<any>();
  private request = new BaseSearchRequest();
  reasonCodeSource$ = this.reasonCodeSource.asObservable();

  constructor(private storage: Storage, private http: HTTPService, private sharedService: SharedService) { }

  getReasonCode(){
     var reasonCode = this.getReasonCodeFromStorage();
     if(reasonCode){
       this.reasonCodeSource.next(reasonCode);
     }else{
      this.http.processCustomUrlServerRequest("ajxService=allReasoncodes","post",this.request, true).subscribe(
                     res =>  {
                       this.processReasonCodes(res);
                      },
                     error =>  {});
     }
  }

  private processReasonCodes(res): void{
     let reasonCode = {};
     let allReasoncodes = res.allReasonCodes;
     if(allReasoncodes){
       for(let i=0; i<allReasoncodes.length;i++){
         if(!reasonCode[allReasoncodes[i]["Type"]]){
           reasonCode[allReasoncodes[i]["Type"]] = [];
         }
         reasonCode[allReasoncodes[i]["Type"]].push(allReasoncodes[i]);
       }
     }    
     this.saveReasonCodeIntoStorage(reasonCode);
     this.reasonCodeSource.next(reasonCode)
  }

  private getReasonCodeFromStorage(): void{
    return this.sharedService.getData("reasonCodes");
  }

  private saveReasonCodeIntoStorage(rc): void{
    this.sharedService.saveData("reasonCodes",rc);
  }

}
