import { Component, OnInit } from '@angular/core';
import { PopoverController } from '@ionic/angular';
import { Router } from '@angular/router';

@Component({
  selector: 'app-popover',
  templateUrl: './popover.component.html',
  styleUrls: ['./popover.component.scss'],
})
export class PopoverComponent implements OnInit {

  constructor(private router: Router, public popoverController: PopoverController) { }

  ngOnInit() {}

  dismissPopover() {
    this.popoverController.dismiss()
  }
  createClass(){
    this.dismissPopover()
    this.router.navigateByUrl("/home/class/create-class")
  }
  joinClass(){
    this.dismissPopover()
    this.router.navigateByUrl("/home/class/join-class")
  }
}
