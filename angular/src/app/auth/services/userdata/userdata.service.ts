import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { Role } from './models/role.enum';

@Injectable({
  providedIn: 'root',
})
export class UserdataService {

  private _roles: Role[] = [];

  public get roles(): Role[] {
    return this._roles;
  }

  constructor(private readonly securityService: OidcSecurityService, private readonly http: HttpClient) {
    this.securityService.checkSessionChanged$.subscribe(() => this.updateUserdata());
  }

  public updateUserdata(): void {
    this.http.get<Role[]>('/api/v1/auth/user-roles').subscribe({
      next: (roles) => {
        this._roles = roles;
      },
    });
  }
}
