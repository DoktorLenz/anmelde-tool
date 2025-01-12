import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import { ButtonModule } from 'primeng/button';
import { ChipModule } from 'primeng/chip';
import { DialogModule } from 'primeng/dialog';
import { DividerModule } from 'primeng/divider';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { MultiSelectModule } from 'primeng/multiselect';
import { PasswordModule } from 'primeng/password';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { PipesModule } from 'src/app/lib/pipes/pipes.module';
import { userManagementFeature } from './ngrx';
import * as UserManagementEffects from './ngrx/user-management.effects';
import { NamiMembersComponent } from './pages/nami-members/nami-members.component';
import { OverviewComponent } from './pages/overview/overview.component';
import { UserManagementRoutingModule } from './user-management-routing.module';
@NgModule({
  declarations: [NamiMembersComponent, OverviewComponent],
  imports: [
    StoreModule.forFeature(userManagementFeature),
    EffectsModule.forFeature(UserManagementEffects),
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
    ChipModule,
  ],
})
export class UserManagementModule {}
