import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { JoinSigninPageRoutingModule } from './join-signin-routing.module';

import { JoinSigninPage } from './join-signin.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    JoinSigninPageRoutingModule
  ],
  declarations: [JoinSigninPage]
})
export class JoinSigninPageModule {}
