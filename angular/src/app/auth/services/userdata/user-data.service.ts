import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthenticatedResult, OidcSecurityService, UserDataResult } from 'angular-auth-oidc-client';
import { Role } from './models/role.enum';
import { Observable, Subject, filter, map, merge, tap, zip, zipAll } from 'rxjs';
import { OidcUserData } from './models/oidc-user-data';
import { UserData } from './models/user-data';

@Injectable({
  providedIn: 'root',
})
export class UserDataService {

  private _roles: Role[] = [];

  private set roles(roles: Role[]) {
    this._roles = roles;
    this.updateUserData();
  }

  private _oidcUserData: OidcUserData = {};

  private set oidcUserData(oidcUserData: OidcUserData) {
    this._oidcUserData = oidcUserData;
    this.updateUserData();
  }

  private _userDataSubject: Subject<UserData> = new Subject();

  public userData$: Observable<UserData> = this._userDataSubject.asObservable();

  private updateUserData():void {
    console.warn('UpdateUserData');
    this._userDataSubject.next({ ...this._oidcUserData, authorities: this._roles });
  }

  constructor(private readonly securityService: OidcSecurityService, private readonly http: HttpClient) {
    this.securityService.getAccessToken().subscribe({
      next: () => {
        zip(this.securityService.getUserData(), this.http.get<Role[]>('/api/v1/auth/user-roles'))
          .pipe(
            map((zip_content) => {
              return { ...zip_content[0], authorities: zip_content[1] } as UserData;
            }),
          )
          .subscribe({
            next: (userData: UserData) => {
              this._userDataSubject.next(userData);
            },
          });
      },
    });
  }
}
