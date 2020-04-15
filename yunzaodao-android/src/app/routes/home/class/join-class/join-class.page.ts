import { Component, OnInit } from '@angular/core';
import { ToastController } from '@ionic/angular';

@Component({
  selector: 'app-join-class',
  templateUrl: './join-class.page.html',
  styleUrls: ['./join-class.page.scss'],
})
export class JoinClassPage implements OnInit {

  classNumber = ''

  constructor(private toastCtrl: ToastController) { }

  ngOnInit() {
  }

  async findClass(){
    if(this.classNumber != ''){
      
    }
    else{
      const toast = await this.toastCtrl.create({
        message: '请输入班课号',
        duration: 3000
      })
      toast.present()
    }
  }
}
