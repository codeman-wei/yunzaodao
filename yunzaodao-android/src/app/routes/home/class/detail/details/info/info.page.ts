import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonService } from 'src/app/shared/services/common.service';
import { ToastController } from '@ionic/angular';
import { LocalStorageService, GLOBAL_VARIABLE_KEY } from 'src/app/shared/services/local-storage.service';

@Component({
  selector: 'app-info',
  templateUrl: './info.page.html',
  styleUrls: ['./info.page.scss'],
})
export class InfoPage implements OnInit {

  classId = ''

  classInfo = {
    'name': '',
    'class': '',
    'semester': '',
    'school': '',
    'college': '',
  }

  constructor(private router: Router, private httpService:CommonService, private toastCtrl: ToastController, private localStorageService: LocalStorageService) { }

  ngOnInit() {
    this.classId = this.localStorageService.get(GLOBAL_VARIABLE_KEY, '').classId
    const api='/class/info'
    const json = {
      'number': this.classId
    }
    this.httpService.ajaxPost(api, json).then(async (res:any) =>{
      this.classInfo.name = res.data.name
      this.classInfo.class = res.data.class
      this.classInfo.semester = res.data.semester
      this.classInfo.school = res.data.school
      this.classInfo.college = res.data.college
    })

  }

  async change(){
    let info = ''
    for(let item in this.classInfo){
      if(!this.classInfo[item]) {
        switch(item){
          case 'name':
            info = '课程'
            break
          case 'class':
            info = '班级'
            break
          case 'semester':
            info = '学期'
            break
          case 'school':
            info = '学校'
            break
          case 'college':
            info = '院系'
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
    const api = '/class/change/info'
    const json = {
      'number': this.classId,
      'name': this.classInfo.name,
      'class': this.classInfo.class,
      'semester': this.classInfo.semester,
      'school': this.classInfo.school,
      'college': this.classInfo.college
    }
    this.httpService.ajaxPost(api, json).then(async (res:any) =>{
      window.location.replace('home/class/detail/details')
    })
  }

}
