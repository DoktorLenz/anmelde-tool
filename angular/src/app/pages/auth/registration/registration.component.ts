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

  protected get firstname(): AbstractControl {
    const control = this.registerForm.get('firstname');
    if (control) {
      return control;
    } else {
      throw new Error('No control for firstname');
    }
  }

  protected get lastname(): AbstractControl {
    const control = this.registerForm.get('lastname');
    if (control) {
      return control;
    } else {
      throw new Error('No control for lastname');
    }
  }

  protected get email(): AbstractControl {
    const control = this.registerForm.get('email');
    if (control) {
      return control;
    } else {
      throw new Error('No control for email');
    }
  }

  protected registerForm = new FormGroup({
    firstname: new FormControl('', Validators.required),
    lastname: new FormControl('', Validators.required),
    email: new FormControl('', [Validators.required, Validators.email]),
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
        next: () => {
          this.loading = false;
          this.messageService.clear();
          this.router.navigateByUrl(`/${BaseRoute.AUTH}/${AuthRoute.REGISTRATION_SENT}`);
        },
        error: () => {
          this.loading = false;
          this.messageService.add({
            severity: 'error',
            summary: 'Upps!',
            detail: 'Da ist etwas schief gelaufen. Bitte überprüfe deine Eingaben und versuche es erneut.',
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
