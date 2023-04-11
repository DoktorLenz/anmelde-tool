import { TestBed } from '@angular/core/testing';

import { HttpSessionService } from './http-session.service';

describe('HttpSessionService', () => {
  let service: HttpSessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HttpSessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
