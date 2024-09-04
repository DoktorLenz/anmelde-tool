import { createAction, props } from '@ngrx/store';
import { NamiMember } from '../models/nami-member';

export const loadNamiMembersInitiate = createAction(
  '[UserManagement] LoadNamiMembers initiate'
);

export const loadNamiMembersSuccess = createAction(
  '[UserManagement] LoadNamiMembers success',
  props<{ namiMembers: NamiMember[] }>()
);

export const namiImportInitiate = createAction(
  '[UserManagement] NamiImport initiate',
  props<{ username: string; password: string; groupingId: string }>()
);

export const namiImportSuccess = createAction(
  '[UserManagement] NamiImport success'
);
