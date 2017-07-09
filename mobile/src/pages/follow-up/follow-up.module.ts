import { NgModule } from '@angular/core';
import { IonicModule } from 'ionic-angular';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { CommonPSModule }   from '../../common/common-ps.module';
import { FollowUpAddPage }   from './';




@NgModule({
  declarations: [
    FollowUpAddPage
  ],
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    CommonPSModule
  ],
  entryComponents: [
    FollowUpAddPage
  ],
  providers: []
})
export class FollowUpModule {}
