import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { HttpAuthService } from 'src/app/core/http/auth/http-auth.service';

@Component({
  templateUrl: './login.component.html',
})
export class LoginComponent {
  protected get email(): AbstractControl {
    const control = this.loginForm.get('email');
    if (control) {
      return control;
    } else {
      throw new Error('No control for email');
    }
  }

  protected get password(): AbstractControl {
    const control = this.loginForm.get('password');
    if (control) {
      return control;
    } else {
      throw new Error('No control for password');
    }
  }

  protected loginForm =  new FormGroup({
    email: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
  });

  protected loading = false;

  protected onSubmit(): void {
    const loadingIndicatorTimeout = setTimeout(() => {
      this.loading = true;
    }, 500);
    console.log('AUTH');
    this.httpAuthService.login(this.email.getRawValue().toString(), this.password.getRawValue().toString())
      .subscribe({
        next: () => {
          this.onLoginFinish(loadingIndicatorTimeout);
          this.messageService.clear();
          this.router.navigateByUrl('');
        },
        error: () => {
          this.onLoginFinish(loadingIndicatorTimeout);
          this.messageService.add({
            severity: 'error',
            summary: 'Upps!',
            detail: 'Da ist etwas schief gelaufen. Bitte überprüfe deine Eingaben und versuche es erneut.',
            life: 4000,
          });
        },
      });
  }

  private onLoginFinish(timeout: NodeJS.Timeout): void {
    clearTimeout(timeout);
    this.loading = false;
  }

  constructor(
    private readonly httpAuthService: HttpAuthService,
    private readonly router: Router,
    private readonly messageService: MessageService) {

  }
}
