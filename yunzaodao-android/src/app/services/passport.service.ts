import { Injectable } from '@angular/core';
import { CommonService } from '../shared/services/common.service';
import { LocalStorageService, USER_KEY } from '../shared/services/local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class PassportService {

  constructor(private httpService:CommonService, private localStorageService: LocalStorageService) { }

  async login(json:object){
    const api='/passport/login'
    return new Promise((resolve,reject)=>{
      this.httpService.ajaxPost(api,json).then((res:any)=>{
        resolve(res)
      }).catch((err)=>{
        reject(err)
      })
    })
  }
  async checkIsRegisted(phone:string){
    const api='/isRegisted?phone='+phone
    return new Promise((resolve,reject)=>{
      this.httpService.ajaxGet(api).then((res)=>{
        resolve(res)
      }).catch((err)=>{
        reject(err)
      })
    })
  }
  async register(json:object){
    const api='/passport/register'
    return new Promise((resolve,reject)=>{
      this.httpService.ajaxPost(api,json).then((res:any)=>{
          resolve(res)
      }).catch((err)=>{
        reject(err)
      })
    })
  }
  async changePassword(oldPassword: string, newPassword: string){
    let userInfo: any = this.localStorageService.get(USER_KEY, false)
    const api='/passport/change'
    const json = {
      phone: userInfo.phone,
      oldPassword: oldPassword,
      newPassword: newPassword
    }
    return new Promise((resolve,reject)=>{
      this.httpService.ajaxPost(api,json).then((res:any)=>{
          resolve(res)
      }).catch((err)=>{
        reject(err)
      })
    })
  }
}
