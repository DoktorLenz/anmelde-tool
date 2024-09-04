import { createFeature, createReducer, on } from '@ngrx/store';
import { NamiMember } from '../models/nami-member';
import * as UserManagementActions from './user-management.actions';

export const userManagementFeatureKey = 'usermanagement';

export interface UserManagementState {
  namiMembers: NamiMember[];
  loadingNamiMembers: boolean;
}

const initialState: UserManagementState = {
  namiMembers: [],
  loadingNamiMembers: false,
};

export const userManagementFeature = createFeature({
  name: userManagementFeatureKey,
  reducer: createReducer(
    initialState,
    on(
      UserManagementActions.loadNamiMembersInitiate,
      UserManagementActions.namiImportInitiate,
      (state): UserManagementState => ({
        ...state,
        loadingNamiMembers: true,
      })
    ),
    on(
      UserManagementActions.loadNamiMembersSuccess,
      (state, action): UserManagementState => ({
        ...state,
        namiMembers: action.namiMembers,
        loadingNamiMembers: false,
      })
    )
  ),
});

export const { name, reducer, selectNamiMembers, selectLoadingNamiMembers } =
  userManagementFeature;
