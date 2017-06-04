import { Injectable, EventEmitter, Output } from '@angular/core';
import { Push, PushObject, PushOptions } from '@ionic-native/push';
import { SecureStorageService } from '../';

@Injectable()
export class PushService {
  @Output() onNotification: EventEmitter<any> = new EventEmitter(true);
  @Output() onNotificationRegistered: EventEmitter<any> = new EventEmitter(true);
  constructor(private push: Push) { }
  checkPermission(){
    this.push.hasPermission().then((res: any) => {
      if (res.isEnabled) {
        console.log('We have permission to send push notifications');
      } else {
        console.log('We do not have permission to send push notifications');
      }

  });

  }
  init(){
    const options: PushOptions = {
       android: {
         senderID: '81761108581'
       },
       ios: {
         alert: 'true',
         badge: true,
         sound: 'false'
      },
    };

    const pushObject: PushObject = this.push.init(options);
    pushObject.on('notification').subscribe((notification: any) => {
           console.log('Received a notification ', notification.message);
           this.onNotification.emit(notification);
           alert(notification.message)
        });

    pushObject.on('registration').subscribe((registration: any) => {
           console.log('Device registered ', registration);
           this.onNotificationRegistered.emit(registration);
        });

    pushObject.on('error').subscribe(error => console.error('Error with Push plugin', error));
  }

}