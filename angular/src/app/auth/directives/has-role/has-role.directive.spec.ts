import { MockBuilder, MockRender } from 'ng-mocks';
import { HasRoleDirective } from './has-role.directive';
import { Role } from '../../models/role.enum';
import { UserDataService } from '../../services/userdata/user-data.service';
import { BehaviorSubject, EMPTY, of } from 'rxjs';
import { UserData } from '../../models/user-data';
import { TestBed } from '@angular/core/testing';

describe('User does not have required role', () => {
  beforeEach(() =>
    MockBuilder(HasRoleDirective)
      .mock(UserDataService, {
        userData$: EMPTY,
        hasRole: jasmine.createSpy().and.returnValue(of(false)),
      }),
  );

  it('hides an element', () => {
    const view = MockRender(
      `
      <div *hasRole="requiredRole">
        content
      </div>
      `,
      {
        requiredRole: Role.ADMIN,
      },
    );

    expect(view.nativeElement.innerText).not.toContain('content');
  });
});

describe('User does have required role', () => {
  beforeEach(() =>
    MockBuilder(HasRoleDirective)
      .mock(UserDataService, {
        userData$: EMPTY,
        hasRole: jasmine.createSpy().and.returnValue(of(true)),
      }),
  );

  it('shows an element', () => {
    const view = MockRender(
      `
      <div *hasRole="requiredRole">
        content
      </div>
      `,
      {
        requiredRole: Role.ADMIN,
      },
    );

    expect(view.nativeElement.innerText).toContain('content');
  });
});


describe('User does not have required role initially but then receives this role', () => {
  const userWithoutRoles: UserData = {
    authorities: [],
  };

  const userWithRoles: UserData = {
    authorities: [Role.ADMIN],
  };

  const userDataSubject = new BehaviorSubject(userWithoutRoles);

  beforeEach(() =>
    MockBuilder(HasRoleDirective)
      .mock(UserDataService, {
        userData$: userDataSubject.asObservable(),
        hasRole: jasmine.createSpy().and.returnValue(of(false)),
      }),
  );

  it('should hide element first and the show it once the roles are sufficient', () => {
    const view = MockRender(
      `
      <div *hasRole="requiredRole">
        content
      </div>
      `,
      {
        requiredRole: Role.ADMIN,
      },
    );

    const userDataService = TestBed.inject(UserDataService);

    expect(view.nativeElement.innerText).not.toContain('content');

    spyOn(userDataService, 'hasRole').and.returnValue(of(true));
    userDataSubject.next(userWithRoles);

    expect(view.nativeElement.innerText).toContain('content');
  });
});
