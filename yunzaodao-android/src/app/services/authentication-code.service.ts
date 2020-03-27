import { Injectable } from '@angular/core';
import { CommonService } from '../shared/services/common.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationCodeService {

  constructor(private httpService:CommonService) { }

  async getCode(phone:string){
    const api='/code?phone='+phone
    return new Promise((resolve,reject)=>{
      this.httpService.ajaxGet(api).then((res:any)=>{
        if(res.code == 200){
          resolve(res)
        }else if(res.code == 400){
          reject(res)
        }
      }).catch((err)=>{
        reject(err)
      })
    })
  }
  async checkCode(phone:string, code:string){
    const api='/code'
    const json={
      "phone":phone,
      "code":code
    }
    return new Promise((resolve,reject)=>{
      this.httpService.ajaxPost(api,json).then((res:any)=>{
        if(res.code == 200){
          resolve(res)
        }else if(res.code == 400){
          reject(res)
        }
      }).catch((err)=>{
        reject(err)
      })
    })
  }
}
