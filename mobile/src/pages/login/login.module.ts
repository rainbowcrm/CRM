import { NgModule } from '@angular/core';
import { LoginPage } from './login';
import { IonicModule } from 'ionic-angular';
import { CommonModule } from '@angular/common';
import { FormsModule }   from '@angular/forms';
import { CommonPSModule }   from '../../common/common-ps.module';


@NgModule({
  declarations: [
    LoginPage
  ],
  imports: [
    IonicModule,
    CommonModule,
    FormsModule,
    CommonPSModule
  ],
  entryComponents: [
    LoginPage
  ],
  providers: []
})
export class LoginModule {}
