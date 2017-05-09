import { Injectable } from '@angular/core';
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
  constructor(public http: Http) {
    this.url = 'http://ec2-35-154-225-199.ap-south-1.compute.amazonaws.com:8080/primuscrm/rdscontroller';//TO BE REMOVED.. may be config
  }
  processServerRequest (restType:string,data:any,auth?:boolean): Observable<any[]> {
    if(auth){
      data.authToken = this.authToken;
    }
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    switch(restType){
      case "get": return this.http.get(this.url)
                    .map(this.extractData)
                    .catch(this.handleError);
      case "post": return this.http.post(this.url, data, options)
                    .map(this.extractData)
                    .catch(this.handleError);
    }
    
  }
  setAuthToken(token: string){
    this.authToken = token;
  }
  getAuthToken():string{
    return this.authToken;
  }
  private extractData(res: any) {
    return JSON.parse(res._body) || { };
  }
  private handleError (error: any) {
    // In a real world app, we might use a remote logging infrastructure
    // We'd also dig deeper into the error to get a better message
    let errMsg = (error.message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    return Observable.throw(errMsg);
  }

}
