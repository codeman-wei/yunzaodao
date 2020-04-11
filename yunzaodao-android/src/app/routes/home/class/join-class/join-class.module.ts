import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { JoinClassPageRoutingModule } from './join-class-routing.module';

import { JoinClassPage } from './join-class.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    JoinClassPageRoutingModule
  ],
  declarations: [JoinClassPage]
})
export class JoinClassPageModule {}
