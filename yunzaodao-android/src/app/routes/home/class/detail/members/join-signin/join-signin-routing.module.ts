import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { JoinSigninPage } from './join-signin.page';

const routes: Routes = [
  {
    path: '',
    component: JoinSigninPage
  },
  {
    path: 'signin',
    loadChildren: () => import('./signin/signin.module').then( m => m.SigninPageModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class JoinSigninPageRoutingModule {}
