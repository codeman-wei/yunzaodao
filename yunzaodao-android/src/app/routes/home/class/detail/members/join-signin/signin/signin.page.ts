import { Component, OnInit } from '@angular/core';
import { LocalStorageService, USER_KEY, GLOBAL_VARIABLE_KEY } from 'src/app/shared/services/local-storage.service';
import { ToastController, AlertController } from '@ionic/angular';
import { CommonService } from 'src/app/shared/services/common.service';
import { Router } from '@angular/router';

import { Geolocation } from "@ionic-native/geolocation/ngx"

@Component({
  selector: 'app-signin',
  templateUrl: './signin.page.html',
  styleUrls: ['./signin.page.scss'],
})
export class SigninPage implements OnInit {

  password = ''
  classId = ''
  courseCode = ''

  constructor(private localStorageService:LocalStorageService, private geolocation:Geolocation, private toastCtrl: ToastController, private httpService:CommonService, private alertCtrl: AlertController, private router: Router) { }

  ngOnInit() {
    this.courseCode = this.localStorageService.get(GLOBAL_VARIABLE_KEY, '').courseCode
    const api = '/mobile/course/info?'+'courseCode='+this.courseCode
    this.httpService.ajaxGet(api).then(async (res:any) =>{
      this.classId = res.id
    })
  }

  async start(){
    this.geolocation.getCurrentPosition().then(async (resp) => {
      console.log(resp.coords.latitude);
      console.log(resp.coords.longitude);
    }).catch((error) => {
      console.log('Error getting location', error);
    })
    const api='/mobile/sign/student?courseId=' + this.classId + '&code=' + this.password + '&studentId=' + this.localStorageService.get(USER_KEY, {'id': null}).id
    this.httpService.ajaxGet(api).then(async (res:any)=>{
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
    }).catch(async (err:any)=>{
      const toast = await this.toastCtrl.create({
        message: err.msg,
        duration: 3000
      })
      toast.present()
    })
  }

}
