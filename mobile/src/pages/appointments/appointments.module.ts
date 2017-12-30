import { NgModule } from '@angular/core';
import { AppointemntHomePage, AppointemntDetailsPage} from './';
import { IonicModule } from 'ionic-angular';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { CommonPSModule }   from '../../common/common-ps.module';
import { Calendar } from '@ionic-native/calendar';



@NgModule({
  declarations: [
   AppointemntHomePage,
   AppointemntDetailsPage
  ],
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    CommonPSModule
  ],
  entryComponents: [
   AppointemntHomePage,
   AppointemntDetailsPage
  ],
  providers: [Calendar]
})
export class AppointmentsModule {}
