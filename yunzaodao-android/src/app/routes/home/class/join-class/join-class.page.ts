import { Component, OnInit, ViewChild } from '@angular/core';
import { ToastController, IonSlides, AlertController } from '@ionic/angular';
import { CommonService } from 'src/app/shared/services/common.service';
import { LocalStorageService, USER_KEY } from 'src/app/shared/services/local-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-join-class',
  templateUrl: './join-class.page.html',
  styleUrls: ['./join-class.page.scss'],
})
export class JoinClassPage implements OnInit {

  classNumber = '1314'

  classInfo = {
    'name': '',
    'class': '',
    'teacher': '',
    'semester': ''
  }

  constructor(private router: Router, private httpService:CommonService, private toastCtrl: ToastController, private alertCtrl: AlertController, private localStorageService: LocalStorageService) { }

  @ViewChild('joinClassSlides', { static: true }) joinClassSlides: IonSlides
  ngOnInit() {
    this.joinClassSlides.lockSwipeToNext(true)
    this.joinClassSlides.lockSwipeToPrev(true)
  }

  async findClass(){
    if(this.classNumber != ''){
      const api = '/class/info' + '?number=' + this.classNumber
      this.httpService.ajaxGet(api).then(async (res:any)=>{
        if(res.code == 200){
          this.classInfo = res.data
          this.joinClassSlides.lockSwipeToNext(false)
          this.joinClassSlides.slideNext()
          this.joinClassSlides.lockSwipeToNext(true)
        }else if(res.code == 400){
          const toast = await this.toastCtrl.create({
            message: '你输入的班课不存在，请重新输入',
            duration: 3000
          })
          toast.present()
        }
      }).catch((err)=>{
        console.log(err)
      })
    }
    else{
      const toast = await this.toastCtrl.create({
        message: '请输入班课号',
        duration: 3000
      })
      toast.present()
    }
  }
  
  async join(){
    const api = '/class/join'
    const json = {
      "number" : this.classNumber,
      "phone" : this.localStorageService.get(USER_KEY, {"phone":null}).phone
    }
    this.httpService.ajaxPost(api,json).then(async (res:any)=>{
      if(res.code == 200){
        const alert = await this.alertCtrl.create({
          header: '提示',
          backdropDismiss: false,
          message: '您已加入班课 '+this.classInfo.name,
          buttons: [{
            text: '确定',
            handler: () => {
              this.router.navigateByUrl('home/class')
            }
          }]
        })
        alert.present()
      }else if(res.code == 400){
        console.log(res.code)
      }
    }).catch((err)=>{
        console.log(err)
    })
  }
}
