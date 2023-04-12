import { TestBed } from '@angular/core/testing';

import { HttpSessionService } from './http-session.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('HttpSessionService', () => {
  let httpSessionService: HttpSessionService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [HttpSessionService],
    });
    httpSessionService = TestBed.inject(HttpSessionService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  describe('session()', () => {
    it('should send a GET request to receive JSESSIONID and XSRF Token as Cookie', () => {
      httpSessionService.session().subscribe((result) => {
        expect(result).toBeUndefined();
      });

      const request = httpTestingController.expectOne('/api/v1/session');

      expect(request.request.method).toEqual('GET');
      request.flush(null);
    });
  });
});
