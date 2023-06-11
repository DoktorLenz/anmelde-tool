import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OidcSecurityService } from 'angular-auth-oidc-client';

@Injectable({
  providedIn: 'root',
})
export class UserdataService {

  private _roles: string[] = [];

  public get roles(): string[] {
    return this._roles;
  }

  constructor(private readonly securityService: OidcSecurityService, private readonly http: HttpClient) {
    this.securityService.checkSessionChanged$.subscribe(() => this.updateUserdata());
  }


  public updateUserdata(): void {
    // this.securityService..subscribe((a) => console.warn(a));
    this.http.get<string[]>('/api/v1/auth/user-roles').subscribe({
      next: (a) => {
        this._roles = a;
        console.warn(a);
      },
    });
  }

}
