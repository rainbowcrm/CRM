import { NgModule } from '@angular/core';
import { IonicApp, IonicModule } from 'ionic-angular';

import { PSApp } from './app.component';
import { LoginModule } from '../pages/login/login.module';
import { CustomerMgmtModule } from '../pages/customer-mgmt/customer-mgmt.module';
import { HomePage } from '../pages/home/home';

import { HTTPService,Loader } from '../providers/';

@NgModule({
  declarations: [
    PSApp,
    HomePage
  ],
  imports: [
    IonicModule.forRoot(PSApp),
    LoginModule,
    CustomerMgmtModule
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    PSApp,
    HomePage
  ],
  providers: [HTTPService, Loader]
})
export class AppModule {}
