import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastController, AlertController } from '@ionic/angular';
import { CommonService } from 'src/app/shared/services/common.service';
import { LocalStorageService, GLOBAL_VARIABLE_KEY } from 'src/app/shared/services/local-storage.service';

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

  isStart = false

  constructor(private localStorageService:LocalStorageService,public activatedRoute: ActivatedRoute, private toastCtrl: ToastController, private httpService:CommonService, private router: Router, private alertCtrl: AlertController) { }

  ngOnInit() {
    this.classId = this.localStorageService.get(GLOBAL_VARIABLE_KEY, '').classId
    // 接受路由传过来的签到种类
    this.activatedRoute.queryParams.subscribe((data: any) => {
      this.type = data.type  //此时的data存的就是上个页面传过来的值
      this.classId = data.classId
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
    this.isStart = true
    const api='/class/signin/create/' + this.type
    const json = {
      'classId':this.classId
    }
    this.httpService.ajaxPost(api,json).then(async (res:any)=>{
      console.log('开始签到')
    }).catch(err=>{
      console.log(err)
      return
    })
    const toast = await this.toastCtrl.create({
      message: '签到已开始，请通知学生进行签到',
      duration: 3000
    })
    toast.present()
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
            const api = '/class/change/close'
            const json = {
              'number': this.classId
            }
            this.httpService.ajaxPost(api,json).then(async (res:any)=>{
              // 结束签到
              this.isStart = false
              const toast = await this.toastCtrl.create({
                message: '签到已结束',
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
}
