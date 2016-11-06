import { NgModule } from '@angular/core';
import { ItemSearch } from './';
import { IonicModule } from 'ionic-angular';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { CommonPSModule }   from '../../common/common-ps.module';


@NgModule({
  declarations: [
    ItemSearch
  ],
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    CommonPSModule
  ],
  entryComponents: [
    ItemSearch
  ],
  providers: []
})
export class ItemModule {}
