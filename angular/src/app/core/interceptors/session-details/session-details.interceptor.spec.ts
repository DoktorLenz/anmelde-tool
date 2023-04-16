import { TestBed } from '@angular/core/testing';

import { SessionDetailsInterceptor } from './session-details.interceptor';
import { MockBuilder, NG_MOCKS_INTERCEPTORS, ngMocks } from 'ng-mocks';
import { HTTP_INTERCEPTORS, HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SessionService } from '../../services/session/session.service';
import { AppModule } from 'src/app/app.module';
import { Authority } from '../../services/session/authority';

describe('SessionDetailsInterceptor', () => {

  beforeEach(() => {
    return MockBuilder(SessionDetailsInterceptor, AppModule)
      .exclude(NG_MOCKS_INTERCEPTORS)
      .keep(HTTP_INTERCEPTORS)
      .replace(HttpClientModule, HttpClientTestingModule)
      .mock(SessionService);
  });

  describe('no session detail header is set', () => {
    it('should update session details to not authenticated and no authorities', () => {
      const url = '/test';
      const client = TestBed.inject(HttpClient);
      const controller = TestBed.inject(HttpTestingController);
      const sessionService = TestBed.inject(SessionService);
      ngMocks.stub(sessionService, {
        setSessionDetails: jasmine.createSpy().and.callFake(() => {}),
      });
      const mockResponse: { status: number, statusText: string, headers: HttpHeaders } = {
        status: 200,
        statusText: '',
        headers: new HttpHeaders(),
      };


      client.get(url).subscribe(() => {});
      controller.expectOne(url).flush(null, mockResponse);

      expect(sessionService.setSessionDetails).toHaveBeenCalledOnceWith(false, []);
    });
  });

  describe('session-authenticated header', () => {
    it('should update session details to not authenticated on header value not authenticated', () => {
      const url = '/test';
      const client = TestBed.inject(HttpClient);
      const controller = TestBed.inject(HttpTestingController);
      const sessionService = TestBed.inject(SessionService);
      ngMocks.stub(sessionService, {
        setSessionDetails: jasmine.createSpy().and.callFake(() => {}),
      });
      const mockResponse: { status: number, statusText: string, headers: HttpHeaders } = {
        status: 200,
        statusText: '',
        headers: new HttpHeaders({ 'session-authenticated': 'false' }),
      };


      client.get(url).subscribe(() => {});
      controller.expectOne(url).flush(null, mockResponse);

      expect(sessionService.setSessionDetails).toHaveBeenCalledOnceWith(false, jasmine.any(Array));
    });

    it('should update session details to authenticated on header value authenticated', () => {
      const url = '/test';
      const client = TestBed.inject(HttpClient);
      const controller = TestBed.inject(HttpTestingController);
      const sessionService = TestBed.inject(SessionService);
      ngMocks.stub(sessionService, {
        setSessionDetails: jasmine.createSpy().and.callFake(() => {}),
      });
      const mockResponse: { status: number, statusText: string, headers: HttpHeaders } = {
        status: 200,
        statusText: '',
        headers: new HttpHeaders({ 'session-authenticated': 'true' }),
      };


      client.get(url).subscribe(() => {});
      controller.expectOne(url).flush(null, mockResponse);

      expect(sessionService.setSessionDetails).toHaveBeenCalledOnceWith(true, jasmine.any(Array));
    });

    it('should update session details to not authenticated on invalid header value', () => {
      const url = '/test';
      const client = TestBed.inject(HttpClient);
      const controller = TestBed.inject(HttpTestingController);
      const sessionService = TestBed.inject(SessionService);
      ngMocks.stub(sessionService, {
        setSessionDetails: jasmine.createSpy().and.callFake(() => {}),
      });
      const mockResponse: { status: number, statusText: string, headers: HttpHeaders } = {
        status: 200,
        statusText: '',
        headers: new HttpHeaders({ 'session-authenticated': 'invalid' }),
      };


      client.get(url).subscribe(() => {});
      controller.expectOne(url).flush(null, mockResponse);

      expect(sessionService.setSessionDetails).toHaveBeenCalledOnceWith(false, jasmine.any(Array));
    });
  });

  describe('session-authorities header', () => {
    it('should update session details to no authorities on header value empty array', () => {
      const url = '/test';
      const client = TestBed.inject(HttpClient);
      const controller = TestBed.inject(HttpTestingController);
      const sessionService = TestBed.inject(SessionService);
      ngMocks.stub(sessionService, {
        setSessionDetails: jasmine.createSpy().and.callFake(() => {}),
      });
      const mockResponse: { status: number, statusText: string, headers: HttpHeaders } = {
        status: 200,
        statusText: '',
        headers: new HttpHeaders({ 'session-authorities': '[]' }),
      };


      client.get(url).subscribe(() => {});
      controller.expectOne(url).flush(null, mockResponse);

      expect(sessionService.setSessionDetails).toHaveBeenCalledOnceWith(jasmine.any(Boolean), []);
    });

    it('should update session details to single authorities on header value single value in array', () => {
      const url = '/test';
      const client = TestBed.inject(HttpClient);
      const controller = TestBed.inject(HttpTestingController);
      const sessionService = TestBed.inject(SessionService);
      ngMocks.stub(sessionService, {
        setSessionDetails: jasmine.createSpy().and.callFake(() => {}),
      });
      const mockResponse: { status: number, statusText: string, headers: HttpHeaders } = {
        status: 200,
        statusText: '',
        headers: new HttpHeaders({ 'session-authorities': '[ROLE_USER]' }),
      };


      client.get(url).subscribe(() => {});
      controller.expectOne(url).flush(null, mockResponse);

      expect(sessionService.setSessionDetails)
        .toHaveBeenCalledOnceWith(jasmine.any(Boolean), [Authority.ROLE_USER]);
    });

    it(
      'should update session details to multiple authorities on header value multiple values in array',
      () => {
        const url = '/test';
        const client = TestBed.inject(HttpClient);
        const controller = TestBed.inject(HttpTestingController);
        const sessionService = TestBed.inject(SessionService);
        ngMocks.stub(sessionService, {
          setSessionDetails: jasmine.createSpy().and.callFake(() => {}),
        });
        const mockResponse: { status: number, statusText: string, headers: HttpHeaders } = {
          status: 200,
          statusText: '',
          headers: new HttpHeaders({ 'session-authorities': '[ROLE_USER,ROLE_ADMIN]' }),
        };


        client.get(url).subscribe(() => {});
        controller.expectOne(url).flush(null, mockResponse);

        expect(sessionService.setSessionDetails)
          .toHaveBeenCalledOnceWith(jasmine.any(Boolean), [Authority.ROLE_USER, Authority.ROLE_ADMIN]);
      },
    );

    it(
      'should update session details to single authorities on header value single value without array',
      () => {
        const url = '/test';
        const client = TestBed.inject(HttpClient);
        const controller = TestBed.inject(HttpTestingController);
        const sessionService = TestBed.inject(SessionService);
        ngMocks.stub(sessionService, {
          setSessionDetails: jasmine.createSpy().and.callFake(() => {}),
        });
        const mockResponse: { status: number, statusText: string, headers: HttpHeaders } = {
          status: 200,
          statusText: '',
          headers: new HttpHeaders({ 'session-authorities': 'ROLE_USER' }),
        };


        client.get(url).subscribe(() => {});
        controller.expectOne(url).flush(null, mockResponse);

        expect(sessionService.setSessionDetails)
          .toHaveBeenCalledOnceWith(jasmine.any(Boolean), [Authority.ROLE_USER]);
      },
    );

    it('should update session details to no authorities on invalid header value', () => {
      const url = '/test';
      const client = TestBed.inject(HttpClient);
      const controller = TestBed.inject(HttpTestingController);
      const sessionService = TestBed.inject(SessionService);
      ngMocks.stub(sessionService, {
        setSessionDetails: jasmine.createSpy().and.callFake(() => {}),
      });
      const mockResponse: { status: number, statusText: string, headers: HttpHeaders } = {
        status: 200,
        statusText: '',
        headers: new HttpHeaders({ 'session-authorities': '[INVALID]' }),
      };


      client.get(url).subscribe(() => {});
      controller.expectOne(url).flush(null, mockResponse);

      expect(sessionService.setSessionDetails)
        .toHaveBeenCalledOnceWith(jasmine.any(Boolean), []);
    });
  });
});
