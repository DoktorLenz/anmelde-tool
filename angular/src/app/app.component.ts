import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { jwtDecode, JwtPayload } from 'jwt-decode';
import { setRoles } from './auth/auth.actions';
import { Role } from './auth/models/role.enum';

@Component({
    selector: 'sv-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss'],
    standalone: false
})
export class AppComponent {
  title = 'scoutventure-spa';

  constructor(
    private readonly securityService: OidcSecurityService,
    private readonly store: Store
  ) {
    this.securityService.getAccessToken().subscribe({
      next: token => {
        const roles = this.getRolesFromJwt(token).map(role => role as Role);
        this.store.dispatch(setRoles({ roles }));
      },
    });
  }

  private getRolesFromJwt(token: string): string[] {
    if (token === null || token.length === 0) return [];
    interface JwtPayloadExtension extends JwtPayload {
      realm_access?: {
        roles?: string[];
      };
    }
    return jwtDecode<JwtPayloadExtension>(token).realm_access?.roles ?? [];
  }
}
