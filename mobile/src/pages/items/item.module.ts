import { NgModule } from '@angular/core';
import { ItemSearch, ItemSearchResult } from './';
import { IonicModule } from 'ionic-angular';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { CommonPSModule }   from '../../common/common-ps.module';


@NgModule({
  declarations: [
    ItemSearch,
    ItemSearchResult
  ],
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    CommonPSModule
  ],
  entryComponents: [
    ItemSearch,
    ItemSearchResult
  ],
  providers: []
})
export class ItemModule {}
