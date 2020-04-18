import { Component, OnInit } from '@angular/core';
import { LocalStorageService, GLOBAL_VARIABLE_KEY } from 'src/app/shared/services/local-storage.service';

@Component({
  selector: 'app-details',
  templateUrl: './details.page.html',
  styleUrls: ['./details.page.scss'],
})
export class DetailsPage implements OnInit {

  constructor(private localStorageService:LocalStorageService) { }

  ngOnInit() {
    console.log(this.localStorageService.get(GLOBAL_VARIABLE_KEY,'').classId)
  }

}
