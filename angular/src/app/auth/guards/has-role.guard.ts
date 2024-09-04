import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { select, Store } from '@ngrx/store';
import { map } from 'rxjs';
import { Role } from '../models/role.enum';
import { authFeature } from '../reducers';

export function hasRoleGuard(requiredRole: Role): CanActivateFn {
  return () => {
    const router: Router = inject(Router);

    const store: Store = inject(Store);

    return store.pipe(
      select(authFeature.selectRoles),
      map(roles => {
        if (roles.includes(requiredRole)) {
          return true;
        } else {
          const history = router.config;
          const previousUrl = history[history.length - 2].path;
          return router.createUrlTree([previousUrl]);
        }
      })
    );
  };
}
