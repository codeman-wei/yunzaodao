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

  userInfo = {
    'name': '',
    'phone': '',
    'birthYear': '',
    'sex': '',
    'status': '',
    'school': '',
    'college': '',
    'number': ''
  }

  constructor(private httpService:CommonService, private toastCtrl: ToastController, private localStorageService:LocalStorageService) { }

  ngOnInit() {
    const userInfo = this.localStorageService.get(USER_KEY, '')
    this.userInfo.phone = userInfo.phone
    const api='/userInfo?phone=' + this.userInfo.phone
    this.httpService.ajaxGet(api).then(async (res:any)=>{
      this.userInfo=res.data
    })
  }

  async save() {
    if(this.userInfo.name){
      const api='/userInfo'
      const json = this.userInfo
      this.httpService.ajaxPost(api,json).then((res:any)=>{
        if(res.code == 200){
          this.localStorageService.set(USER_KEY, this.userInfo)
          window.location.replace('home/mine')
        }else {
          console.log("error")
        }
      }).catch((err)=>{
        console.log('网络错误')
      })
    }
    else {
      const toast = await this.toastCtrl.create({
        message: '姓名不能为空',
        duration: 3000,
      })
      toast.present()
    }
  }
}
