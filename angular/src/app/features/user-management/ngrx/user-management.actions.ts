import { createAction, props } from '@ngrx/store';
import { NamiMember } from '../models/nami-member';
import { User } from '../models/user';

export const loadNamiMembersInitiate = createAction(
  '[UserManagement] LoadNamiMembers initiate'
);

export const loadNamiMembersSuccess = createAction(
  '[UserManagement] LoadNamiMembers success',
  props<{ namiMembers: NamiMember[] }>()
);

export const updateNamiMemberInitiate = createAction(
  '[UserManagement] UpdateNamiMember initiate',
  props<{ namiMember: NamiMember }>()
);

export const updateNamiMemberSuccess = createAction(
  '[UserManagement] UpdateNamiMember success'
);

export const namiImportInitiate = createAction(
  '[UserManagement] NamiImport initiate',
  props<{ username: string; password: string; groupingId: string }>()
);

export const namiImportSuccess = createAction(
  '[UserManagement] NamiImport success'
);

export const loadUsersInititate = createAction(
  '[UserManagement] LoadUsers initiate'
);

export const loadUsersSuccess = createAction(
  '[UserManagement] LoadUsers success',
  props<{ users: User[] }>()
);
