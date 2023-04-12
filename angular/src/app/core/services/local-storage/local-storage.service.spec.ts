import { TestBed } from '@angular/core/testing';

import { LocalStorageService } from './local-storage.service';
import { LocalStorageKey } from './local-storage-key';
import { Authority } from '../session/authority';
import { ngMocks } from 'ng-mocks';

describe('LocalStorageService', () => {
  let localStorageService: LocalStorageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    localStorageService = TestBed.inject(LocalStorageService);
  });

  afterEach(() => {
    localStorage.clear();
  });

  it('should be created', () => {
    expect(localStorageService).toBeTruthy();
  });

  describe('setSessionAuthenticated', () => {
    it('should set SESSION_AUTHENTICATED to true', () => {
      localStorageService.setSessionAuthenticated(true);
      const storedValue = localStorage.getItem(LocalStorageKey.SESSION_AUTHENTICATED.toString());

      expect(storedValue).toEqual('true');
    });

    it('should set SESSION_AUTHENTICATED to false', () => {
      localStorageService.setSessionAuthenticated(false);
      const storedValue = localStorage.getItem(LocalStorageKey.SESSION_AUTHENTICATED.toString());

      expect(storedValue).toEqual('false');
    });
  });

  describe('getSessionAuthenticated', () => {
    it('should return false if SESSION_AUTHENTICATED is not set', () => {
      const storedValue = localStorageService.getSessionAuthenticated();

      expect(storedValue).toBeFalse();
    });

    it('should return true if SESSION_AUTHENTICATED is set to true', () => {
      localStorage.setItem(LocalStorageKey.SESSION_AUTHENTICATED.toString(), 'true');
      const storedValue = localStorageService.getSessionAuthenticated();

      expect(storedValue).toBeTrue();
    });

    it('should return false if SESSION_AUTHENTICATED is set to false', () => {
      localStorage.setItem(LocalStorageKey.SESSION_AUTHENTICATED.toString(), 'false');
      const storedValue = localStorageService.getSessionAuthenticated();

      expect(storedValue).toBeFalse();
    });
  });

  describe('setSessionAuthorities', () => {
    it('should set SESSION_AUTHORITIES to an empty array if an empty array is passed', () => {
      localStorageService.setSessionAuthorities([]);
      const storedValue = localStorage.getItem(LocalStorageKey.SESSION_AUTHORITIES.toString());

      expect(storedValue).toEqual('[]');
    });

    it('should set SESSION_AUTHORITIES to a non-empty array', () => {
      localStorageService.setSessionAuthorities([Authority.ROLE_USER]);
      const storedValue = localStorage.getItem(LocalStorageKey.SESSION_AUTHORITIES.toString());

      expect(storedValue).toEqual('["ROLE_USER"]');
    });
  });

  describe('getSessionAuthorities', () => {
    it('should return an empty array if SESSION_AUTHORITIES is not set', () => {
      const storedValue = localStorageService.getSessionAuthorities();

      expect(storedValue).toEqual([]);
    });

    it('should return a non-empty array if SESSION_AUTHORITIES is set to a valid value', () => {
      localStorage.setItem(LocalStorageKey.SESSION_AUTHORITIES.toString(), '["ROLE_ADMIN"]');
      const storedValue = localStorageService.getSessionAuthorities();

      expect(storedValue).toEqual([Authority.ROLE_ADMIN]);
    });

    it('should return an empty array if SESSION_AUTHORITIES is set to an invalid value', () => {
      ngMocks.stub(console, {
        error: jasmine.createSpy().and.callFake(() => {}),
      });
      localStorage.setItem(LocalStorageKey.SESSION_AUTHORITIES.toString(), 'invalid');
      const storedValue = localStorageService.getSessionAuthorities();

      expect(storedValue).toEqual([]);
      expect(console.error).toHaveBeenCalledWith(jasmine.any(Error));
    });
  });

});
