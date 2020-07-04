import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SigninInfoPageRoutingModule } from './signin-info-routing.module';

import { SigninInfoPage } from './signin-info.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SigninInfoPageRoutingModule
  ],
  declarations: [SigninInfoPage]
})
export class SigninInfoPageModule {}
