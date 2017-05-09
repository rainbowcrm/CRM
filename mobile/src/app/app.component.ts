import { Component } from '@angular/core';
import { Platform } from 'ionic-angular';
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

  constructor(platform: Platform, private statusBar: StatusBar, private splashScreen: SplashScreen) {
    platform.ready().then(() => {
            this.statusBar.styleDefault();
    });
  }
}
