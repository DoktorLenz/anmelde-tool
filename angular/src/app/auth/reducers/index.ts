import { createFeature, createReducer, createSelector, on } from '@ngrx/store';
import * as AuthActions from '../auth.actions';
import { Role } from '../models/role.enum';

export const authFeatureKey = 'auth';

export interface AuthState {
  roles: Role[];
}
const initialState: AuthState = {
  roles: [],
};

// export const reducers: ActionReducerMap<State> = {};

// export const metaReducers: MetaReducer<State>[] = isDevMode() ? [] : [];

export const authFeature = createFeature({
  name: authFeatureKey,
  reducer: createReducer(
    initialState,
    on(
      AuthActions.setRoles,
      (state, action): AuthState => ({ ...state, roles: action.roles })
    )
  ),
  extraSelectors: ({ selectRoles }) => ({
    isAdmin: createSelector(selectRoles, (roles: Role[]) =>
      roles.includes(Role.ADMIN)
    ),
    isVerified: createSelector(selectRoles, (roles: Role[]) =>
      roles.includes(Role.VERIFIED)
    ),
  }),
});

export const { name, reducer, selectRoles } = authFeature;
