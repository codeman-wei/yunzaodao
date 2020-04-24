import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-create-signin',
  templateUrl: './create-signin.page.html',
  styleUrls: ['./create-signin.page.scss'],
})
export class CreateSigninPage implements OnInit {

  historyList=[
    {
      'id':1,
      'day':'2020-02-29',
      'time':'08:02',
      'numSignin':54,
      'numMember':60
    },
    {
      'id':2,
      'day':'2020-03-07',
      'time':'08:01',
      'numSignin':58,
      'numMember':60
    }
  ]

  constructor() { }

  ngOnInit() {
  }

}
