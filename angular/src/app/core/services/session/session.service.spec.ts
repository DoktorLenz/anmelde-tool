import { TestBed } from '@angular/core/testing';
import { Authority } from './authority';
import { LocalStorageService } from '../local-storage/local-storage.service';
import { SessionService } from './session.service';
import { MockProvider, ngMocks } from 'ng-mocks';

describe('SessionService', () => {
  let sessionService: SessionService;
  let localStorageService: LocalStorageService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        SessionService,
        MockProvider(LocalStorageService),
      ],
    });
    sessionService = TestBed.inject(SessionService);
    localStorageService = TestBed.inject(LocalStorageService);
  });

  describe('isSessionAuthenticated()', () => {
    it('should return true when session is authenticated loaded from localstorage service', () => {
      ngMocks.stub(localStorageService, {
        getSessionAuthenticated: jasmine.createSpy().and.returnValue(true),
      });

      const isAuthenticated = sessionService.isSessionAuthenticated();

      expect(isAuthenticated).toBe(true);
      expect(localStorageService.getSessionAuthenticated).toHaveBeenCalledWith();
    });

    it('should return false when session is not authenticated loaded from localstorage service', () => {
      ngMocks.stub(localStorageService, {
        getSessionAuthenticated: jasmine.createSpy().and.returnValue(false),
      });

      const isAuthenticated = sessionService.isSessionAuthenticated();

      expect(isAuthenticated).toBe(false);
      expect(localStorageService.getSessionAuthenticated).toHaveBeenCalledWith();
    });
  });

  describe('setSessionDetails()', () => {

    it('should set session authenticated and authorities in local storage', () => {
      const authorities: Authority[] = [Authority.ROLE_ADMIN, Authority.ROLE_USER];

      sessionService.setSessionDetails(true, authorities);

      expect(localStorageService.setSessionAuthenticated).toHaveBeenCalledWith(true);
      expect(localStorageService.setSessionAuthorities).toHaveBeenCalledWith(authorities);
    });
  });
});
