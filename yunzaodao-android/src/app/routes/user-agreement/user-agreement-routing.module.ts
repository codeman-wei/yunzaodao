import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UserAgreementPage } from './user-agreement.page';

const routes: Routes = [
  {
    path: '',
    component: UserAgreementPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserAgreementPageRoutingModule {}
