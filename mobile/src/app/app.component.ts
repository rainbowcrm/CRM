import { Component, ViewChild } from '@angular/core';
import { Platform, Nav, AlertController } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { LoginPage } from '../pages/login/login';
import { HomePage } from '../pages/home/home';
import { SplashScreen } from '@ionic-native/splash-screen';



@Component({
  template: `<ion-nav [root]="rootPage"></ion-nav>`,
  providers:[StatusBar]
})
export class PSApp {
  rootPage = LoginPage;
  alert: any;
  @ViewChild(Nav) nav: Nav;
  constructor(private platform: Platform, private statusBar: StatusBar, 
                 private splashScreen: SplashScreen, private alertCtrl: AlertController) {
    platform.ready().then(() => {
            this.statusBar.styleDefault();
            this.splashScreen.hide();

        platform.registerBackButtonAction(() => {
            // get current active page
            if(this.nav.canGoBack()){
              this.nav.pop();
            }else{
              if(this.alert){ 
                this.alert.dismiss();
                this.alert =null;     
              }else{
                this.showAlert();
               }
            }
        });
    });
  }
  showAlert() {
      this.alert = this.alertCtrl.create({
        title: 'Exit?',
        message: 'Do you want to exit the app?',
        buttons: [
          {
            text: 'Cancel',
            role: 'cancel',
            handler: () => {
              this.alert =null;
            }
          },
          {
            text: 'Exit',
            handler: () => {
              this.platform.exitApp();
            }
          }
        ]
      });
      this.alert.present();
    }
}
