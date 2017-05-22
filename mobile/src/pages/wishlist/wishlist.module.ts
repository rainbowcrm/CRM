import { NgModule } from '@angular/core';
import { WishListPage } from './';
import { IonicModule } from 'ionic-angular';
import { CommonModule } from '@angular/common';
import { CommonPSModule }   from '../../common/common-ps.module';
import { IonicImageLoader } from 'ionic-image-loader';


@NgModule({
  declarations: [
    WishListPage
  ],
  imports: [
    IonicModule,
    CommonModule,
    CommonPSModule,
    IonicImageLoader
  ],
  entryComponents: [
    WishListPage
  ],
  providers: []
})
export class WishlistModule {}
