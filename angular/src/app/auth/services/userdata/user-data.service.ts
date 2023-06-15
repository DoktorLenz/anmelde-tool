import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EventTypes, OidcClientNotification, PublicEventsService } from 'angular-auth-oidc-client';
import { Role } from './models/role.enum';
import { Observable, ReplaySubject, filter } from 'rxjs';
import { UserData } from './models/user-data';
import { OidcUserData } from './models/oidc-user-data';

@Injectable({
  providedIn: 'root',
})
export class UserDataService {

  private _userDataSubject: ReplaySubject<UserData> = new ReplaySubject(1);

  public userData$: Observable<UserData> = this._userDataSubject.asObservable();

  constructor(
    private readonly http: HttpClient,
    private readonly eventService: PublicEventsService,
  ) {
    this.eventService
      .registerForEvents()
      .pipe(
        filter((notification) => notification.type === EventTypes.UserDataChanged),
      )
      .subscribe({
        next: (notification: OidcClientNotification<OidcUserData>) => {
          this.http.get<Role[]>('/api/v1/auth/user-roles').subscribe({
            next: (roles: Role[]) => {
              this._userDataSubject.next({ ...notification.value, authorities: roles });
            },
          });
        },
      });
  }
}
