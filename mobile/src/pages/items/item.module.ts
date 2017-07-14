import { NgModule } from '@angular/core';
import { ItemSearch, ItemSearchResult, ItemDetails } from './';
import { IonicModule } from 'ionic-angular';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { CommonPSModule }   from '../../common/common-ps.module';
import { IonicImageLoader } from 'ionic-image-loader';
import { BarcodeScanner } from '@ionic-native/barcode-scanner';
import { FileOpener } from '@ionic-native/file-opener';
import { FileTransfer } from '@ionic-native/file-transfer';


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
  providers: [BarcodeScanner, FileOpener, FileTransfer]
})
export class ItemModule {}
