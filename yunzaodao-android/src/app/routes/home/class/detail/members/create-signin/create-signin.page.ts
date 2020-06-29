import { Component, OnInit } from '@angular/core';
import { LocalStorageService, GLOBAL_VARIABLE_KEY } from 'src/app/shared/services/local-storage.service';
import { CommonService } from 'src/app/shared/services/common.service';

@Component({
  selector: 'app-create-signin',
  templateUrl: './create-signin.page.html',
  styleUrls: ['./create-signin.page.scss'],
})
export class CreateSigninPage implements OnInit {

  courseCode= ''

  historyList=[
    {
      'id':1,
      'createTime':1589370317000,
      'attendance':0,
      'absence':10,
      'day':'',
      'time':''
    },
    {
      'id':2,
      'createTime':1589370317000,
      'attendance':10,
      'absence':0,
      'day':'',
      'time':''
    }
  ]

  constructor(private localStorageService:LocalStorageService, private httpService:CommonService) { }

  ngOnInit() {
    this.courseCode = this.localStorageService.get(GLOBAL_VARIABLE_KEY,'').courseCode
    // 获取班课信息
    const api = '/mobile/course/info?'+'courseCode='+this.courseCode
    this.httpService.ajaxGet(api).then(async (res:any) =>{
      this.historyList=res.signHistory
      this.convert2DateTime()
    })
  }

  async convert2DateTime(){
    for(let index in this.historyList){
      const now = new Date(this.historyList[index].createTime);
      const year = now.getFullYear();
      const month = this.padding(now.getMonth() + 1);
      const date = this.padding(now.getDate());
      const hour = this.padding(now.getHours());
      const minute = this.padding(now.getMinutes());
      const second = this.padding(now.getSeconds());
      this.historyList[index]['day'] = year + '-' + month + '-' + date
      this.historyList[index]['time'] = hour + ':' + minute + ':' + second
    }
  }
  padding(number:Number){
    return number < 10 ? ('0' + number) : number
  }
}
