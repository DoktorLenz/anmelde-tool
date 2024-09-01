import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HasRoleDirective } from './has-role/has-role.directive';

@NgModule({
  declarations: [HasRoleDirective],
  imports: [CommonModule],
  exports: [HasRoleDirective],
})
export class AuthDirectivesModule {}
