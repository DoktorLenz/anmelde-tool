import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EventTypes, OidcClientNotification, PublicEventsService } from 'angular-auth-oidc-client';
import { BehaviorSubject, Observable, filter } from 'rxjs';
import { OidcUserData } from '../../models/oidc-user-data';
import { UserData } from '../../models/user-data';
import { Role } from '../../models/role.enum';
import { LocalStorageService } from 'src/app/storage/local-storage/local-storage.service';

@Injectable({
  providedIn: 'root',
})
export class UserDataService {

  private _userDataSubject: BehaviorSubject<UserData> =
    new BehaviorSubject(this.localStorageService.userData);

  public userData$: Observable<UserData> = this._userDataSubject.asObservable();

  private updateUserData(userData: UserData): void {
    this.localStorageService.userData = userData;
    this._userDataSubject.next(userData);
  }

  constructor(
    private readonly http: HttpClient,
    private readonly eventService: PublicEventsService,
    private readonly localStorageService: LocalStorageService,
  ) {
    // There will only be an event on login
    this.eventService
      .registerForEvents()
      .pipe(
        filter((notification) => notification.type === EventTypes.UserDataChanged),
      )
      .subscribe({
        next: (notification: OidcClientNotification<OidcUserData>) => {
          this.http.get<Role[]>('/api/v1/auth/user-roles').subscribe({
            next: (roles: Role[]) => {
              this.updateUserData({ ...notification.value, authorities: roles });
            },
          });
        },
      });

  }
}
