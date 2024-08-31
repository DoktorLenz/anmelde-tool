import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EventTypes, OidcClientNotification, OidcSecurityService, PublicEventsService } from 'angular-auth-oidc-client';
import { jwtDecode, JwtPayload } from "jwt-decode";
import { BehaviorSubject, filter, map, Observable } from 'rxjs';
import { LocalStorageService } from 'src/app/storage/local-storage/local-storage.service';
import { OidcUserData } from '../../models/oidc-user-data';
import { Role } from '../../models/role.enum';
import { UserData } from '../../models/user-data';

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
    private readonly authService: OidcSecurityService
  ) {
    // There will only be an event on login
    this.eventService
      .registerForEvents()
      .pipe(
        filter((notification) => notification.type === EventTypes.UserDataChanged),
      )
      .subscribe({
        next: (notification: OidcClientNotification<OidcUserData>) => {
          console.log(notification);

          const accessToken = this.authService.getAccessToken();

          accessToken.subscribe((token) => {
            const roles = this.getRolesFromJwt(token).map(() => Role.ADMIN);
            this.updateUserData({...notification, authorities: roles})
          });

        },
      });
  }

  private getRolesFromJwt(token: string): string[] {
    interface JwtPayloadExtension extends JwtPayload {
      realm_access?: {
        roles?: string[]
      }
    }
    return jwtDecode<JwtPayloadExtension>(token).realm_access?.roles ?? [];
  }

  public hasRole(role: Role): Observable<boolean> {
    return this.userData$.pipe(
      map((userData) => {
        return userData.authorities?.find((authority) => authority === role) ? true : false;
      }),
    );
  }
}
