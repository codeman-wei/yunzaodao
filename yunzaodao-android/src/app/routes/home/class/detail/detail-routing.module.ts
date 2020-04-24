import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DetailPage } from './detail.page';

const routes: Routes = [
  {
    path: '',
    component: DetailPage,
    children: [
      {
        path: '',
        redirectTo: 'members',
        pathMatch: 'full'
      },
      {
        path: 'members',
        children: [
          {
            path: '',
            loadChildren: () => import('./members/members.module').then( m => m.MembersPageModule)
          }
        ]
      },
      {
        path: 'details',
        children: [
          {
            path: '',
            loadChildren: () => import('./details/details.module').then( m => m.DetailsPageModule)
          }
        ]
      }
    ]
  },
  {
    path: 'details/info',
    loadChildren: () => import('./details/info/info.module').then( m => m.InfoPageModule)
  },
  {
    path: 'members/create-signin',
    loadChildren: () => import('./members/create-signin/create-signin.module').then( m => m.CreateSigninPageModule)
  },
  {
    path: 'members/join-signin',
    loadChildren: () => import('./members/join-signin/join-signin.module').then( m => m.JoinSigninPageModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DetailPageRoutingModule {}
