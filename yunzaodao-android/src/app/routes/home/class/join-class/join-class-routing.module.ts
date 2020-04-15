import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { JoinClassPage } from './join-class.page';

const routes: Routes = [
  {
    path: '',
    component: JoinClassPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class JoinClassPageRoutingModule {}
