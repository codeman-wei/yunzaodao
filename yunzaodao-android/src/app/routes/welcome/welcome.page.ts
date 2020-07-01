import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from 'src/app/shared/services/local-storage.service';

import { Geolocation } from "@ionic-native/geolocation/ngx"
import { AlertController } from '@ionic/angular';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.page.html',
  styleUrls: ['./welcome.page.scss'],
})
export class WelcomePage implements OnInit {

  constructor(private router: Router, private geolocation:Geolocation, private alertCtrl: AlertController, private localStorageService:LocalStorageService) { }

  ngOnInit() {
//     this.geolocation.getCurrentPosition().then(async (resp) => {
//       const alert = await this.alertCtrl.create({
//         header: '提示',
//         message: '您的坐标：（'+resp.coords.longitude+', '+resp.coords.latitude+')',
//       })
//       alert.present()
//       console.log(resp.coords.latitude);
//       console.log(resp.coords.longitude);
//     }).catch((error) => {
//       console.log('Error getting location', error);
//     })
  }

  onRegister() {
    this.router.navigateByUrl('/passport/register')
  }
  onLogin() {
    this.router.navigateByUrl('/passport/login')
  }
}
