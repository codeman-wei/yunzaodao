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
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CreateSigninPageRoutingModule {}
