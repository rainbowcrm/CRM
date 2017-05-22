import { NgModule } from '@angular/core';
import { ItemSearch, ItemSearchResult, ItemDetails } from './';
import { IonicModule } from 'ionic-angular';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { CommonPSModule }   from '../../common/common-ps.module';
import { IonicImageLoader } from 'ionic-image-loader';
import { BarcodeScanner } from '@ionic-native/barcode-scanner';


@NgModule({
  declarations: [
    ItemSearch,
    ItemSearchResult,
    ItemDetails
  ],
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    CommonPSModule,
    IonicImageLoader
  ],
  entryComponents: [
    ItemSearch,
    ItemSearchResult,
    ItemDetails
  ],
  providers: [BarcodeScanner]
})
export class ItemModule {}
