import { createAction, props } from '@ngrx/store';
import { NamiMember } from './nami-members/model/nami-member';

export const loadNamiMembersInitiate = createAction(
  '[UserManagement] LoadNamiMembers initiate'
);
export const loadNamiMembersSuccess = createAction(
  '[UserManagement] LoadNamiMembers success',
  props<{ namiMembers: NamiMember[] }>()
);
