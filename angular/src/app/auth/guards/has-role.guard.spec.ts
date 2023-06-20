import { Router, Routes } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { MockProvider } from 'ng-mocks';
import { hasRoleGuard } from './has-role.guard';
import { Role } from '../models/role.enum';
import { UserDataService } from '../services/userdata/user-data.service';
import { of } from 'rxjs';
import { TestBed, fakeAsync, flushMicrotasks } from '@angular/core/testing';
import { Location } from '@angular/common';
import { Component } from '@angular/core';

@Component({ template: '' })
class PrevComponent {}

@Component({ template: '' })
class MockComponent {}

const routes: Routes = [
  { path: '', redirectTo: 'previousPath', pathMatch: 'full' },
  { path: 'previousPath', component: PrevComponent },
  { path: 'currentPath', component: MockComponent, canActivate: [hasRoleGuard(Role.ADMIN)] },
];

describe('User does not have required role', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      declarations: [PrevComponent, MockComponent],
      providers: [
        MockProvider(UserDataService, {
          hasRole: jasmine.createSpy().and.returnValue(of(false)),
        }),
      ],
    });
  });

  it('it should route to previous url', fakeAsync(() => {
    const router = TestBed.inject(Router);
    const location = TestBed.inject(Location);
    const userDataService = TestBed.inject(UserDataService);

    router.resetConfig(routes);

    router.navigate(['/currentPath']);
    flushMicrotasks();

    expect(location.path()).toEqual('/previousPath');
    expect(userDataService.hasRole).toHaveBeenCalledWith(Role.ADMIN);
  }));
});

describe('User has required role', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      declarations: [PrevComponent, MockComponent],
      providers: [
        MockProvider(UserDataService, {
          hasRole: jasmine.createSpy().and.returnValue(of(true)),
        }),
      ],
    });
  });

  it('it should allow routing', fakeAsync(() => {
    const router = TestBed.inject(Router);
    const location = TestBed.inject(Location);
    const userDataService = TestBed.inject(UserDataService);

    router.resetConfig(routes);

    router.navigate(['/currentPath']);
    flushMicrotasks();

    expect(location.path()).toEqual('/currentPath');
    expect(userDataService.hasRole).toHaveBeenCalledWith(Role.ADMIN);
  }));
});
