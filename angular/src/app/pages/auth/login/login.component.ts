import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
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
    email: new FormControl('', {
      validators: [Validators.required, Validators.email],
      updateOn: 'blur',
    }),
    password: new FormControl(''),
  });

  protected loading = false;

  protected onSubmit(): void {
    if (this.loginForm.invalid || !this.email.getRawValue().toString()) {
      return;
    }
    this.loading = true;
    this.httpAuthService.login(this.email.getRawValue().toString(), this.password.getRawValue().toString())
      .subscribe({
        next: () => {
          this.loading = false;
          this.router.navigateByUrl('');
        },
        error: () => {
          this.loading = false;
          this.loginForm.setErrors({ 'invalid-login': true });
        },
      });
  }

  constructor(
    private readonly httpAuthService: HttpAuthService,
    private readonly router: Router) {

  }
}
