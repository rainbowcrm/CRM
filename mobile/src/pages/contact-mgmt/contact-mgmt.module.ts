import { NgModule } from '@angular/core';
import { ContactAddPage, ContactHomePage } from './';
import { IonicModule } from 'ionic-angular';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { CommonPSModule }   from '../../common/common-ps.module';


@NgModule({
  declarations: [
   ContactAddPage,
   ContactHomePage
  ],
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    CommonPSModule
  ],
  entryComponents: [
   ContactAddPage,
   ContactHomePage
  ],
  providers: []
})
export class ContactMgmtModule {}
