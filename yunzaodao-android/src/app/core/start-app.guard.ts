import { Injectable } from '@angular/core';
import { CanActivate, CanActivateChild, CanLoad, Route, UrlSegment, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { LocalStorageService, APP_KEY, USER_KEY } from '../shared/services/local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class StartAppGuard implements CanActivate {
  constructor(
    private localStorageService: LocalStorageService,
    private router: Router
  ) {}
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
      const appConfig: any = this.localStorageService.get(APP_KEY, {
        hasRun: false,
        version: '1.0.0'
      })
      const userInfo: any = this.localStorageService.get(USER_KEY, {
        isLogined: false
      })
      if (appConfig.hasRun === false) {
        appConfig.hasRun = true
        this.localStorageService.set(APP_KEY, appConfig)
        return true
      } else if (userInfo.isLogined === false) {
        this.router.navigateByUrl('/passport/login')
        return true
      } else {
        this.router.navigateByUrl('/home')
        return true
        // const current = new Date(+new Date() + 8 * 3600 * 1000)
        //   .toISOString()
        //   .replace(/T/g, ' ')
        //   .replace(/\.[\d]{3}Z/, '')
        //   .replace(/-/g, '/')
        // const loginTime = userInfo.loginTime
        // const sTime = new Date(current) // 开始时间
        // const eTime = new Date(loginTime) // 结束时间
        // const differ: any = (
        //   (sTime.getTime() - eTime.getTime()) /
        //   1000 /
        //   60 /
        //   60
        // ).toFixed(0)
        // // 6天即144小时
        // if (differ - 144 >= 0) {
        //   this.router.navigateByUrl('/passport/login')
        //   return true
        // } else {
        //   this.router.navigateByUrl('/home')
        //   return true
        // }
      }
  }
}
