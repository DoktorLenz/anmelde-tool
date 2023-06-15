import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserManagementRoutingModule } from './user-management-routing.module';
import { NamiMembersComponent } from './nami-members/nami-members.component';
import { OverviewComponent } from './overview/overview.component';


@NgModule({
  declarations: [
    NamiMembersComponent,
    OverviewComponent,
  ],
  imports: [
    CommonModule,
    UserManagementRoutingModule,
  ],
})
export class UserManagementModule { }
