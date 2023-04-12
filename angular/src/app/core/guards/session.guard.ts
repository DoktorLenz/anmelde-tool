import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { SessionService } from '../services/session/session.service';
import { BaseRoute } from 'src/app/lib/routes/base-route';

export const sessionAuthenticatedGuard: CanActivateFn = () => {
  const router = inject(Router);
  const sessionService = inject(SessionService);

  if (sessionService.sessionAuthenticated) {
    return true;
  } else {
    return router.navigate([`/${BaseRoute.AUTH}`]);
  }
};

export const sessionNotAuthenticatedGuard: CanActivateFn = () => {
  const router = inject(Router);
  const sessionService = inject(SessionService);

  if ( !sessionService.sessionAuthenticated) {
    return true;
  } else {
    return router.navigate(['']);
  }
};
