import { NgModule } from '@angular/core';
import { SalesLeadSearch, SalesLeadSearchResult, SalesLeadDetails } from './';
import { IonicModule } from 'ionic-angular';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { CommonPSModule }   from '../../common/common-ps.module';
import { IonicImageLoader } from 'ionic-image-loader';



@NgModule({
  declarations: [
    SalesLeadSearch,
    SalesLeadSearchResult,
    SalesLeadDetails
  ],
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    CommonPSModule,
    IonicImageLoader
  ],
  entryComponents: [
    SalesLeadSearch,
    SalesLeadSearchResult,
    SalesLeadDetails
  ],
  providers: []
})
export class SalesLeadModule {}
