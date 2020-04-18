import { Component, OnInit } from '@angular/core';
import { PopoverController } from '@ionic/angular';
import { PopoverComponent } from 'src/app/routes/home/class/component/popover/popover.component'
import { LocalStorageService, USER_KEY, GLOBAL_VARIABLE_KEY } from 'src/app/shared/services/local-storage.service';
import { CommonService } from 'src/app/shared/services/common.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-class',
  templateUrl: './class.page.html',
  styleUrls: ['./class.page.scss'],
})
export class ClassPage implements OnInit {

  phone = ''

  slideFlag = false  // false = created, true = joined

  classList = []

  constructor(public popoverController: PopoverController, private router:Router, private localStorageService:LocalStorageService, private httpService:CommonService) { }

  ngOnInit() {
    const userInfo = this.localStorageService.get(USER_KEY, '')
    this.phone = userInfo.phone
    this.initClassList(true)
  }

  async more(ev: any){
    const popover = await this.popoverController.create({
      component: PopoverComponent,
      event: ev,
      translucent: true
    });
    return await popover.present();
  }

  async initClassList(isCreater){
    const api='/class/list'
    const json={
      'phone': this.phone,
      'isCreater': isCreater
    }
    this.httpService.ajaxPost(api, json).then(async (res:any)=>{
      if(res.code == 200){
        this.classList=res.data
      }
    })
  }

  async myCreate(){
    if(this.slideFlag){
      this.slideFlag = false
      this.initClassList(true)
    }
  }

  async myJoin(){
    if(!this.slideFlag){
      this.slideFlag = true
      this.initClassList(false)
    }
  }

  async detailInfo(index){
    this.localStorageService.set(GLOBAL_VARIABLE_KEY,{'classId':index})
    this.router.navigateByUrl('/home/class/detail')
  }
}
