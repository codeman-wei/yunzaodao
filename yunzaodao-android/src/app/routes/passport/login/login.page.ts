import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastController } from '@ionic/angular';
import { PassportService } from 'src/app/services/passport.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  login = {
    userName: '',
    password: '',
    submited: false
  }

  constructor(private router: Router,private toastCtrl: ToastController,private passportService: PassportService) { }

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
          // window.location.replace('class')
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
