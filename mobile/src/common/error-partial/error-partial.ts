import { Component,Input } from '@angular/core';
import { NavController } from 'ionic-angular';

/*
  Generated class for the ErrorPartial page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-error',
  templateUrl: 'error-partial.html'
})
export class ErrorPartial {
  @Input() message:string;
  @Input() type:string;

  setClasses() {
    let classes =  {
      warn: this.type=='warn',      
      error: this.type=='error', 
      info: this.type=='info',    
    };
    return classes;
  }

  ionViewDidLoad() {
    console.log('Hello Login Page');
  }
}
