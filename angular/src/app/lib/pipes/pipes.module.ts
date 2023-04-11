import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ErrorTooltipPipe } from './error-tooltip/error-tooltip.pipe';



@NgModule({
  declarations: [
    ErrorTooltipPipe,
  ],
  imports: [
    CommonModule,
  ],
  exports: [
    ErrorTooltipPipe,
  ],
})
export class PipesModule { }
