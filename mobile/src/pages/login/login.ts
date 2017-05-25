import { Component } from '@angular/core';
import { NavController} from 'ionic-angular';
import { Login, LoginRequest, LoginResponse } from './login.model';
import { HTTPService, Loader } from '../../providers/';
import { HomePage } from '../home/home';
import { Storage } from '@ionic/storage';

/*
  Generated class for the Login page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'pm-login',
  templateUrl: 'login.html'
})
export class LoginPage {
  private model: Login = new Login();
  private response:LoginResponse;
  private errorMessage;
  constructor(public navCtrl: NavController,private http:HTTPService,
    private loader:Loader, private storage: Storage) {
      this.model.password="abc123";
      this.model.username="manager@atstar";
    }

  ionViewDidLoad() {
    console.log('Hello Login Page');
  }

  login():void{
    this.loader.presentLoader();
    this.errorMessage = null;
    let loginRequest = new LoginRequest();
    loginRequest.pageID = 'login';
    loginRequest.dataObject = this.model;
    this.http.processServerRequest("post",loginRequest).subscribe(
                     res => this.loginSuccess(res),
                     error =>  this.loginError(error));               
  }

  loginSuccess(response):void{
    this.loader.dismissLoader();
    if(Object.keys(response).length == 0){
       this.errorMessage = "Username or password is incorrect"; 
       return ;
    }
    this.response = response.dataObject;
    this.storage.set('user', response.dataObject.Username);
     this.storage.set('division', response.dataObject.LoggedInDivision);
    this.http.setAuthToken(response.dataObject.AuthToken);
    this.navCtrl.setRoot(HomePage);
    
  }

  loginError(error){
    this.http.setAuthToken(null);
    this.loader.dismissLoader();
    this.errorMessage = "Username or password is incorrect";
  }
 

}
