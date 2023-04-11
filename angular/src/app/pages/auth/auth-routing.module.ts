import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './auth.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './registration/registration.component';
import { AuthRoute } from 'src/app/lib/routes/auth-route';
import { RegistrationSentComponent } from './registration-sent/registration-sent.component';

const routes: Routes = [
  {
    path: '',
    component: AuthComponent,
    children: [
      {
        path: AuthRoute.LOGIN,
        component: LoginComponent,
      },
      {
        path: AuthRoute.REGISTER,
        component: RegisterComponent,
      },
      {
        path: AuthRoute.REGISTRATION_SENT,
        component: RegistrationSentComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AuthRoutingModule { }
