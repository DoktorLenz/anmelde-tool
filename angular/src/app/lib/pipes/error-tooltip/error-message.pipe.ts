import { Pipe, PipeTransform } from '@angular/core';
import { ValidationErrors } from '@angular/forms';

@Pipe({
  name: 'errorMessage',
})
export class ErrorMessagePipe implements PipeTransform {
  transform(errors: ValidationErrors | null): string {
    const errorMessages = Object.entries(errors ?? []).map(([property]) =>
      this.getErrorMessage(property)
    );

    return errorMessages.join(', ');
  }

  getErrorMessage(error: string): string {
    switch (error) {
      case 'required':
        return 'Erforderlich';
      case 'email':
        return 'Ungültige E-Mail Adresse';
      case 'invalid-login':
        return 'Falsche E-Mail Adresse oder falsches Passwort';
      default:
        return `unknown error "${error}"`;
    }
  }
}
