import { Component } from '@angular/core';
import { NavController, ToastController } from 'ionic-angular';

import { HomePage } from '../../home/home';
import { Appointment, AppointmentSearchRequest, AppointemntDetailsPage } from '../'
import { HTTPService , ReasonCodeProvider} from '../../../providers/';

/*

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'ps-appointments-home',
  templateUrl: 'appointments-home.html'
})
export class AppointemntHomePage {
  private model: Appointment;
  private errorMessage:string;
  private request: AppointmentSearchRequest;
  private appointments: Array<Appointment>;
  private pageNumber: number;
  private fetchedResults: number;
  private numberOfResults: number;
  private apptStatus: Array<any>;
  

  constructor(public navCtrl: NavController, private http: HTTPService, private toastCtrl: ToastController,
    private rcp: ReasonCodeProvider) {
     this.model = new Appointment();
     this.rcp.finiteValueSource$.subscribe(res => {this.updateReasonCodes(res)});
     this.rcp.getFiniteValues(true);

  }

  ionViewDidLoad() {
    this.getAppointments();
  }

  goHome():void{
      this.navCtrl.setRoot(HomePage);
  }

  updateReasonCodes(reasonCodes){
     this.apptStatus =  reasonCodes.APPTSTATUS;
  }


  getAppointments():void{
      this.errorMessage = null;
      this.request = new AppointmentSearchRequest();
      this.request.currentmode = 'READ';
      this.request.fixedAction = "FixedAction.NAV_FIRSTPAGE";
      this.request.pageID = "appointments";
      this.http.processServerRequest("post",this.request, true).subscribe(
                     res => this.appointmentSearchSuccess(res),
                     error =>  this.appointmentSearchError(error));  
  }

  appointmentSearchSuccess(response):void{
    if(response.result == "failure"){
       this.errorMessage = "Failed to fetch appointments"; 
       return ;
    }
    if(response.dataObject.length == 0){
       this.showToast("No Appointments found");
       this.goHome();
    }
    this.pageNumber = 0;
    this.fetchedResults = response.fetchedRecords;
    this.numberOfResults = response.availableRecords;
    this.appointments = response.dataObject;
  }

 

  appointmentSearchError(error){
    this.errorMessage = "Failed to fetch appointments";
  }

  appointmentLazySearchSuccess(response, infiniteScroll):void{
    if(response.result == "failure"){
       infiniteScroll.complete();
       return ;
    }
    if(response.dataObject.length == 0){
      infiniteScroll.complete();
       return ;
    }
    this.appointments = this.appointments.concat(response.dataObject);
    this.fetchedResults += response.fetchedRecords;
    infiniteScroll.complete();
  }
 

  appointmentLazySearchError(error,infiniteScroll){
    infiniteScroll.complete();
  }

  showToast(msg: string):any{
     let toast = this.toastCtrl.create({
      message: msg,
      duration: 2000,
      position: 'top'
     });
    toast.present();
  }

  doSearchMoreAppointments(infiniteScroll) {
      if(this.fetchedResults >= this.numberOfResults ){
        infiniteScroll.complete();
        return;
      }
      this.request = new AppointmentSearchRequest();
      this.request.currentmode = 'READ';
      this.request.fixedAction = "FixedAction.NAV_NEXTPAGE";
      this.request.hdnPage = ++this.pageNumber;
      this.request.pageID = "appointments";
      this.http.processServerRequest("post",this.request, true, true).subscribe(
                     res => this.appointmentLazySearchSuccess(res, infiniteScroll),
                     error =>  this.appointmentLazySearchError(error, infiniteScroll)); 
  }

  getApptStatus(code: string):any{
     for (var index = 0; index < this.apptStatus.length; ++index) {
        if(this.apptStatus[index]["Code"] === code){
          return this.apptStatus[index]["Description"];
        }
     }
  }

  navigateToDetails(appt){
    this.navCtrl.push(AppointemntDetailsPage, {"appointment": appt});
  }

  newAppointment(){
    
  }

}
