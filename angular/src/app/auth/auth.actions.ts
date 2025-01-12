import { createAction, props } from '@ngrx/store';
import { Role } from './models/role.enum';

export const setRoles = createAction(
  '[Auth] SetRoles',
  props<{ roles: Role[] }>()
);
