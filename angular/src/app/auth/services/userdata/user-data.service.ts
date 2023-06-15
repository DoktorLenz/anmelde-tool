import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EventTypes, OidcClientNotification, PublicEventsService } from 'angular-auth-oidc-client';
import { Observable, ReplaySubject, filter } from 'rxjs';
import { OidcUserData } from '../../models/oidc-user-data';
import { UserData } from '../../models/user-data';
import { Role } from '../../models/role.enum';

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
        // filter((notification) => notification.type === EventTypes.NewAuthenticationResult),
      )
      .subscribe((x) => console.warn(x));

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
