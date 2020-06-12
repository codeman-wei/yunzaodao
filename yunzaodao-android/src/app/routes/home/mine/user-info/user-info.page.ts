import { Component, OnInit } from '@angular/core';
import { LocalStorageService, USER_KEY } from 'src/app/shared/services/local-storage.service';
import { CommonService } from 'src/app/shared/services/common.service';
import { ToastController } from '@ionic/angular';

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.page.html',
  styleUrls: ['./user-info.page.scss'],
})
export class UserInfoPage implements OnInit {

  name: ''

  college = ''
  colleges = []

  userInfo = {
    'id': '',
    'name': '',
    'phone': '',
    'sex': '',
    'status': '',
    'school': '',
    'college': { "id": 8 },
    'number': ''
  }

  constructor(private httpService:CommonService, private toastCtrl: ToastController, private localStorageService:LocalStorageService) { }

  ngOnInit() {
    const userInfo = this.localStorageService.get(USER_KEY, '')
    this.userInfo.phone = userInfo.phone
    let api='/mobile/userInfo?phone=' + this.userInfo.phone
    this.httpService.ajaxGet(api).then(async (res:any)=>{
      this.userInfo=res
      api='/mobile/college'
      this.httpService.ajaxGet(api).then((res:any)=>{
        for(let i in res[0].children){
          if(res[0].children[i].label === this.userInfo.college){
            this.college = res[0].children[i].id.toString()
          }
          const item = {
            'id': res[0].children[i].id,
            'label': res[0].children[i].label
          }
          this.colleges.push(item)
        }
      }).catch((err)=>{
        console.log(err)
      })
    }).catch((err)=>{
      console.log(err)
    })
  }

  async save() {
    this.userInfo.college = {id: Number(this.college)}
    this.userInfo['studentNumber'] = this.userInfo.number
    if(this.userInfo.name && this.userInfo.number){
      const api='/mobile/student/update'
      const json = this.userInfo
      this.httpService.ajaxPost(api,json).then((res:any)=>{
          this.localStorageService.set(USER_KEY, this.userInfo)
          window.location.replace('home/mine')
      }).catch((err)=>{
        console.log(err)
      })
    }
    else {
      const toast = await this.toastCtrl.create({
        message: '请输入完整信息',
        duration: 3000,
      })
      toast.present()
    }
  }
}
