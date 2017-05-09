import { Injectable } from '@angular/core';
import {LoadingController } from 'ionic-angular';

/*
  Generated class for the Loader provider.

  See https://angular.io/docs/ts/latest/guide/dependency-injection.html
  for more info on providers and Angular 2 DI.
*/
@Injectable()
export class Loader {
  private loader;

  constructor(private loadingCtrl: LoadingController) { }

  presentLoader():void{
    if(!this.loader){
      this.loader = this.loadingCtrl.create({
        spinner: 'crescent',
        content: 'Please Wait...'
       // dismissOnPageChange: true //buggy.. Hang the person!!!!
     });
    }
     this.loader.present();
  }

  dismissLoader():void{
    this.loader.dismiss();
  }

}
