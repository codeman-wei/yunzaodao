import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonService } from 'src/app/shared/services/common.service';

@Component({
  selector: 'app-signin-info',
  templateUrl: './signin-info.page.html',
  styleUrls: ['./signin-info.page.scss'],
})
export class SigninInfoPage implements OnInit {

  absences=[]
  attendences=[]

  constructor(private activatedRoute: ActivatedRoute, private httpService:CommonService) { }

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe((data: any) => {
      const api = '/mobile/sign?signId=' + data.signId
      this.httpService.ajaxGet(api).then(async (res:any)=>{
        this.attendences = res.attendences
        this.absences = res.absences
      })
    })
  }
}
