import { NgModule } from '@angular/core';
import { IonicModule } from 'ionic-angular';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { CommonPSModule }   from '../../common/common-ps.module';
import { ExpenseHomePage, ExpensesAddPage, ExpensesLineAddPage }   from './';
import { IonicImageLoader } from 'ionic-image-loader';
import { ImagePickerService } from '../../plugins/';
import { Camera} from '@ionic-native/camera';




@NgModule({
  declarations: [
    ExpenseHomePage,
    ExpensesAddPage,
    ExpensesLineAddPage
  ],
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    CommonPSModule
  ],
  entryComponents: [
    ExpenseHomePage,
    ExpensesAddPage,
    ExpensesLineAddPage
  ],
  providers: [Camera, ImagePickerService]
})
export class ExpenseModule {}
