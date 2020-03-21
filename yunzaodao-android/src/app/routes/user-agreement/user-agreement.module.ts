import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { UserAgreementPageRoutingModule } from './user-agreement-routing.module';

import { UserAgreementPage } from './user-agreement.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    UserAgreementPageRoutingModule
  ],
  declarations: [UserAgreementPage]
})
export class UserAgreementPageModule {}
