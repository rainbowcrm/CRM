import { NgModule } from '@angular/core';
import { IonicModule } from 'ionic-angular';
import { CommonModule } from '@angular/common';
import { IonicImageLoader } from 'ionic-image-loader';
import {ErrorPartial} from './error-partial/error-partial'
import {SortPopOverPage} from './sort-helper/sort-popover'
import { IonRating }   from './ion-rating/ion-rating';



@NgModule({
  declarations: [
    ErrorPartial,
    SortPopOverPage,
    IonRating
  ],
  imports: [
    IonicModule,
    CommonModule,
    IonicImageLoader
  ],
  exports:[
    ErrorPartial,
    SortPopOverPage,
    IonRating
  ],
  entryComponents: [
    ErrorPartial,
    SortPopOverPage,IonRating ],
  providers: []
})
export class CommonPSModule {}
