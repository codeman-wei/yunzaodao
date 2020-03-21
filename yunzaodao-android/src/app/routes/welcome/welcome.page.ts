import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from 'src/app/shared/services/local-storage.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.page.html',
  styleUrls: ['./welcome.page.scss'],
})
export class WelcomePage implements OnInit {

  constructor(private router: Router, private localStorageService:LocalStorageService) { }

  ngOnInit() {
  }

  onRegister() {
    this.router.navigateByUrl('/passport/register')
  }
  onLogin() {
    this.router.navigateByUrl('/passport/login')
  }
}
