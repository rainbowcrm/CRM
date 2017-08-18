import { NgModule } from '@angular/core';
import { IonicModule } from 'ionic-angular';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { CommonPSModule }   from '../../common/common-ps.module';
import { ProductsList, ProductsDetailsList }   from './';





@NgModule({
  declarations: [
   ProductsList,
   ProductsDetailsList
  ],
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    CommonPSModule
  ],
  entryComponents: [
   ProductsList,
   ProductsDetailsList
  ],
  providers: []
})
export class ProductsFAQModule {}
