import { Component, OnInit } from '@angular/core';
import { LocalStorageService, GLOBAL_VARIABLE_KEY, USER_KEY } from 'src/app/shared/services/local-storage.service';
import { Router } from '@angular/router';
import { CommonService } from 'src/app/shared/services/common.service';
import { AlertController, ToastController } from '@ionic/angular';

@Component({
  selector: 'app-members',
  templateUrl: './members.page.html',
  styleUrls: ['./members.page.scss'],
})
export class MembersPage implements OnInit {

  classId = ''

  num = 50

  signIn_text = '发起签到'

  isTeacher = false

  classInfo = {
    'number': '',
    'name': '',
    'class': '',
    'teacher': '',
    'semester': '',
    'school': '',
    'college': '',
    'isJoinable': true,
    'isClosed': false,
    'isDeleted': false
  }

  memberList = [
    {
      'id': '1',
      'name': '张三',
      'exp': '50',
      'number': '190327071'
    },
    {
      'id': '2',
      'name': '李四',
      'exp': '44',
      'number': '190327088'
    }
  ]

  constructor(private localStorageService:LocalStorageService, private router: Router, private httpService:CommonService, private toastCtrl: ToastController, private alertCtrl: AlertController) { }

  ngOnInit() {
    this.classId = this.localStorageService.get(GLOBAL_VARIABLE_KEY,'').classId
    // 查询是否是老师的接口
    let api = '/class/isTeacher'
    let json = {
      'phone':this.localStorageService.get(USER_KEY,'').phone,
      'classId':this.classId
    }
    this.httpService.ajaxPost(api, json).then(async (res:any) =>{
      if(res.code==200){
        this.isTeacher=true
        this.signIn_text='发起签到'
      }else if(res.code==400){
        this.isTeacher=false
        this.signIn_text='参与签到'
      }
    })
    // 获取班课信息的接口
    api = '/class/info'
    json['classId'] = this.classId
    this.httpService.ajaxPost(api, json).then(async (res:any) =>{
      this.classInfo = res.data
    })

    // 获取班课成员的接口

    
  }

  async signIn(){
    if(this.classInfo.isClosed){
      const toast = await this.toastCtrl.create({
        message: '本班课已结束',
        duration: 3000
      })
      toast.present()
    }else {
      if(this.isTeacher){
        // 发起签到
        this.router.navigateByUrl('/home/class/detail/members/create-signin')
      }else{
        // 参与签到
        this.router.navigateByUrl('/home/class/detail/members/join-signin')
      }
    }
  }

  async memberDetail(number){
    if(this.isTeacher){
      // 成员详情接口
    }
  }
}
