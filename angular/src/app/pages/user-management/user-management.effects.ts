import { inject } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { NamiMembersService } from 'src/app/user-management/nami-members/services/nami-members.service';

import { exhaustMap, map } from 'rxjs';
import * as UserManagementActions from './user-management.actions';

export const loadNamiMembers = createEffect(
  (
    actions$ = inject(Actions),
    namiMembersService = inject(NamiMembersService)
  ) => {
    return actions$.pipe(
      ofType(UserManagementActions.loadNamiMembersInitiate),
      exhaustMap(() =>
        namiMembersService
          .getNamiMembers()
          .pipe(
            map(namiMembers =>
              UserManagementActions.loadNamiMembersSuccess({ namiMembers })
            )
          )
      )
    );
  },
  { functional: true }
);
