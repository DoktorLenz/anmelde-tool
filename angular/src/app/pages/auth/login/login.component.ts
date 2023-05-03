import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpAuthService } from 'src/app/core/http/auth/http-auth.service';

@Component({
  templateUrl: './login.component.html',
})
export class LoginComponent {
  private _email: AbstractControl = new FormControl('', {
    validators: [Validators.required, Validators.email],
    updateOn: 'blur',
  });

  protected get email(): AbstractControl {
    return this._email;
  }

  private _password: AbstractControl = new FormControl('');

  protected get password(): AbstractControl {
    return this._password;
  }

  protected loginForm =  new FormGroup({
    email: this.email,
    password: this.password,
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
