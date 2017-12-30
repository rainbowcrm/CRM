import { NgModule } from '@angular/core';
import { IonicApp, IonicModule } from 'ionic-angular';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from "@angular/common/http";

import { PSApp } from './app.component';
import { LoginModule } from '../pages/login/login.module';
import { AlertsModule } from '../pages/alerts/alerts.module';
import { CustomerMgmtModule } from '../pages/customer-mgmt/customer-mgmt.module';
import { ItemModule } from '../pages/items/item.module';
import { HomePage } from '../pages/home/home';
import { WishlistModule } from '../pages/wishlist/wishlist.module';
import { ContactMgmtModule } from '../pages/contact-mgmt/contact-mgmt.module';
import { SalesLeadModule } from '../pages/sales-lead/sales-lead.module';
import { DiscussionModule } from '../pages/discussions/discussion.module';
import { ExpenseModule } from '../pages/expenses/expenses.module';
import { FollowUpModule } from '../pages/follow-up/follow-up.module';
import { EnquiryModule } from '../pages/enquiry/enquiry.module';
import { AppointmentsModule } from '../pages/appointments/appointments.module';
import { ProductsFAQModule } from '../pages/products-faq/products-faq.module';

import { Loader,HTTPService, SharedService, ReasonCodeProvider, FilterProvider, PromptService, CommonHelper } from '../providers/';
import { ContactService, PushService, SecureStorageService } from '../plugins/';
import { Push} from '@ionic-native/push';
import { SecureStorage } from '@ionic-native/secure-storage';
import { SplashScreen } from '@ionic-native/splash-screen';
import { IonicImageLoader } from 'ionic-image-loader';
import { IonicStorageModule } from '@ionic/storage';

@NgModule({
  declarations: [
    PSApp,
    HomePage
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    IonicModule.forRoot(PSApp),
    LoginModule,
    CustomerMgmtModule,
    ContactMgmtModule,
    ItemModule,
    WishlistModule,
    SalesLeadModule,
    DiscussionModule,
    AlertsModule,
    ExpenseModule,
    FollowUpModule,
    EnquiryModule,
    ProductsFAQModule,
    AppointmentsModule,
    IonicImageLoader.forRoot(),
    IonicStorageModule.forRoot()
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    PSApp,
    HomePage
  ],
  providers: [Loader,HTTPService, ReasonCodeProvider, FilterProvider, ContactService, SecureStorage, SecureStorageService,
                Push,PushService, SplashScreen, SharedService, PromptService, ExpenseModule, CommonHelper]
})
export class AppModule {}
