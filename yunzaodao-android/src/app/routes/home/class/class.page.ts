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
  status = ''

  slideFlag = false  // false = created, true = joined

  classList = []

  constructor(public popoverController: PopoverController, private router:Router, private localStorageService:LocalStorageService, private httpService:CommonService) { }

  ngOnInit() {
    const userInfo = this.localStorageService.get(USER_KEY, '')
    this.phone = userInfo.phone
    this.status = userInfo.status
    switch(this.status){
      case '教师':
        this.slideFlag = false
        this.initClassList(true)
        break
      case '学生':
        this.slideFlag = true
        this.initClassList(false)
    }
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
    let api='/mobile/course/'
    const id = this.localStorageService.get(USER_KEY, '').id
    if(isCreater){
      api += 'belong'
    }else{
      api += 'join'
    }
    api += '?id=' + id
    this.httpService.ajaxGet(api).then(async (res:any)=>{
      this.classList = res
    })
  }

  async myCreate(){
    if(this.slideFlag){
      this.slideFlag = false
      if(this.status == '教师') this.initClassList(true)
      else this.classList = []
    }
  }

  async myJoin(){
    if(!this.slideFlag){
      this.slideFlag = true
      if(this.status == '学生') this.initClassList(false)
      else this.classList = []
    }
  }

  async detailInfo(index){
    this.localStorageService.set(GLOBAL_VARIABLE_KEY,{'courseCode':index})
    this.router.navigateByUrl('/home/class/detail')
  }
}
