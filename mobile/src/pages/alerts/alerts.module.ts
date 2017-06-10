import { NgModule } from '@angular/core';
import { AlertListPage, AlertsHomePage } from './';
import { IonicModule } from 'ionic-angular';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { CommonPSModule }   from '../../common/common-ps.module';



@NgModule({
  declarations: [
   AlertListPage,
   AlertsHomePage
  ],
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    CommonPSModule
  ],
  entryComponents: [
   AlertListPage,
   AlertsHomePage
  ],
  providers: []
})
export class AlertsModule {}
