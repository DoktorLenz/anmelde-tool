import { createFeature, createReducer, on } from '@ngrx/store';
import { NamiMember } from '../models/nami-member';
import { User } from '../models/user';
import * as UserManagementActions from './user-management.actions';

export const userManagementFeatureKey = 'usermanagement';

export interface UserManagementState {
  namiMembers: NamiMember[];
  loadingNamiMembers: boolean;
  users: User[];
  loadingUsers: boolean;
}

const initialState: UserManagementState = {
  namiMembers: [],
  loadingNamiMembers: false,
  users: [],
  loadingUsers: false,
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
    ),
    on(
      UserManagementActions.loadUsersInititate,
      (state): UserManagementState => ({
        ...state,
        loadingUsers: true,
      })
    ),
    on(
      UserManagementActions.loadUsersSuccess,
      (state, action): UserManagementState => ({
        ...state,
        users: action.users,
        loadingUsers: false,
      })
    ),
    on(
      UserManagementActions.updateNamiMemberInitiate,
      (state, action): UserManagementState => ({
        ...state,
        namiMembers: state.namiMembers.map(namiMember => {
          if (namiMember.memberId === action.namiMember.memberId) {
            return action.namiMember;
          }
          return namiMember;
        }),
      })
    )
  ),
});

export const { name, reducer, selectNamiMembers, selectLoadingNamiMembers } =
  userManagementFeature;
