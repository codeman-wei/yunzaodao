import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { WelcomePage } from './welcome.page';
import { Geolocation } from "@ionic-native/geolocation/ngx"

const routes: Routes = [
  {
    path: '',
    component: WelcomePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [Geolocation],
})
export class WelcomePageRoutingModule {}
