import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { LoadingController, AlertController } from '@ionic/angular';
import { Observable  } from 'rxjs';
import { finalize, tap } from 'rxjs/operators';
import { LocalStorageService, USER_KEY } from './local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private httpClient: HttpClient, private loading: LoadingController,public alertController: AlertController, private localStorageService:LocalStorageService) { }

  domain = 'http://47.115.72.49:7300/mock/5e7ababe6914d01473f8142c/yunzaodao' // easy-mock

  headerOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': 'my-auth-token'
    }),
  }

  public request(method:string,url:string,data: Object):Observable<HttpResponse<any>> {
    this.loadShow()
    let token = this.localStorageService.get(USER_KEY, '').token
    if(!token) token = 'my-auth-token'
    this.headerOptions.headers = new HttpHeaders({
      'Content-Type':  'application/json',
      'Authorization': token
    })
    switch(method){
      case 'get':
        data = {params: data}
        break
      case 'post':
        data = {body: data}
        break
    }
    return this.httpClient.request(method,this.domain + url,{ ...data, ...this.headerOptions, ...{observe: 'response'}}).pipe(
      tap((response) => {
        this.handleSuccess(response)
      },
      (error) => {
        this.handleError(error)
      }),
      finalize(() => {
        this.loadHide()
      })
  )
  }

  private handleSuccess(response) {
    return response.data
  }

  private async handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
    } else {
      if (error.status === 400) {
        this.alert('请求失败')
      }
      else if (error.status === 401) {
        const alert = await this.alertController.create({
          header: '提示',
          message: '登录状态已过期，您可以继续留在该页面，或者重新登录',
          buttons: [
            {
              text: '取消',
              role: 'cancel'
            },
            {
              text: '确定',
              handler: () => {
                const userInfo = this.localStorageService.get(USER_KEY, '')
                userInfo.isLogined = false
                this.localStorageService.set(USER_KEY, userInfo)
                window.location.replace('passport/login')
              }
            }
          ]
        });
        await alert.present();
      }else if (error.status === 404) {
        this.alert('参数有误，请检查')
      } else if (error.status === 500) {
        this.alert('接口有错，联系后台管理员')
      } else {
        this.alert(error.message)
      }
    }
  }

  private async alert(message) {
    const alert = await this.alertController.create({
      header: '提示',
      message,
      buttons: ['确认']
    });
    await alert.present();
  }

  private async loadShow() {
    const loading = await this.loading.create({
      message: '加载中...',
      duration: 2000,
      translucent: true,
      cssClass: 'custom-class custom-loading'
    });
    await loading.present();
  }

  private async loadHide() {
    await this.loading.dismiss()
  }
}
