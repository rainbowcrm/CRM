import { NgModule } from '@angular/core';
import { IonicApp, IonicModule } from 'ionic-angular';

import { PSApp } from './app.component';
import { LoginModule } from '../pages/login/login.module';
import { CustomerMgmtModule } from '../pages/customer-mgmt/customer-mgmt.module';
import { ItemModule } from '../pages/items/item.module';
import { HomePage } from '../pages/home/home';
import { ContactMgmtModule } from '../pages/contact-mgmt/contact-mgmt.module';

import { HTTPService,Loader } from '../providers/';
import { ContactService, PushService, SecureStorageService } from '../plugins/';
import { Push} from '@ionic-native/push';
import { SecureStorage } from '@ionic-native/secure-storage';
import { SplashScreen } from '@ionic-native/splash-screen';

@NgModule({
  declarations: [
    PSApp,
    HomePage
  ],
  imports: [
    IonicModule.forRoot(PSApp),
    LoginModule,
    CustomerMgmtModule,
    ContactMgmtModule,
    ItemModule
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    PSApp,
    HomePage
  ],
  providers: [HTTPService, Loader, ContactService, SecureStorage, SecureStorageService,
                Push,PushService, SplashScreen]
})
export class AppModule {}
