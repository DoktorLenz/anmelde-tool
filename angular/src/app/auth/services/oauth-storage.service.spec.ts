import { TestBed } from '@angular/core/testing';

import { OAuthStorageService } from './oauth-storage.service';

describe('OAuthStorageService', () => {
  let service: OAuthStorageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OAuthStorageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
