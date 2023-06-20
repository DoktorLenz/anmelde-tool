import { CanActivateFn, Router } from '@angular/router';
import { Role } from '../models/role.enum';
import { UserDataService } from '../services/userdata/user-data.service';
import { inject } from '@angular/core';
import { map } from 'rxjs';

export function hasRoleGuard(requiredRole: Role): CanActivateFn {
  return () => {
    const userDataService: UserDataService = inject(UserDataService);
    const router: Router = inject(Router);

    return userDataService.hasRole(requiredRole)
      .pipe(
        map((hasRole: boolean) => {
          if (hasRole) {
            return true;
          } else {

            const history = router.config;
            const previousUrl = history[history.length - 2].path;
            console.warn(previousUrl);
            return router.createUrlTree([previousUrl]);
          }
        }),
      );
  };
}
