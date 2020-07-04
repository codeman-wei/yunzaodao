import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SigninInfoPage } from './signin-info.page';

const routes: Routes = [
  {
    path: '',
    component: SigninInfoPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SigninInfoPageRoutingModule {}
