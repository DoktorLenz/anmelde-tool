import { Component } from '@angular/core';
import { AppMainComponent } from '../main/app.main.component';

@Component({
  selector: 'at-topbar',
  templateUrl: './app.topbar.component.html',
})
export class AppTopBarComponent {

  constructor(public app: AppMainComponent) { }
}
