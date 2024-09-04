import { createAction, props } from '@ngrx/store';
import { NamiMember } from '../models/nami-member';

export const loadNamiMembersInitiate = createAction(
  '[UserManagement] LoadNamiMembers initiate'
);
export const loadNamiMembersSuccess = createAction(
  '[UserManagement] LoadNamiMembers success',
  props<{ namiMembers: NamiMember[] }>()
);
