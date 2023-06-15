import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BaseRoute } from './lib/routes/base-route.enum';
import { AuthRoute } from './lib/routes/auth-route.enum';
import { CallbackComponent } from './pages/callback/callback.component';
import { AutoLoginPartialRoutesGuard } from 'angular-auth-oidc-client';

const routes: Routes = [
  {
    path: '',
    redirectTo: BaseRoute.HOME,
    pathMatch: 'full',
  },
  {
    path: `${BaseRoute.AUTH}/${AuthRoute.CALLBACK}`,
    component: CallbackComponent,
  },
  {
    path: BaseRoute.HOME,
    loadChildren: () => import('./pages/main/main.module').then(m => m.MainModule),
    canActivate: [AutoLoginPartialRoutesGuard],
  },
  {
    path: BaseRoute.USER_MANAGEMENT,
    loadChildren: () => import('./pages/user-management/user-management.module')
      .then(m => m.UserManagementModule),
    canActivate: [AutoLoginPartialRoutesGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
