import { Component, OnInit } from '@angular/core';
import { LocalStorageService, GLOBAL_VARIABLE_KEY, USER_KEY } from 'src/app/shared/services/local-storage.service';
import { Router } from '@angular/router';
import { CommonService } from 'src/app/shared/services/common.service';
import { AlertController } from '@ionic/angular';

@Component({
  selector: 'app-details',
  templateUrl: './details.page.html',
  styleUrls: ['./details.page.scss'],
})
export class DetailsPage implements OnInit {

  classId = ''

  isTeacher = true

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

  constructor(private localStorageService:LocalStorageService, private router: Router, private httpService:CommonService, private alertCtrl: AlertController) { }

  ngOnInit() {
    this.classId = this.localStorageService.get(GLOBAL_VARIABLE_KEY,'').classId
    // switch (this.localStorageService.get(USER_KEY,'').status) {
    //   case '学生':
    //     this.isTeacher=false
    //     break
    //   case '教师':
    //     this.isTeacher=true
    //     break
    //   default:
    //     this.isTeacher=false
    // }
    const api = '/class/info'
    const json = {
      'number': this.classId
    }
    this.httpService.ajaxPost(api, json).then(async (res:any) =>{
      this.classInfo = res.data
    })
    
  }

  async checkboxChange(){
    const api = '/class/change/permission'
    const json = {
      'number': this.classId,
      'permission': this.classInfo.isJoinable
    }
    this.httpService.ajaxPost(api, json).then(async (res:any) =>{
      console.log('changed checkbox')
    })
  }

  async doChange(){
    this.router.navigateByUrl('/home/class/detail/details/info')
  }

  async closeClass(){
    const alert = await this.alertCtrl.create({
      header: '警告',
      message: '是否结束当前班课',
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
              this.classInfo.isClosed = true
            })
          }
        }
      ]
    })
    alert.present()
  }

  async deleteClass(){
    const alert = await this.alertCtrl.create({
      header: '警告',
      message: '是否删除当前班课',
      buttons: [
        {
          text: '取消',
          role: 'cancel'
        },
        {
          text: '确定',
          handler: () => {
            const api = '/class/change/delete'
            const json = {
              'number': this.classId
            }
            this.httpService.ajaxPost(api,json).then(async (res:any)=>{
              this.classInfo.isDeleted = true
            })
          }
        }
      ]
    })
    alert.present()
  }

  async exitClass(){
    const alert = await this.alertCtrl.create({
      header: '警告',
      message: '是否退出当前班课',
      buttons: [
        {
          text: '取消',
          role: 'cancel'
        },
        {
          text: '确定',
          handler: () => {
            const api = '/class/change/exit'
            const json = {
              'number': this.classId,
              'phone': this.localStorageService.get(GLOBAL_VARIABLE_KEY,'').phone
            }
            this.httpService.ajaxPost(api,json).then(async (res:any)=>{
              window.location.replace('/home/class')
            })
          }
        }
      ]
    })
    alert.present()
  }
}
