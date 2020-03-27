import { Component, OnInit, ViewChild } from '@angular/core';
import { IonSlides, ToastController, AlertController } from '@ionic/angular';
import { AuthenticationCodeService } from 'src/app/services/authentication-code.service'
import { NgForm } from '@angular/forms';
import { PassportService } from 'src/app/services/passport.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
})
export class RegisterPage implements OnInit {

  slideIndex = 0

  register = {
    username: '15695917757',
    password: 'a123456',
    code: '1314',
    type: '',
    name: ''
  }

  verifyCode = {
    verifyCodeTips: '发送验证码',
    countdown: 60,
    disable: true,
    sended: false,
    submited: false,
    verifyCodeResult: false
  }


  constructor(private router: Router, private toastCtrl: ToastController,private alertCtrl: AlertController,private passportService: PassportService, private authenticationCode: AuthenticationCodeService) { }

  @ViewChild('registerSlides', { static: true }) registerSlides: IonSlides
  //字符串'registerSlides'和模板中的#registerSlides引用变量的名称一致
  ngOnInit() {
    this.registerSlides.lockSwipeToNext(true)
    this.registerSlides.lockSwipeToPrev(true)
  }
  onNext() {
    this.registerSlides.lockSwipeToNext(false)
    this.slideIndex = (this.slideIndex + 1) % 6
    this.registerSlides.slideNext()
    this.registerSlides.lockSwipeToNext(true)
  }
  onPrevious() {
    this.registerSlides.lockSwipeToPrev(false)
    this.slideIndex = (this.slideIndex - 1) % 6
    this.registerSlides.slidePrev()
    this.registerSlides.lockSwipeToPrev(true)
  }
  
  async onregisterPhone(form: NgForm) {
    if (form.valid) {
      this.passportService.checkIsRegisted(this.register.username).then(async (Response:any) =>{
        if(!Response.data){
          this.onNext()
        }
        else {
          const toast = await this.toastCtrl.create({
            message: '该手机号码已注册',
            duration: 3000,
            buttons: [
              {
                side: 'end',
                text: '去登陆',
                handler: () => {
                  this.router.navigateByUrl('login')
                }
              }
            ]
          })
          toast.present()
        }
      })
    } else {
      const toast = await this.toastCtrl.create({
        message: '请输入正确的手机号码',
        duration: 3000
      })
      toast.present()
    }
  }
  settime() {
    if (this.verifyCode.countdown == 1) {
      this.verifyCode.countdown = 60
      this.verifyCode.verifyCodeTips = '重新发送'
      this.verifyCode.disable = true
      return
    } else {
      this.verifyCode.countdown--
    }

    this.verifyCode.verifyCodeTips =
      '重新发送(' + this.verifyCode.countdown + ')'
    setTimeout(() => {
      this.verifyCode.verifyCodeTips =
        '重新发送(' + this.verifyCode.countdown + ')'
      this.settime()
    }, 1000)
  }
  async getCode() {
    this.authenticationCode.getCode(this.register.username).then(async (res)=>{
      const toast = await this.toastCtrl.create({
        message: '验证码已发送至' + this.register.username,
        duration: 3000
      })
      toast.present()
    }).catch(async (err)=>{
      const toast = await this.toastCtrl.create({
        message: '验证码请求失败，请稍后再试',
        duration: 3000
      })
      toast.present()
    })
    //发送验证码成功后开始倒计时
    this.verifyCode.disable = false
    this.verifyCode.sended = true
    this.settime()
  }
  async onRegisterCode(form: NgForm) {
    if(form.valid){
      this.verifyCode.submited = true
      // 验证code是否一致
      this.authenticationCode.checkCode(this.register.username, this.register.code).then((res)=>{
        this.verifyCode.verifyCodeResult = true
        this.onNext()
      }).catch(async (err)=>{
        this.verifyCode.verifyCodeResult = false
        const toast = await this.toastCtrl.create({
          message: '验证码错误或已失效',
          duration: 3000
        })
        toast.present()
      })
    }else{
      const toast = await this.toastCtrl.create({
        message: '请输入验证码',
        duration: 3000
      })
      toast.present()
    }
  }
  async onRegisterPassword(form: NgForm){
    if(form.valid){
      this.onNext()
    }else{
      const toast = await this.toastCtrl.create({
        message: '密码格式不正确，请重新输入',
        duration: 3000
      })
      toast.present()
    }
  }

  imStudent() {
    this.register.type='学生'
    this.onNext()
  }
  imTeacher() {
    this.register.type='教师'
    this.onNext()
  }
  async onRegisterInfo(form: NgForm){
    if(form.valid){
      this.passportService.register(this.register).then(async (res:any)=>{
        if(res.code == 200){
          const alert = await this.alertCtrl.create({
            header: '提示',
            message: '注册成功',
            buttons: ['确定']
          })
          alert.present()
          // window.location.replace('passport/login')
          this.router.navigateByUrl('passport/login')
        }
        else{ }
      })
    }else{
      const toast = await this.toastCtrl.create({
        message: '请输入您的姓名',
        duration: 3000
      })
      toast.present()
    }
  }
}
