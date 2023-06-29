import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BreakpointDirective } from './breakpoint/breakpoint.directive';



@NgModule({
  declarations: [BreakpointDirective],
  imports: [
    CommonModule,
  ],
  exports: [BreakpointDirective],
})
export class LayoutDirectivesModule { }
