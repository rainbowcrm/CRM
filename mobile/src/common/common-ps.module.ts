import { NgModule } from '@angular/core';
import { IonicModule } from 'ionic-angular';
import { CommonModule } from '@angular/common';
import { IonicImageLoader } from 'ionic-image-loader';
import {ErrorPartial} from './error-partial/error-partial'
import {SortPopOverPage} from './sort-helper/sort-popover'



@NgModule({
  declarations: [
    ErrorPartial,
    SortPopOverPage
  ],
  imports: [
    IonicModule,
    CommonModule,
    IonicImageLoader
  ],
  exports:[
    ErrorPartial,
    SortPopOverPage
  ],
  entryComponents: [
    ErrorPartial,
    SortPopOverPage  ],
  providers: []
})
export class CommonPSModule {}
