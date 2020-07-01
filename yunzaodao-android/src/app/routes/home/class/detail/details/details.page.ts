import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { LocalStorageService, GLOBAL_VARIABLE_KEY, USER_KEY } from 'src/app/shared/services/local-storage.service';
import { Router } from '@angular/router';
import { CommonService } from 'src/app/shared/services/common.service';
import { AlertController } from '@ionic/angular';
declare var QRious: any;

@Component({
  selector: 'app-details',
  templateUrl: './details.page.html',
  styleUrls: ['./details.page.scss'],
})
export class DetailsPage implements OnInit {

  courseCode = ''

  isTeacher = true

  classInfo = {
    'id': '',
    'courseCode': '',
    'courseName': '',
    'className': '',
    'teacherName': '',
    'semester': '',
    'school': '福州大学',
    'college': {},
    'joinPermission': null,
    'enabled': null,
  }
  @ViewChild('qr', {static: true}) qr: ElementRef;
  constructor(private localStorageService:LocalStorageService, private router: Router, private httpService:CommonService, private alertCtrl: AlertController) { }
  
  ngOnInit() {
    const userInfo = this.localStorageService.get(USER_KEY,'')
    this.courseCode = this.localStorageService.get(GLOBAL_VARIABLE_KEY,'').courseCode
    let api = '/mobile/course/check?'+'courseCode='+this.courseCode+'&'+'phone='+userInfo.phone
    this.httpService.ajaxGet(api).then(res =>{
      this.isTeacher = true
    }).catch(err =>{
      this.isTeacher = false
    })
    api = '/mobile/course/info?'+'courseCode='+this.courseCode
    this.httpService.ajaxGet(api).then(async (res:any) =>{
      for(let item in res){
        this.classInfo[item] = res[item]
      }
    })
    var qr = new QRious({
      element: this.qr.nativeElement,
      size: 180,
      value: this.courseCode
    })
  }

  async checkboxChange(){
    let api = '/mobile/course/update'
    const json = {
      'id': this.classInfo.id,
      'joinPermission': this.classInfo.joinPermission
    }
    this.httpService.ajaxPut(api, json).then(async (res:any) =>{
      console.log('change to ' + json.joinPermission + ' success')
    })
  }

  async doChange(){
    this.router.navigateByUrl('/home/class/detail/details/info')
  }

  async closeClass(){
    const alert = await this.alertCtrl.create({
      header: '警告',
      message: '是否结束当前班课',
      buttons: [
        {
          text: '取消',
          role: 'cancel'
        },
        {
          text: '确定',
          handler: () => {
            const api = '/mobile/course/update'
            const json = {
              'id': this.classInfo.id,
              'enabled': false
            }
            this.httpService.ajaxPut(api,json).then(async (res:any)=>{
              this.classInfo.enabled = false
            })
          }
        }
      ]
    })
    alert.present()
  }

  // async deleteClass(){
  //   const alert = await this.alertCtrl.create({
  //     header: '警告',
  //     message: '是否删除当前班课',
  //     buttons: [
  //       {
  //         text: '取消',
  //         role: 'cancel'
  //       },
  //       {
  //         text: '确定',
  //         handler: () => {
  //           const api = '/class/change/delete'
  //           const json = {
  //             'number': this.classId
  //           }
  //           this.httpService.ajaxPost(api,json).then(async (res:any)=>{
  //             this.classInfo.isDeleted = true
  //           })
  //         }
  //       }
  //     ]
  //   })
  //   alert.present()
  // }

  async exitClass(){
    const alert = await this.alertCtrl.create({
      header: '警告',
      message: '是否退出当前班课',
      buttons: [
        {
          text: '取消',
          role: 'cancel'
        },
        {
          text: '确定',
          handler: () => {
            const api = '/mobile/quit/course?'+'userId='+this.localStorageService.get(USER_KEY, {"id":null}).id+'&'+'courseCode='+this.courseCode
            this.httpService.ajaxGet(api).then(async (res:any)=>{
              window.location.replace('/home/class')
            })
          }
        }
      ]
    })
    alert.present()
  }
}
