import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignedOutComponent } from './signed-out.component';

const routes: Routes = [{ path: '', component: SignedOutComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SignedOutRoutingModule { }
