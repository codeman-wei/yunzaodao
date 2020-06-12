import { Component, OnInit } from '@angular/core';
import { HttpService } from 'src/app/shared/services/http.service';

@Component({
  selector: 'app-about-us',
  templateUrl: './about-us.page.html',
  styleUrls: ['./about-us.page.scss'],
})
export class AboutUsPage implements OnInit {

  constructor(private httpService: HttpService) { }

  ngOnInit() {
    // requst 拦截器测试
    // const api = '/passport/login'
    // const json = {
    //   'username':'15695917757',
    //   'password':'admin'
    // }
    // this.httpService.request('post', api, json).subscribe(res =>{
    //   console.log(res)
    // })
  }
}
