import { Component } from '@angular/core';
import { AuthenticationService } from 'src/app/auth/services/authentication.service';

@Component({
  templateUrl: './signed-out.component.html',
})
export class SignedOutComponent {
  constructor(private readonly authenticationService: AuthenticationService) {}

  protected triggerAuthentication(): void {
    this.authenticationService.authenticate();
  }
}