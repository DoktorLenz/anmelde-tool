import { OidcUserData } from './oidc-user-data';
import { Role } from './role.enum';

export interface UserData extends OidcUserData {
  authorities?: Role[]
}
