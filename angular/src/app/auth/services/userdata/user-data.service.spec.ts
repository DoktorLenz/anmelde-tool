import { MockBuilder, ngMocks } from 'ng-mocks';
import { UserDataService } from './user-data.service';
import {} from '@angular/common/http';
import { HttpTestingController } from '@angular/common/http/testing';
import { AppModule } from 'src/app/app.module';
import { EventTypes, OidcClientNotification, PublicEventsService } from 'angular-auth-oidc-client';
import { EMPTY, Subject } from 'rxjs';
import { OidcUserData } from '../../models/oidc-user-data';
import { Role } from '../../models/role.enum';
import { UserData } from '../../models/user-data';
import { LocalStorageService } from 'src/app/storage/local-storage/local-storage.service';
import { fakeAsync, flushMicrotasks } from '@angular/core/testing';

describe('On change of userdata', () => {

  const oidcUserData: OidcUserData = {
    family_name: 'family_name',
    given_name: 'given_name',
    name: 'name',
    prefered_username: 'prefered_username',
    sub: '0123456789',
  };

  const notification: OidcClientNotification<OidcUserData> = {
    type: EventTypes.UserDataChanged,
    value: oidcUserData,
  };

  const eventsSubject: Subject<OidcClientNotification<OidcUserData>> = new Subject();

  const userRoles: Role[] = [Role.VERIFIED, Role.ADMIN];

  beforeEach(() =>
    MockBuilder(UserDataService, AppModule)
      .replace(HttpClientModule, HttpClientTestingModule)
      .mock(PublicEventsService, {
        registerForEvents: jasmine.createSpy().and.returnValue(eventsSubject.asObservable()),
      })
      .mock(LocalStorageService),
  );

  it('should fetch the users roles', () => {
    ngMocks.get(UserDataService);
    const httpMock = ngMocks.get(HttpTestingController);

    eventsSubject.next(notification);

    const req = httpMock.expectOne('/api/v1/auth/user-roles');

    expect(req.request.method).toEqual('GET');
    req.flush(userRoles);

    httpMock.verify();
  });

  it('should issue an update of the userData observable', () => {
    let subscriptionTriggered = false;
    const userDataService = ngMocks.get(UserDataService);
    const httpMock = ngMocks.get(HttpTestingController);

    eventsSubject.next(notification);

    const req = httpMock.expectOne('/api/v1/auth/user-roles');

    expect(req.request.method).toEqual('GET');
    req.flush(userRoles);

    httpMock.verify();

    userDataService.userData$.subscribe((userData: UserData) => {
      expect(userData).toEqual({ ...oidcUserData, authorities: userRoles });
      subscriptionTriggered = true;
    });

    expect(subscriptionTriggered).toBeTrue();
  });

  it('should save user data in local storage', () => {
    ngMocks.get(UserDataService);
    const httpMock = ngMocks.get(HttpTestingController);
    const localStorageService = ngMocks.get(LocalStorageService);
    const userDataSpy = ngMocks.stubMember(localStorageService, 'userData', jasmine.createSpy(), 'set');

    eventsSubject.next(notification);

    const req = httpMock.expectOne('/api/v1/auth/user-roles');
    req.flush(userRoles);

    expect(userDataSpy).toHaveBeenCalledWith({ ...oidcUserData, authorities: userRoles });
  });
});

describe('When hasRole is called', () => {

  const userData: UserData = {
    authorities: [Role.VERIFIED],
  };

  let localStorageService: LocalStorageService;

  beforeEach(() =>
    MockBuilder(UserDataService, AppModule)
      .replace(HttpClientModule, HttpClientTestingModule)
      .mock(PublicEventsService, {
        registerForEvents: jasmine.createSpy().and.returnValue(EMPTY),
      })
      .mock(LocalStorageService),
  );

  it('should return observable with value true when user has role', fakeAsync(() => {
    localStorageService = ngMocks.get(LocalStorageService);
    ngMocks.stubMember(localStorageService, 'userData', jasmine.createSpy(), 'get').and.returnValue(userData);
    const userDataService = ngMocks.get(UserDataService);

    userDataService.hasRole(Role.VERIFIED).subscribe((hasRole: boolean) => {
      expect(hasRole).toBeTrue();
    });

    flushMicrotasks();
  }));

  it('should return observable with value false when user has not role', fakeAsync(() => {
    localStorageService = ngMocks.get(LocalStorageService);
    ngMocks.stubMember(localStorageService, 'userData', jasmine.createSpy(), 'get').and.returnValue(userData);
    const userDataService = ngMocks.get(UserDataService);

    userDataService.hasRole(Role.ADMIN).subscribe((hasRole: boolean) => {
      expect(hasRole).toBeFalse();
    });

    flushMicrotasks();
  }));
});
