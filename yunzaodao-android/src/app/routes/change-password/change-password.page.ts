import { Component, OnInit } from '@angular/core';
import { AlertController } from '@ionic/angular';
import { NgForm } from '@angular/forms';
import { PassportService } from 'src/app/services/passport.service';
import { LocalStorageService, USER_KEY } from 'src/app/shared/services/local-storage.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.page.html',
  styleUrls: ['./change-password.page.scss'],
})
export class ChangePasswordPage implements OnInit {

  oldPassword: ''
  newPassword: ''
  checkPassword: ''

  constructor(
    private passportService: PassportService,
    private alertCtrl: AlertController,
    private localStorageService: LocalStorageService
  ) { }

  ngOnInit() {
  }

  async onChangePassword(form: NgForm) {
    if (form.valid) {
      const userInfo = this.localStorageService.get(USER_KEY, '')
      let status = userInfo.status
      switch(status){
        case '学生':
          status = 'student'
          break
        case '教师':
          status = 'teacher'
          break
      }
      this.passportService.changePassword(this.oldPassword, this.newPassword, status).then(async (res:any) =>{
        let userInfo: any = this.localStorageService.get(USER_KEY, false)
        userInfo['isLogin'] = false
        this.localStorageService.set(USER_KEY, userInfo)
        const alert = await this.alertCtrl.create({
            header: '提示',
            message: '密码修改成功',
            backdropDismiss: false,
            buttons: [{
              text: '重新登录',
              handler: () => {
                window.location.replace('passport/login')
              }
            }]
        })
        alert.present()
      }).catch(async (err:any) => {
        console.log(err)
        const alert = await this.alertCtrl.create({
          header: '警告',
          message: '原密码错误',
          buttons: ['确定']
        })
        alert.present()
      })
    }
  }
}
