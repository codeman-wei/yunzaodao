import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastController } from '@ionic/angular';
import { PassportService } from 'src/app/services/passport.service';
import { LocalStorageService, USER_KEY } from 'src/app/shared/services/local-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  login = {
    userName: '15695917757',
    password: 'admin',
    submited: false
  }

  constructor(private router: Router,private toastCtrl: ToastController,private passportService: PassportService,private localStorageService: LocalStorageService) { }

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
      const toast = await this.toastCtrl.create({
        message: '帐号或密码不正确',
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
      this.passportService.login(json).then((res:any)=>{
        if(res.code == 200){
          let userInfo: any = this.localStorageService.get(USER_KEY, {})
          userInfo['phone'] = this.login.userName
          userInfo['name'] = res.data.name
          userInfo['status'] = res.data.status
          userInfo['isLogined'] = true
          this.localStorageService.set(USER_KEY, userInfo)
          window.location.replace('home')
          console.log("登录成功")
        }else if (res.code == 400){
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
