import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastController, AlertController, IonSlides, LoadingController } from '@ionic/angular';
import { CommonService } from 'src/app/shared/services/common.service';
import { LocalStorageService, GLOBAL_VARIABLE_KEY } from 'src/app/shared/services/local-storage.service';

import { Geolocation } from "@ionic-native/geolocation/ngx"

@Component({
  selector: 'app-signin',
  templateUrl: './signin.page.html',
  styleUrls: ['./signin.page.scss'],
})
export class SigninPage implements OnInit {
  
  password = ''

  type = ''
  title = ''
  classId = ''
  courseCode = ''
  attendances = []
  absences = []

  isStart = false

  constructor(private localStorageService:LocalStorageService,private loadingController: LoadingController,private geolocation:Geolocation,private activatedRoute: ActivatedRoute, private toastCtrl: ToastController, private httpService:CommonService, private router: Router, private alertCtrl: AlertController) { }
  
  @ViewChild('createSigninSlides', { static: true }) createSigninSlides: IonSlides
  ngOnInit() {
    this.createSigninSlides.lockSwipeToNext(true)
    this.createSigninSlides.lockSwipeToPrev(true)
    this.courseCode = this.localStorageService.get(GLOBAL_VARIABLE_KEY, '').courseCode
    const api = '/mobile/course/info?'+'courseCode='+this.courseCode
    this.httpService.ajaxGet(api).then(async (res:any) =>{
      this.classId = res.id
    })
    // 接受路由传过来的签到种类
    this.activatedRoute.queryParams.subscribe((data: any) => {
      this.type = data.type  //此时的data存的就是上个页面传过来的值
      switch (this.type){
        case 'common':
          this.title = '一键签到'
          this.password = 'padding'
          break
        // case 'pattern':
        //   this.title = '图案签到'
        //   this.password = 'padding'
        //   break
        case 'password':
          this.title = '密码签到'
          this.password = ''
          break
      }
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
      this.isStart = true
      const api='/mobile/release/sign'
      const json = {
        'course':{'id':this.classId},
        'code':this.password
      }
      this.httpService.ajaxPost(api,json).then(async (res:any)=>{
        loading.dismiss()
      }).catch(err=>{
        console.log(err)
        loading.dismiss()
        return
      })
      const toast = await this.toastCtrl.create({
        message: '签到已开始，请通知学生进行签到',
        duration: 3000
      })
      toast.present()
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

  async finish(){
    const alert = await this.alertCtrl.create({
      header: '警告',
      message: '是否结束签到',
      buttons: [
        {
          text: '取消',
          role: 'cancel'
        },
        {
          text: '确定',
          handler: () => {
            const api = '/mobile/sign/close?courseId='+this.classId
            this.httpService.ajaxGet(api).then(async (res:any)=>{
              this.attendances = res.attendances
              this.absences = res.absences
              this.convert2DateTime()
              // 结束签到
              this.isStart = false
              const toast = await this.toastCtrl.create({
                message: '签到已结束',
                duration: 3000
              })
              toast.present()
              this.createSigninSlides.lockSwipeToNext(false)
              this.createSigninSlides.slideNext()
              this.createSigninSlides.lockSwipeToNext(true)
            }).catch(async (err:any)=>{
              const toast = await this.toastCtrl.create({
                message: '结束签到失败，请重试',
                duration: 3000
              })
              toast.present()
            })
          }
        }
      ]
    })
    alert.present()
  }
  async convert2DateTime(){
    for(let index in this.attendances){
      const now = new Date(this.attendances[index].signTime);
      const hour = now.getHours();
      let h = this.padding(hour)
      const minute = now.getMinutes();
      let m = this.padding(minute)
      const second = now.getSeconds();
      let s = this.padding(second)
      this.attendances[index]['time'] = h + ':' + m + ':' + s
    }
  }
  padding(number:Number){
    return number < 10 ? ('0' + number) : number
  }
}
