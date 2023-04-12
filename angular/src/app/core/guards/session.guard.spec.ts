import { TestBed } from '@angular/core/testing';
import { SessionService } from '../services/session/session.service';
import { BaseRoute } from 'src/app/lib/routes/base-route';
import { sessionAuthenticatedGuard, sessionNotAuthenticatedGuard } from './session.guard';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { MockProvider, MockService, ngMocks } from 'ng-mocks';


describe('SessionGuard', () => {

  describe('sessionAuthenticatedGuard', () => {
    let route: ActivatedRouteSnapshot;
    let state: RouterStateSnapshot;
    let sessionService: SessionService;
    let router: Router;

    beforeEach(() => {
      route = MockService(ActivatedRouteSnapshot);
      state = MockService(RouterStateSnapshot);

      TestBed.configureTestingModule({
        providers: [
          MockProvider(SessionService),
          MockProvider(Router),
        ],
      });

      sessionService = TestBed.inject(SessionService);
      router = TestBed.inject(Router);
    });

    it('should return true if session is authenticated and not navigate', () => {
      ngMocks.stub(sessionService, {
        isSessionAuthenticated: jasmine.createSpy().and.returnValue(true),
      });

      const result = TestBed.runInInjectionContext(() => sessionAuthenticatedGuard(route, state));

      expect(result).toBeTrue();
      expect(router.navigate).not.toHaveBeenCalled();
    });

    it('should navigate to auth page if session is not authenticated', () => {
      ngMocks.stub(sessionService, {
        isSessionAuthenticated: jasmine.createSpy().and.returnValue(false),
      });

      TestBed.runInInjectionContext(() => sessionAuthenticatedGuard(route, state));

      expect(router.navigate).toHaveBeenCalledWith([`/${BaseRoute.AUTH}`]);
    });
  });


  describe('sessionNotAuthenticatedGuard', () => {
    let route: ActivatedRouteSnapshot;
    let state: RouterStateSnapshot;
    let sessionService: SessionService;
    let router: Router;

    beforeEach(() => {
      route = MockService(ActivatedRouteSnapshot);
      state = MockService(RouterStateSnapshot);

      TestBed.configureTestingModule({
        providers: [
          MockProvider(SessionService),
          MockProvider(Router),
        ],
      });

      sessionService = TestBed.inject(SessionService);
      router = TestBed.inject(Router);
    });

    it('should return true if session is not authenticated and not navigate', () => {
      ngMocks.stub(sessionService, {
        isSessionAuthenticated: jasmine.createSpy().and.returnValue(false),
      });

      const result = TestBed.runInInjectionContext(() => sessionNotAuthenticatedGuard(route, state));

      expect(result).toBeTrue();
      expect(router.navigate).not.toHaveBeenCalled();
    });

    it('should navigate to root page if session is authenticated', () => {
      ngMocks.stub(sessionService, {
        isSessionAuthenticated: jasmine.createSpy().and.returnValue(true),
      });

      TestBed.runInInjectionContext(() => sessionNotAuthenticatedGuard(route, state));

      expect(router.navigate).toHaveBeenCalledWith(['']);
    });
  });
});
