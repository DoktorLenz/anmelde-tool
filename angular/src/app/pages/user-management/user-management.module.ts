import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { DividerModule } from 'primeng/divider';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { MultiSelectModule } from 'primeng/multiselect';
import { PasswordModule } from 'primeng/password';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { PipesModule } from 'src/app/lib/pipes/pipes.module';
import { NamiMembersComponent } from './nami-members/nami-members.component';
import { OverviewComponent } from './overview/overview.component';
import * as fromUserManagement from './reducers';
import { UserManagementRoutingModule } from './user-management-routing.module';
import * as userManagementEffects from './user-management.effects';
@NgModule({
  declarations: [NamiMembersComponent, OverviewComponent],
  imports: [
    StoreModule.forFeature(fromUserManagement.userManagementFeature),
    EffectsModule.forFeature(userManagementEffects),
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
    DividerModule,
    MultiSelectModule,
    DropdownModule,
  ],
})
export class UserManagementModule {}
