import { Injectable } from '@angular/core';
import {  AlertController } from 'ionic-angular';
import { Subject }    from 'rxjs/Subject';
import { Observable }    from 'rxjs/Observable';

/*
  Generated class for the Reason code page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Injectable()
export class PromptService {
  private prompt= new Subject<any>();
  prompt$ = this.prompt.asObservable();
  constructor(private alertCtrl: AlertController) {
  }

  displayPrompt(title: string,msg: string, buttonTxt: string): any{
    let alert = this.alertCtrl.create({
     title: title,
     message: msg,
     inputs: [
        {
          name: buttonTxt,
          placeholder: buttonTxt
        },
     ],
     buttons: [{
       text: 'Cancel',
       handler: data => { 
          this.prompt.next("");
        }
      },{
       text: buttonTxt,
       handler: data => { 
          this.prompt.next(data[buttonTxt]);
        }
      }    
      ]
    });

  return alert;
 }

}
