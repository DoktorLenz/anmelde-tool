import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SignedOutRoutingModule } from './signed-out-routing.module';
import { SignedOutComponent } from './signed-out.component';
import { ButtonModule } from 'primeng/button';


@NgModule({
  declarations: [
    SignedOutComponent,
  ],
  imports: [
    CommonModule,
    SignedOutRoutingModule,
    ButtonModule,
  ],
})
export class SignedOutModule { }
