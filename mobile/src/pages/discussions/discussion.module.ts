import { NgModule } from '@angular/core';
import { IonicModule } from 'ionic-angular';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { CommonPSModule }   from '../../common/common-ps.module';
import { DiscussionsList, DiscussionTopicList, DiscussionAddPage, DiscussionBrandModalPage }   from './';





@NgModule({
  declarations: [
   DiscussionsList,
   DiscussionTopicList,
   DiscussionAddPage,
   DiscussionBrandModalPage
  ],
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    CommonPSModule
  ],
  entryComponents: [
   DiscussionsList,
   DiscussionTopicList,
   DiscussionAddPage,
   DiscussionBrandModalPage
  ],
  providers: []
})
export class DiscussionModule {}
