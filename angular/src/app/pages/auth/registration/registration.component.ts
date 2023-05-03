import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { HttpAuthService } from 'src/app/core/http/auth/http-auth.service';
import { AuthRoute } from 'src/app/lib/routes/auth-route';
import { BaseRoute } from 'src/app/lib/routes/base-route';

@Component({
  templateUrl: './registration.component.html',
})
export class RegistrationComponent {

  private _firstname = new FormControl('', {
    validators: Validators.required,
    updateOn: 'change',
  });

  protected get firstname(): AbstractControl {
    return this._firstname;
  }

  private _lastname: AbstractControl = new FormControl('', {
    validators: Validators.required,
    updateOn: 'change',
  });

  protected get lastname(): AbstractControl {
    return this._lastname;
  }

  private _email: AbstractControl = new FormControl('', {
    validators: [Validators.required, Validators.email],
    updateOn: 'change',
  });

  protected get email(): AbstractControl {
    return this._email;
  }

  protected registerForm = new FormGroup({
    firstname: this.firstname,
    lastname: this.lastname,
    email: this.email,
  });

  protected loading = false;

  protected onSubmit(): void {
    this.loading = true;
    this.httpAuthService.register({
      firstname: this.firstname.getRawValue().toString(),
      lastname: this.lastname.getRawValue().toString(),
      email: this.email.getRawValue().toString(),
    })
      .subscribe({
        next: async () => {
          this.loading = false;
          await this.router.navigateByUrl(`/${BaseRoute.AUTH}/${AuthRoute.REGISTRATION_SENT}`);
        },
        error: () => {
          this.loading = false;
          this.messageService.add({
            severity: 'error',
            summary: 'Upps!',
            detail: 'Da ist etwas schief gelaufen. Bitte versuche es später erneut.',
            life: 4000,
          });
        },
      });
  }

  constructor(
    private readonly httpAuthService: HttpAuthService,
    private readonly router: Router,
    private readonly messageService: MessageService) {

  }
}
