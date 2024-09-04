import { inject } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { NamiMembersService } from '../services/nami-members.service';

import { exhaustMap, map } from 'rxjs';
import * as UserManagementActions from './user-management.actions';

export const loadNamiMembers = createEffect(
  (
    actions$ = inject(Actions),
    namiMembersService = inject(NamiMembersService)
  ) => {
    return actions$.pipe(
      ofType(
        UserManagementActions.loadNamiMembersInitiate,
        UserManagementActions.namiImportSuccess
      ),
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

export const importNamiMembers = createEffect(
  (
    actions$ = inject(Actions),
    namiMembersService = inject(NamiMembersService)
  ) => {
    return actions$.pipe(
      ofType(UserManagementActions.namiImportInitiate),
      exhaustMap(action =>
        namiMembersService
          .fetchNamiMembers({
            username: action.username,
            password: action.password,
            groupingId: action.groupingId,
          })
          .pipe(map(() => UserManagementActions.namiImportSuccess()))
      )
    );
  },
  { functional: true }
);
