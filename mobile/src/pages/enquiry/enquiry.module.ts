import { NgModule } from '@angular/core';
import { IonicModule } from 'ionic-angular';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { CommonPSModule }   from '../../common/common-ps.module';
import { EnquiryAddPage }   from './';
import { IonicImageLoader } from 'ionic-image-loader';
import { ImagePickerService } from '../../plugins/';
import { Camera} from '@ionic-native/camera';




@NgModule({
  declarations: [
    EnquiryAddPage
  ],
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    CommonPSModule
  ],
  entryComponents: [
    EnquiryAddPage
  ],
  providers: [Camera, ImagePickerService]
})
export class EnquiryModule {}
