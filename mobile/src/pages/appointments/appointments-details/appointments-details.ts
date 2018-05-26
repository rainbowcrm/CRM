import { Component } from '@angular/core';
import { NavController, NavParams, ToastController } from 'ionic-angular';

import { Appointment } from '../'
import { HTTPService, ReasonCodeProvider } from '../../../providers/';
import { Calendar } from '@ionic-native/calendar';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-appointments-details',
  templateUrl: 'appointments-details.html'
})
export class AppointemntDetailsPage {
  private appointment: Appointment;
  private apptStatus: Array<any>;
  private visitTypeStatus: Array<any>;
  private status: string;
  private visitType: string;
  

  constructor(public navCtrl: NavController, private http: HTTPService, private navParams: NavParams, private toastCtrl: ToastController,
    private rcp: ReasonCodeProvider, private calendar: Calendar) {
     this.appointment = this.navParams.get("appointment");
     this.rcp.finiteValueSource$.subscribe(res => {this.updateReasonCodes(res)});
     this.rcp.getFiniteValues(true);
  }

  ionViewDidLoad() {
  }

  updateReasonCodes(reasonCodes){
     this.apptStatus =  reasonCodes.APPTSTATUS;
     this.visitTypeStatus = reasonCodes.EXPTYTYPE;
     this.status = this.getStatusfromCode(this.appointment.Status.Code, this.apptStatus);
     this.visitType = this.getStatusfromCode(this.appointment.PartyType.Code, this.visitTypeStatus);
  }


 
  showToast(msg: string):any{
     let toast = this.toastCtrl.create({
      message: msg,
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

  getStatusfromCode(code: string, list):any{
     for (var index = 0; index < list.length; ++index) {
        if(list[index]["Code"] === code){
          return list[index]["Description"];
        }
     }
  }

  addToCalendar():void{
     var appDate = new Date(this.appointment.ApptDate+" "+this.appointment.Hh+":"+this.appointment.Mm);
     var appEndDate = new Date(this.appointment.ApptDate+" "+this.appointment.Hh+":"+this.appointment.Mm);
     var duration = parseFloat(this.appointment.Duration);
     appEndDate.setMinutes(appEndDate.getMinutes() + duration);
     appEndDate = new Date(appEndDate);
     this.calendar.createEventInteractivelyWithOptions(this.appointment.DocNo, null, 'Appointment with '+this.appointment.PartyNameWithType+' at '+this.appointment.Location.Name, 
                                                        appDate, appEndDate);
  }

}
