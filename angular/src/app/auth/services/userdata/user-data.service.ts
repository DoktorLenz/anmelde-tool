import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { Role } from './models/role.enum';
import { Observable, Subject, map, zip } from 'rxjs';
import { UserData } from './models/user-data';

@Injectable({
  providedIn: 'root',
})
export class UserDataService {

  private _userDataSubject: Subject<UserData> = new Subject();

  public userData$: Observable<UserData> = this._userDataSubject.asObservable();

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
