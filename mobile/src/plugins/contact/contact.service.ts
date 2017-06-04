import { Injectable } from '@angular/core';
import { Contacts, Contact, ContactField, ContactName } from '@ionic-native/contacts';
import { ToastController } from 'ionic-angular';
import { Customer } from '../';

@Injectable()
export class ContactService {
  public contact:Contact;
  constructor(private toastCtrl: ToastController, private contacts:Contacts) {
    this.contact= this.contacts.create();
  }
  saveContact(customer: any){
    this.contact.name = new ContactName(null, customer.FirstName, customer.LastName);
    if(customer.Phone && customer.Phone.length > 0){
      this.contact.phoneNumbers = [new ContactField('mobile', customer.Phone)];
    }
    if(customer.Email && customer.Email.length > 0){
      this.contact.emails = [new ContactField('email', customer.Email)];
    }
    
    this.contact.save().then(
      () => {
          let toast = this.toastCtrl.create({
            message: 'Contact saved to device',
            duration: 2000,
            position: 'top'
          });
          toast.present();
      },
      (error: any) => {
          let toast = this.toastCtrl.create({
            message: 'Failed to save customer details',
            duration: 2000,
            position: 'top'
          });
          toast.present();
      }
    );
  }

}