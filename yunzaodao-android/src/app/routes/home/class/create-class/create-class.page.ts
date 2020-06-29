import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonService } from 'src/app/shared/services/common.service';
import { ToastController, AlertController } from '@ionic/angular';
import { LocalStorageService, USER_KEY } from 'src/app/shared/services/local-storage.service';

@Component({
  selector: 'app-create-class',
  templateUrl: './create-class.page.html',
  styleUrls: ['./create-class.page.scss'],
})
export class CreateClassPage implements OnInit {

  college = ''
  colleges = []

  classInfo = {
    'courseName': '心算',
    'className': '19级计算机专硕',
    'semester': '2019-2020-2',
    'school': '福州大学',
    'college': {}
  }

  constructor(private router: Router, private httpService:CommonService, private toastCtrl: ToastController, private alertCtrl: AlertController, private localStorageService: LocalStorageService) { }

  ngOnInit() {
    const api='/mobile/college'
    this.httpService.ajaxGet(api).then((res:any)=>{
      for(let i in res[0].children){
        const item = {
          'id': res[0].children[i].id,
          'label': res[0].children[i].label
        }
        this.colleges.push(item)
      }
    }).catch((err)=>{
      console.log(err)
    })
  }

  async create() {
    let info = ''
    for(let item in this.classInfo){
      if(!this.classInfo[item]) {
        switch(item){
          case 'courseName':
            info = '课程'
            break
          case 'className':
            info = '班级'
            break
          case 'semester':
            info = '学期'
            break
          case 'school':
            info = '学校'
            break
        }
        const toast = await this.toastCtrl.create({
          message: info + ' 不能为空',
          duration: 3000
        })
        toast.present()
        return
      }
    }
    if(JSON.stringify(this.classInfo['college']) == '{}' && !this.college){
      const toast = await this.toastCtrl.create({
        message: '院系 不能为空',
        duration: 3000
      })
      toast.present()
      return
    }
    const api = '/mobile/course'
    const userInfo = this.localStorageService.get(USER_KEY,'')
    this.classInfo['teacherName'] = userInfo.name
    this.classInfo['createUser'] = { id : userInfo.id }
    this.classInfo.college = { id : Number(this.college) }
    this.httpService.ajaxPost(api, this.classInfo).then(async (res:any)=>{
      const alert = await this.alertCtrl.create({
        header: '提示',
        backdropDismiss: false,
        message: '班课 '+this.classInfo.courseName+' 已创建',
        buttons: [{
          text: '确定',
          handler: () => {
            window.location.replace('home/class')
          }
        }]
      })
      alert.present()
    }).catch(err=>{
      console.log(err)
    })
  }

}
