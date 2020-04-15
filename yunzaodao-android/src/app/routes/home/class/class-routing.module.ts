import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ClassPage } from './class.page';
import { PopoverComponent } from './component/popover/popover.component';
import { IonicModule } from '@ionic/angular';

const routes: Routes = [
  {
    path: '',
    component: ClassPage
  }
];

@NgModule({
  imports: [IonicModule, RouterModule.forChild(routes)],
  exports: [RouterModule],
  declarations: [
    PopoverComponent
  ],
  entryComponents:[
    PopoverComponent
  ]
})
export class ClassPageRoutingModule {}
