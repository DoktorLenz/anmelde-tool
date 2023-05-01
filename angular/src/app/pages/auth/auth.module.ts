import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { AuthComponent } from './auth.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { ReactiveFormsModule } from '@angular/forms';
import { TooltipModule } from 'primeng/tooltip';
import { PipesModule } from '../../lib/pipes/pipes.module';
import { ChipModule } from 'primeng/chip';
import { RegistrationSentComponent } from './registration-sent/registration-sent.component';


@NgModule({
  declarations: [
    AuthComponent,
    LoginComponent,
    RegistrationComponent,
    RegistrationSentComponent,
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    CardModule,
    InputTextModule,
    PasswordModule,
    ButtonModule,
    ReactiveFormsModule,
    TooltipModule,
    PipesModule,
    ChipModule,
  ],
})
export class AuthModule { }
