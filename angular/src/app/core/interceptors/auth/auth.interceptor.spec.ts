import { TestBed } from '@angular/core/testing';

import { AuthInterceptor } from './auth.interceptor';
import { MockBuilder, NG_MOCKS_INTERCEPTORS } from 'ng-mocks';
import { AppModule } from 'src/app/app.module';
import { HTTP_INTERCEPTORS, HttpClient, HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('AuthInterceptor', () => {
  beforeEach(() => {
    return MockBuilder(AuthInterceptor, AppModule)
      .exclude(NG_MOCKS_INTERCEPTORS)
      .keep(HTTP_INTERCEPTORS)
      .replace(HttpClientModule, HttpClientTestingModule);
  });

  it('should set "X-Requested-With" header to value "XMLHttpRequest"', () => {
    const url = '/test';
    const client = TestBed.inject(HttpClient);
    const controller = TestBed.inject(HttpTestingController);

    client.get(url).subscribe(() => {});
    const header = controller.expectOne(url).request.headers.get('X-Requested-With');

    expect(header).toEqual('XMLHttpRequest');
  });

  it('should send every request withCredentials', () => {
    const url = '/test';
    const client = TestBed.inject(HttpClient);
    const controller = TestBed.inject(HttpTestingController);

    client.get(url).subscribe(() => {});
    const withCreds = controller.expectOne(url).request.withCredentials;

    expect(withCreds).toBeTrue();
  });
});
