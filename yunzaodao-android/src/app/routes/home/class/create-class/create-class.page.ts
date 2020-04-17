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

  classInfo = {
    'name': '',
    'class': '',
    'semester': '',
    'school': '',
    'college': ''
  }

  constructor(private router: Router, private httpService:CommonService, private toastCtrl: ToastController, private alertCtrl: AlertController, private localStorageService: LocalStorageService) { }

  ngOnInit() {
  }

  async create() {
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
    const api = '/class/create'
    this.classInfo['phone'] = this.localStorageService.get(USER_KEY,'').phone
    this.httpService.ajaxPost(api, this.classInfo).then(async (res:any)=>{
      if(res.code == 200){
        const alert = await this.alertCtrl.create({
          header: '提示',
          backdropDismiss: false,
          message: '班课 '+this.classInfo.name+' 已创建',
          buttons: [{
            text: '确定',
            handler: () => {
              this.router.navigateByUrl('home/class')
            }
          }]
        })
        alert.present()
      }
    })
  }

}
