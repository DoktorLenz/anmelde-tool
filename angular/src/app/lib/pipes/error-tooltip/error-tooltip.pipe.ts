import { Pipe, PipeTransform } from '@angular/core';
import { AbstractControl } from '@angular/forms';

@Pipe({
  name: 'errorTooltip',
})
export class ErrorTooltipPipe implements PipeTransform {

  transform(control: AbstractControl): string {
    if (control.errors?.['required']) {
      return 'Erforderlich';
    } else if (control.errors?.['email']) {
      return 'Gib eine gültige E-Mail Adresse ein';
    } else {
      return JSON.stringify(control.errors);
    }
  }

}
