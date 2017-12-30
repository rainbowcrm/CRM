import { Injectable } from '@angular/core';
import { Loader } from './';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { Observable }     from 'rxjs/Observable';

/*
  Generated class for the HttpModule provider.

  See https://angular.io/docs/ts/latest/guide/dependency-injection.html
  for more info on providers and Angular 2 DI.
*/
@Injectable()
export class HTTPService {
  private url; 
  private authToken;
  constructor(public http: HttpClient, private loader: Loader) {
    //this.url = 'http://119.18.52.32:8080/primuscrm/rdscontroller';
    //this.url = 'http://10.0.2.2:10210/controller';
    this.url = 'http://localhost:10210/controller';
  }
  processServerRequest (restType:string,data:any,auth?:boolean, isSilent?:boolean): Observable<any> {
    if(!isSilent){
      this.loader.presentLoader();
    }
    if(auth){
      data.authToken = this.authToken;
      data.AuthToken = this.authToken;
    }
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    switch(restType){
      case "get": return this.http.get(this.url)
                    .map(this.extractData.bind(this))
                    .catch(this.handleError.bind(this));
      case "post": return this.http.post(this.url, data, {headers})
                    .map(this.extractData.bind(this))
                    .catch(this.handleError.bind(this));
    }
    
  }
  processCustomUrlServerRequest (url:string, restType:string,data:any,auth?:boolean, isSilent?:boolean): Observable<any> {
    var newUrl = this.url+"?"+url;
    if(auth){
      data.authToken = this.authToken;
    }
    if(!isSilent){
      this.loader.presentLoader();
    }
    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    switch(restType){
      case "get": return this.http.get(newUrl)
                    .map(this.extractData.bind(this))
                    .catch(this.handleError.bind(this));
      case "post": return this.http.post(newUrl, data, {headers})
                    .map(this.extractData.bind(this))
                    .catch(this.handleError.bind(this));
    }
    
  }
  setAuthToken(token: string){
    this.authToken = token;
  }
  getAuthToken():string{
    return this.authToken;
  }
  private extractData(res: any) {
    this.loader.dismissLoader();
    return res || { };
  }
  private handleError (error: any) {
    // In a real world app, we might use a remote logging infrastructure
    // We'd also dig deeper into the error to get a better message
    this.loader.dismissLoader();
    let errMsg = (error.message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    return Observable.throw(errMsg);
  }

}
