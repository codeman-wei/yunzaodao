import { Component, OnInit } from '@angular/core';
import { ToastController, AlertController } from '@ionic/angular';
import { LocalStorageService, GLOBAL_VARIABLE_KEY, USER_KEY } from 'src/app/shared/services/local-storage.service';
import { CommonService } from 'src/app/shared/services/common.service';
import { async } from '@angular/core/testing';
import { Router } from '@angular/router';

@Component({
  selector: 'app-join-signin',
  templateUrl: './join-signin.page.html',
  styleUrls: ['./join-signin.page.scss'],
})
export class JoinSigninPage implements OnInit {

  classId=''

  historyList=[
    {
      'id':1,
      'day':'2020-02-29',
      'time':'08:02',
      'isSignined':true
    },
    {
      'id':2,
      'day':'2020-03-07',
      'time':'08:01',
      'isSignined':false
    }
  ]

  constructor(private localStorageService:LocalStorageService, private toastCtrl: ToastController, private httpService:CommonService, private alertCtrl: AlertController, private router: Router) { }

  ngOnInit() {
    this.classId = this.localStorageService.get(GLOBAL_VARIABLE_KEY, '').clasId
  }

  async signin(){
    const api='/class/signin/join'
    const json={
      'classId': this.classId
    }
    this.httpService.ajaxPost(api, json).then(async (res:any)=>{
      if(res.code == 400){
        const toast = await this.toastCtrl.create({
          message: '老师还未发起签到或签到已结束',
          duration: 3000
        })
        toast.present()
      }else if(res.code == 200){
        const api = '/class/signin/join/common'
        const json={
          'classId': this.classId,
          'phone': this.localStorageService.get(USER_KEY,'').phone
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
      }else if(res.code == 202){
        this.router.navigateByUrl('/home/class/detail/members/join-signin/signin')
      }
    })
  }
}
