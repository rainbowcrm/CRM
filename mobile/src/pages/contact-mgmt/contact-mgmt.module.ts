import { NgModule } from '@angular/core';
import { ContactAddPage, ContactHomePage, ContactListPage } from './';
import { IonicModule } from 'ionic-angular';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { CommonPSModule }   from '../../common/common-ps.module';
import { Contacts} from '@ionic-native/contacts';



@NgModule({
  declarations: [
   ContactAddPage,
   ContactHomePage,
   ContactListPage
  ],
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    CommonPSModule
  ],
  entryComponents: [
   ContactAddPage,
   ContactHomePage,
   ContactListPage
  ],
  providers: [Contacts]
})
export class ContactMgmtModule {}
