import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { sessionAuthenticatedGuard, sessionNotAuthenticatedGuard } from './core/guards/session.guard';

const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () => import('./pages/auth/auth.module').then(m => m.AuthModule),
    canActivate: [sessionNotAuthenticatedGuard],
  },
  {
    path: '',
    loadChildren: () => import('./pages/main/main.module').then(m => m.MainModule),
    canActivate: [sessionAuthenticatedGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
