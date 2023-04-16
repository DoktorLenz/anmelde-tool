import { TestBed } from '@angular/core/testing';

import { SessionDetailsInterceptor } from './session-details.interceptor';
import { MockBuilder, NG_MOCKS_INTERCEPTORS, ngMocks } from 'ng-mocks';
import { HTTP_INTERCEPTORS, HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SessionService } from '../../services/session/session.service';
import { AppModule } from 'src/app/app.module';
import { Authority } from '../../services/session/authority';

describe('SessionDetailsInterceptor', () => {
  // beforeEach(() => TestBed.configureTestingModule({
  //   providers: [
  //     SessionDetailsInterceptor,
  //   ],
  // }));

  // it('should be created', () => {
  //   const interceptor: SessionDetailsInterceptor = TestBed.inject(SessionDetailsInterceptor);

  //   expect(interceptor).toBeTruthy();
  // });

  beforeEach(() => {
    return MockBuilder(SessionDetailsInterceptor, AppModule)
      .exclude(NG_MOCKS_INTERCEPTORS)
      .keep(HTTP_INTERCEPTORS)
      .replace(HttpClientModule, HttpClientTestingModule)
      .mock(SessionService);
  });


  describe('no session details transmitted', () => {
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

  describe('session details partially transmitted', () => {
    it('should update session details to partially transmitted data', () => {
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

      expect(sessionService.setSessionDetails).toHaveBeenCalledOnceWith(true, []);
    });
  });

  describe('session details transmitted', () => {
    it('should update session details to transmitted data', () => {
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
        headers: new HttpHeaders({ 'session-authenticated': 'true', 'session-authorities': '[ROLE_USER]' }),
      };


      client.get(url).subscribe(() => {});
      controller.expectOne(url).flush(null, mockResponse);

      expect(sessionService.setSessionDetails).toHaveBeenCalledOnceWith(true, [Authority.ROLE_USER]);
    });
  });
});
