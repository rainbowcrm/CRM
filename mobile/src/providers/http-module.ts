import { Injectable } from '@angular/core';
import { Loader } from './';
import { Http, Response,Headers, RequestOptions } from '@angular/http';
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
  constructor(public http: Http, private loader: Loader) {
    //this.url = 'http://ec2-35-154-225-199.ap-south-1.compute.amazonaws.com:8080/primuscrm/rdscontroller';//TO BE REMOVED.. may be config
    this.url = 'http://localhost:8080/rdscontroller';
  }
  processServerRequest (restType:string,data:any,auth?:boolean, isSilent?:boolean): Observable<any[]> {
    if(!isSilent){
      this.loader.presentLoader();
    }
    if(auth){
      data.authToken = this.authToken;
      data.AuthToken = this.authToken;
    }
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    switch(restType){
      case "get": return this.http.get(this.url)
                    .map(this.extractData.bind(this))
                    .catch(this.handleError.bind(this));
      case "post": return this.http.post(this.url, data, options)
                    .map(this.extractData.bind(this))
                    .catch(this.handleError.bind(this));
    }
    
  }
  processCustomUrlServerRequest (url:string, restType:string,data:any,auth?:boolean): Observable<any[]> {
    var newUrl = this.url+"?"+url;
    if(auth){
      data.authToken = this.authToken;
    }
    let headers = new Headers({ 'Content-Type': 'plain/text' });
    let options = new RequestOptions({ headers: headers });
    switch(restType){
      case "get": return this.http.get(newUrl)
                    .map(this.extractData.bind(this))
                    .catch(this.handleError.bind(this));
      case "post": return this.http.post(newUrl, data, options)
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
    return JSON.parse(res._body) || { };
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
