import { Component, OnInit } from '@angular/core';
import { CommonService } from 'src/app/shared/services/common.service'

@Component({
  selector: 'app-about-us',
  templateUrl: './about-us.page.html',
  styleUrls: ['./about-us.page.scss'],
})
export class AboutUsPage implements OnInit {

  constructor(private httpService:CommonService) { }

  ngOnInit() {
    this.getDataTest()
  }

  getDataTest(){
    const api = '/query'
    this.httpService.ajaxGet(api).then((Response)=>{
      console.log(Response)
    })
  }
}
