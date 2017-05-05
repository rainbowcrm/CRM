import { NgModule } from '@angular/core';
import { CustomerAddPage, CustomerHomePage, CustomerListPage } from './';
import { IonicModule } from 'ionic-angular';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { CommonPSModule }   from '../../common/common-ps.module';
import { Contacts} from '@ionic-native/contacts';
import { Camera} from '@ionic-native/camera';
import { ImagePickerService } from '../../plugins/';


@NgModule({
  declarations: [
    CustomerAddPage,
    CustomerHomePage,
    CustomerListPage
  ],
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    CommonPSModule
  ],
  entryComponents: [
    CustomerHomePage,
    CustomerAddPage,
    CustomerListPage
  ],
  providers: [Contacts, Camera, ImagePickerService]
})
export class CustomerMgmtModule {}
