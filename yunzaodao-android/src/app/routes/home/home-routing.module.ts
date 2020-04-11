import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomePage } from './home.page';

const routes: Routes = [
  {
    path: '',
    component: HomePage,
    children: [
      {
        path: '',
        redirectTo: 'class',
        pathMatch: 'full'
      },
      {
        path: 'class',
        children: [
          {
            path: '',
            loadChildren: () => import('./class/class.module').then( m => m.ClassPageModule)
          }
        ]
      },
      {
        path: 'mine',
        children: [
          {
            path: '',
            loadChildren: () => import('./mine/mine.module').then( m => m.MinePageModule)
          }
        ]
      }
    ],
  },
  {
    path: 'class/join-class',
    children: [
      {
        path: '',
        loadChildren: () => import('./class/join-class/join-class.module').then( m => m.JoinClassPageModule)
      }
    ]
  },
  {
    path: 'class/create-class',
    children: [
      {
        path: '',
        loadChildren: () => import('./class/create-class/create-class.module').then( m => m.CreateClassPageModule)
      }
    ]
  },
  {
    path: 'mine/user-info',
    children: [
      {
        path: '',
        loadChildren: () => import('./mine/user-info/user-info.module').then( m => m.UserInfoPageModule)
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HomePageRoutingModule {}
