import { CanActivateFn } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = () => {
  const authenticationService = inject(AuthenticationService);
  if (!authenticationService.authenticated) {
    return authenticationService.authenticate();
  }
  return authenticationService.authenticated;
};
