import { Injectable } from '@angular/core';
import { CommonService } from '../shared/services/common.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationCodeService {

  // constructor(private httpService:CommonService) { }

  // async getCode(phone:string){
  //   const api='/code?phone='+phone
  //   return new Promise((resolve,reject)=>{
  //     this.httpService.ajaxGet(api).then((res:any)=>{
  //       if(res.code == 200){
  //         resolve(res)
  //       }else if(res.code == 400){
  //         reject(res)
  //       }
  //     }).catch((err)=>{
  //       reject(err)
  //     })
  //   })
  // }
  // async checkCode(phone:string, code:string){
  //   const api='/code'
  //   const json={
  //     "phone":phone,
  //     "code":code
  //   }
  //   return new Promise((resolve,reject)=>{
  //     this.httpService.ajaxPost(api,json).then((res:any)=>{
  //       if(res.code == 200){
  //         resolve(res)
  //       }else if(res.code == 400){
  //         reject(res)
  //       }
  //     }).catch((err)=>{
  //       reject(err)
  //     })
  //   })
  // }

  // 用于保存验证码
  private code: string
  // 存放验证码的过期时间
  private deadline: number

  constructor() {
    this.code = ''
  }
  // 生成指定长度的随机数字
  createCode(count: number): string {
    var code: string
    this.code = ''
    // 10分钟内有效
    this.deadline = Date.now() + 60 * 10 * 1000
    for (let i = 0; i < count; i++) {
      let num = Math.floor(Math.random() * 10)
      this.code += num.toString()
    }
    code = this.code
    return code
  }
  // 验证用户输入的短信验证码是否一致，是否过期
  validate(value: string): boolean {
    let now = Date.now()
    value = value.toString()
    return value == this.code && now < this.deadline
  }
}
