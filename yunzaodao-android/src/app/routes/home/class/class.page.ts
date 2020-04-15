import { Component, OnInit } from '@angular/core';
import { PopoverController } from '@ionic/angular';
import { PopoverComponent } from 'src/app/routes/home/class/component/popover/popover.component'

@Component({
  selector: 'app-class',
  templateUrl: './class.page.html',
  styleUrls: ['./class.page.scss'],
})
export class ClassPage implements OnInit {


  constructor(public popoverController: PopoverController) { }

  ngOnInit() {
  }

  async more(ev: any){
    const popover = await this.popoverController.create({
      component: PopoverComponent,
      event: ev,
      translucent: true
    });
    return await popover.present();
  }
}
