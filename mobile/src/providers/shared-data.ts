import { Injectable } from '@angular/core';

/*
  Generated class for the Loader provider.

  See https://angular.io/docs/ts/latest/guide/dependency-injection.html
  for more info on providers and Angular 2 DI.
*/
@Injectable()
export class SharedService {
  //will be removed on ap relaunch
  private sharedData;

  constructor() { 
    this.sharedData = {};
  }

  saveData(key: string, value: any){
     this.sharedData[key] = value;
  }
  
  getData(key: string): any{
    return this.sharedData[key];
  }

  removeData(key: string): any{
    delete this.sharedData[key];
  }
  

}
