import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authGuard } from './auth/guards/auth.guard';
import { BaseRoute } from './lib/routes/base-route';
import { AuthRoute } from './lib/routes/auth-route';

const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./pages/main/main.module').then(m => m.MainModule),
    canActivate: [authGuard],
  },
  {
    path: `${BaseRoute.AUTH}/${AuthRoute.CALLBACK}`,
    redirectTo: '/',
  },
  {
    path: BaseRoute.SIGNED_OUT,
    loadChildren: () => import('./pages/signed-out/signed-out.module').then(m => m.SignedOutModule),
  },
  {
    path: '**',
    redirectTo: '/',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
