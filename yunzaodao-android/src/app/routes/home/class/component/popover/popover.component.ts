import { Component, OnInit } from '@angular/core';
import { PopoverController, ToastController } from '@ionic/angular';
import { Router } from '@angular/router';
import { LocalStorageService, USER_KEY } from 'src/app/shared/services/local-storage.service';

@Component({
  selector: 'app-popover',
  templateUrl: './popover.component.html',
  styleUrls: ['./popover.component.scss'],
})
export class PopoverComponent implements OnInit {

  constructor(private router: Router, public popoverController: PopoverController, private localStorageService:LocalStorageService, private toastCtrl: ToastController) { }

  ngOnInit() {}

  dismissPopover() {
    this.popoverController.dismiss()
  }
  async createClass(){
    this.dismissPopover()
    if(this.localStorageService.get(USER_KEY,{}).status === '教师'){
      this.router.navigateByUrl("/home/class/create-class")
    }
    else{
      const toast = await this.toastCtrl.create({
        message: '学生暂不能创建班课',
        duration: 3000,
      })
      toast.present()
    }
  }
  async joinClass(){
    this.dismissPopover()
    if(this.localStorageService.get(USER_KEY,{}).status === '学生'){
      this.router.navigateByUrl("/home/class/join-class")
    }
    else{
      const toast = await this.toastCtrl.create({
        message: '教师暂不能加入班课',
        duration: 3000,
      })
      toast.present()
    }
  }
}
