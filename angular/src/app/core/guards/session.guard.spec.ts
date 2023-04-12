import { TestBed } from '@angular/core/testing';
import { SessionService } from '../services/session/session.service';
import { BaseRoute } from 'src/app/lib/routes/base-route';
import { sessionAuthenticatedGuard, sessionNotAuthenticatedGuard } from './session.guard';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';


describe('SessionGuard', () => {

  describe('sessionAuthenticatedGuard', () => {
    let mockRouter: jasmine.SpyObj<Router>;
    let mockRoute: jasmine.SpyObj<ActivatedRouteSnapshot>;
    let mockState: jasmine.SpyObj<RouterStateSnapshot>;

    beforeEach(() => {
      mockRouter = jasmine.createSpyObj<Router>('Router', ['navigate']);
      mockRoute = jasmine.createSpyObj('ActivatedRouteSnapshot', ['']);
      mockRoute = jasmine.createSpyObj('RouterStateSnapshot', ['']);
    });

    it('should return true if session is authenticated and not navigate', () => {
      TestBed.configureTestingModule({
        providers: [
          { provide: SessionService, useValue: { isSessionAuthenticated: () => true } },
          { provide: Router, useValue: mockRouter },
        ],
      });

      const result = TestBed.runInInjectionContext(() => sessionAuthenticatedGuard(mockRoute, mockState));

      expect(result).toBeTrue();
      expect(mockRouter.navigate).not.toHaveBeenCalled();
    });

    it('should navigate to auth page if session is not authenticated', () => {
      TestBed.configureTestingModule({
        providers: [
          { provide: SessionService, useValue: { isSessionAuthenticated: () => false } },
          { provide: Router, useValue: mockRouter },
        ],
      });

      TestBed.runInInjectionContext(() => sessionAuthenticatedGuard(mockRoute, mockState));

      expect(mockRouter.navigate).toHaveBeenCalledWith([`/${BaseRoute.AUTH}`]);
    });
  });


  describe('sessionNotAuthenticatedGuard', () => {
    let mockRouter: jasmine.SpyObj<Router>;
    let mockRoute: jasmine.SpyObj<ActivatedRouteSnapshot>;
    let mockState: jasmine.SpyObj<RouterStateSnapshot>;

    beforeEach(() => {
      mockRouter = jasmine.createSpyObj<Router>('Router', ['navigate']);
      mockRoute = jasmine.createSpyObj('ActivatedRouteSnapshot', ['']);
      mockRoute = jasmine.createSpyObj('RouterStateSnapshot', ['']);
    });

    it('should return true if session is not authenticated and not navigate', () => {
      TestBed.configureTestingModule({
        providers: [
          { provide: SessionService, useValue: { isSessionAuthenticated: () => false } },
          { provide: Router, useValue: mockRouter },
        ],
      });

      const result = TestBed.runInInjectionContext(() => sessionNotAuthenticatedGuard(mockRoute, mockState));

      expect(result).toBeTrue();
      expect(mockRouter.navigate).not.toHaveBeenCalled();
    });

    it('should navigate to root page if session is authenticated', () => {
      TestBed.configureTestingModule({
        providers: [
          { provide: SessionService, useValue: { isSessionAuthenticated: () => true } },
          { provide: Router, useValue: mockRouter },
        ],
      });

      TestBed.runInInjectionContext(() => sessionNotAuthenticatedGuard(mockRoute, mockState));

      expect(mockRouter.navigate).toHaveBeenCalledWith(['']);
    });
  });
});
