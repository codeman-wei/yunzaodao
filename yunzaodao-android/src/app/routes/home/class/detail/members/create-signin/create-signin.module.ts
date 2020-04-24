import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CreateSigninPageRoutingModule } from './create-signin-routing.module';

import { CreateSigninPage } from './create-signin.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    CreateSigninPageRoutingModule
  ],
  declarations: [CreateSigninPage]
})
export class CreateSigninPageModule {}
