import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { StartAppGuard } from './core/start-app.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'welcome',
    pathMatch: 'full'
  },
  {
    path: 'welcome',
    loadChildren: './routes/welcome/welcome.module#WelcomePageModule',
    canActivate: [StartAppGuard]
  },
  {
    path: 'user-agreement',
    loadChildren: () => import('./routes/user-agreement/user-agreement.module').then( m => m.UserAgreementPageModule)
  },
  {
    path: 'privacy-policy',
    loadChildren: () => import('./routes/privacy-policy/privacy-policy.module').then( m => m.PrivacyPolicyPageModule)
  },
  {
    path: 'about-us',
    loadChildren: () => import('./routes/about-us/about-us.module').then( m => m.AboutUsPageModule)
  },
  {
    path: 'passport/login',
    loadChildren: () => import('./routes/passport/login/login.module').then( m => m.LoginPageModule)
  },
  {
    path: 'passport/register',
    loadChildren: () => import('./routes/passport/register/register.module').then( m => m.RegisterPageModule)
  },
  {
    path: 'about-us',
    loadChildren: () => import('./routes/about-us/about-us.module').then( m => m.AboutUsPageModule)
  },
  {
    path: 'forgot-password',
    loadChildren: () => import('./routes/forgot-password/forgot-password.module').then( m => m.ForgotPasswordPageModule)
  },
  {
    path: 'home',
    loadChildren: () => import('./routes/home/home.module').then( m => m.HomePageModule)
  },
  {
    path: 'change-password',
    loadChildren: () => import('./routes/change-password/change-password.module').then( m => m.ChangePasswordPageModule)
  },
  {
    path: 'user-info',
    loadChildren: () => import('./routes/user-info/user-info.module').then( m => m.UserInfoPageModule)
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
