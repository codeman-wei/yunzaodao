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
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DetailPageRoutingModule {}
