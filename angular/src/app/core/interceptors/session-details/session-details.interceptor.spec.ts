import { TestBed } from '@angular/core/testing';

import { SessionDetailsInterceptor } from './session-details.interceptor';

describe('SessionDetailsInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      SessionDetailsInterceptor,
    ],
  }));

  it('should be created', () => {
    const interceptor: SessionDetailsInterceptor = TestBed.inject(SessionDetailsInterceptor);

    expect(interceptor).toBeTruthy();
  });
});
