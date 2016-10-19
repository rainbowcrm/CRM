import { NgModule } from '@angular/core';
import { IonicModule } from 'ionic-angular';
import { CommonModule } from '@angular/common';

import {ErrorPartial} from './error-partial/error-partial'



@NgModule({
  declarations: [
    ErrorPartial
  ],
  imports: [
    IonicModule,
    CommonModule
  ],
  exports:[
    ErrorPartial
  ],
  entryComponents: [
    ErrorPartial
  ],
  providers: []
})
export class CommonPSModule {}
