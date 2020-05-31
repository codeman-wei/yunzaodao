import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastController } from '@ionic/angular';
import { PassportService } from 'src/app/services/passport.service';
import { LocalStorageService, USER_KEY } from 'src/app/shared/services/local-storage.service';
import { CommonService } from 'src/app/shared/services/common.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  login = {
    userName: '15695917757',
    password: 'a123456',
    submited: false
  }

  constructor(private router: Router,private toastCtrl: ToastController,private passportService: PassportService,private localStorageService: LocalStorageService, private httpService:CommonService) { }

  ngOnInit() {
  }

  onSingup() {
    this.router.navigateByUrl('/passport/register')
  }
  async onLogin() {
    // 验证输入是否合法
    if (this.login.userName === '') {
      const toast = await this.toastCtrl.create({
        message: '请输入您的手机号码',
        duration: 3000
      })
      toast.present()
    } else if (this.login.password === '') {
      const toast = await this.toastCtrl.create({
        message: '请输入您的密码',
        duration: 3000
      })
      toast.present()
    } else {
      const json = { 'username':this.login.userName, 'password':this.login.password }
      this.passportService.login(json).then(async (res:any)=>{
        let userInfo: any = this.localStorageService.get(USER_KEY, {})
        userInfo['phone'] = this.login.userName
        const api='/mobile/userInfo?phone=' + this.login.userName
        this.httpService.ajaxGet(api).then((res:any)=>{
          userInfo = res
          userInfo['isLogined'] = true
          this.localStorageService.set(USER_KEY, userInfo)
          window.location.replace('home')
          console.log("登录成功")
        }).catch((err)=>{
          console.log(err)
        })
      }).catch(async (err:any) =>{
        console.log(err)
        if (err.status == 400){
          const toast = await this.toastCtrl.create({
            message: '密码不正确',
            duration: 3000,
            buttons: [
              {
                side: 'end',
                text: '找回密码',
                handler: () => {
                  this.router.navigateByUrl('forgot-password')
                }
              }
            ]
          })
          toast.present()
        }else if (err.status == 404){
          const toast = await this.toastCtrl.create({
            message: '帐号不存在',
            duration: 3000,
            buttons: [
              {
                side: 'end',
                text: '去注册',
                handler: () => {
                  this.router.navigateByUrl('forgot-password')
                }
              }
            ]
          })
          toast.present()
        }
      })
    }
  }

  onForgotPassword() {
    //进入找回密码页面
    this.router.navigateByUrl('/forgot-password')
  }
}
