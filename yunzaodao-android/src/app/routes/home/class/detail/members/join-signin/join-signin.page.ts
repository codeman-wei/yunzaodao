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
  courseCode=''

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
    this.courseCode = this.localStorageService.get(GLOBAL_VARIABLE_KEY, '').courseCode
    const api = '/mobile/course/info?'+'courseCode='+this.courseCode
    this.httpService.ajaxGet(api).then(async (res:any) =>{
      this.classId = res.id
    })
  }

  async signin(){
    const api='/mobile/sign/check?courseId=' + this.classId
    this.httpService.ajaxGet(api).then(async (res:any)=>{
      this.router.navigateByUrl('/home/class/detail/members/join-signin/signin')
    }).catch(async (err:any)=>{
      const toast = await this.toastCtrl.create({
        message: '老师还未发起签到',
        duration: 3000
      })
      toast.present()
    })
  }
}
