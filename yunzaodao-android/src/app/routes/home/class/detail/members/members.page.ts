import { Component, OnInit } from '@angular/core';
import { LocalStorageService, GLOBAL_VARIABLE_KEY } from 'src/app/shared/services/local-storage.service';

@Component({
  selector: 'app-members',
  templateUrl: './members.page.html',
  styleUrls: ['./members.page.scss'],
})
export class MembersPage implements OnInit {

  constructor(private localStorageService:LocalStorageService) { }

  ngOnInit() {
    console.log(this.localStorageService.get(GLOBAL_VARIABLE_KEY,'').classId)
  }

}
