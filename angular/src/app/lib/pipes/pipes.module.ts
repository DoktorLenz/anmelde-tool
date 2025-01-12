import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ErrorMessagePipe } from './error-tooltip/error-message.pipe';
import { RankColorClassPipe } from './rank-color-class/rank-color-class.pipe';

@NgModule({
  declarations: [ErrorMessagePipe, RankColorClassPipe],
  imports: [CommonModule],
  exports: [ErrorMessagePipe, RankColorClassPipe],
})
export class PipesModule {}
