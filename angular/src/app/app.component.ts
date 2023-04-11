import { Component, OnInit } from '@angular/core';
import { PrimeNGConfig } from 'primeng/api';

@Component({
  selector: 'at-root',
  templateUrl: './app.component.html',
})
export class AppComponent implements OnInit {

  menuMode = 'static';

  theme = 'absolution';

  inputStyle = 'outlined';

  ripple = false;

  constructor(private primengConfig: PrimeNGConfig) {
  }

  ngOnInit() {
    this.primengConfig.ripple = true;
    this.ripple = true;
  }
}
