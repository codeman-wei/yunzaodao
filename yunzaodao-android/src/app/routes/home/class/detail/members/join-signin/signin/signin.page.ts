import { Component, OnInit } from '@angular/core';
import { LocalStorageService, USER_KEY, GLOBAL_VARIABLE_KEY } from 'src/app/shared/services/local-storage.service';
import { ToastController, AlertController, LoadingController } from '@ionic/angular';
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

  constructor(private localStorageService:LocalStorageService, private loadingController: LoadingController, private geolocation:Geolocation, private toastCtrl: ToastController, private httpService:CommonService, private alertCtrl: AlertController, private router: Router) { }

  ngOnInit() {
    this.courseCode = this.localStorageService.get(GLOBAL_VARIABLE_KEY, '').courseCode
    const api = '/mobile/course/info?'+'courseCode='+this.courseCode
    this.httpService.ajaxGet(api).then(async (res:any) =>{
      this.classId = res.id
    })
  }

  async start(){
    const loading = await this.loadingController.create({
      cssClass: 'my-custom-class',
      message: '请稍后...',
      duration: 2000
    })
    await loading.present();
    this.geolocation.getCurrentPosition().then(async (resp) => {
      console.log(resp.coords.latitude);
      console.log(resp.coords.longitude);
      const api='/mobile/sign/student?courseId=' + this.classId + '&code=' + this.password + '&studentId=' + this.localStorageService.get(USER_KEY, {'id': null}).id
      this.httpService.ajaxGet(api).then(async (res:any)=>{
        loading.dismiss()
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
        console.log(err)
        loading.dismiss()
        const toast = await this.toastCtrl.create({
          message: err.msg,
          duration: 3000
        })
        toast.present()
      })
    }).catch(async (error) => {
      console.log('Error getting location', error);
      loading.dismiss()
      const toast = await this.toastCtrl.create({
        message: '申请权限失败，请打开定位权限',
        duration: 3000
      })
      toast.present()
    })
  }

}
