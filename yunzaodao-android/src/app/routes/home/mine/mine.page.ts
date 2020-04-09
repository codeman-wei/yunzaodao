import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AlertController } from '@ionic/angular';
import { LocalStorageService, USER_KEY } from 'src/app/shared/services/local-storage.service';

@Component({
  selector: 'app-mine',
  templateUrl: './mine.page.html',
  styleUrls: ['./mine.page.scss'],
})
export class MinePage implements OnInit {

  userInfo = {
    name: ""
  }

  constructor(private router: Router,private alertCtrl: AlertController,private localStorageService:LocalStorageService) { }

  ngOnInit() {
    const userInfo = this.localStorageService.get(USER_KEY, '')
    this.userInfo.name = userInfo.name
  }

  async onLogout() {
    const alert = await this.alertCtrl.create({
      header: '警告',
      message: '是否退出当前帐号',
      buttons: [
        {
          text: '取消',
          role: 'cancel'
        },
        {
          text: '确定',
          handler: () => {
            const userInfo = this.localStorageService.get(USER_KEY, '')
            userInfo.isLogined = false
            this.localStorageService.set(USER_KEY, userInfo)
            window.location.replace('passport/login')
          }
        }
      ]
    })
    alert.present()
  }
}
