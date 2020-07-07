import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CreateSigninPage } from './create-signin.page';

const routes: Routes = [
  {
    path: '',
    component: CreateSigninPage
  },
  {
    path: 'signin',
    loadChildren: () => import('./signin/signin.module').then( m => m.SigninPageModule)
  },
  {
    path: 'signin-info',
    loadChildren: () => import('./signin-info/signin-info.module').then( m => m.SigninInfoPageModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CreateSigninPageRoutingModule {}
