import { Component, OnInit } from '@angular/core';
import { LocalStorageService, USER_KEY, GLOBAL_VARIABLE_KEY } from 'src/app/shared/services/local-storage.service';
import { ToastController, AlertController } from '@ionic/angular';
import { CommonService } from 'src/app/shared/services/common.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.page.html',
  styleUrls: ['./signin.page.scss'],
})
export class SigninPage implements OnInit {

  password = ''

  constructor(private localStorageService:LocalStorageService, private toastCtrl: ToastController, private httpService:CommonService, private alertCtrl: AlertController, private router: Router) { }

  ngOnInit() {
  }

  async start(){
    const api='/class/signin/join/password'
    const json={
      'phone':this.localStorageService.get(USER_KEY,'').phone,
      'classId':this.localStorageService.get(GLOBAL_VARIABLE_KEY,'').classId,
      'password':this.password
    }
    this.httpService.ajaxPost(api,json).then(async (res:any)=>{
      const alert = await this.alertCtrl.create({
        header: '提示',
        message: '签到成功',
        buttons: [
          {
            text: '确定',
            handler: () => {
              this.router.navigateByUrl('/home/class/detail/members')
            }
          }
        ]
      })
      alert.present()
    })
  }

}
