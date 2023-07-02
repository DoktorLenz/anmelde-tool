import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserManagementRoutingModule } from './user-management-routing.module';
import { NamiMembersComponent } from './nami-members/nami-members.component';
import { OverviewComponent } from './overview/overview.component';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { PasswordModule } from 'primeng/password';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { TagModule } from 'primeng/tag';
import { PipesModule } from 'src/app/lib/pipes/pipes.module';

@NgModule({
  declarations: [
    NamiMembersComponent,
    OverviewComponent,
  ],
  imports: [
    CommonModule,
    UserManagementRoutingModule,
    TableModule,
    ButtonModule,
    DialogModule,
    InputTextModule,
    PasswordModule,
    FormsModule,
    TagModule,
    PipesModule,
  ],
})
export class UserManagementModule { }
