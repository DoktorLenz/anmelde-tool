import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { RegistrationRequestDto } from './model/registration-request.dto';
import { HttpAuthService } from './http-auth.service';
import { Buffer } from 'buffer';

describe('HttpAuthService', () => {
  let httpAuthService: HttpAuthService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [HttpAuthService],
    });
    httpAuthService = TestBed.inject(HttpAuthService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  describe('register()', () => {
    it('should send a POST request to register a user and return an Observable of void', () => {
      const registrationRequestDto: RegistrationRequestDto = {
        email: 'foo@localhost',
        firstname: 'foo',
        lastname: 'bar',
      };

      httpAuthService.register(registrationRequestDto).subscribe((result) => {
        expect(result).toBeUndefined();
      });

      const request = httpTestingController.expectOne('/api/v1/auth/registration');

      expect(request.request.method).toEqual('POST');
      expect(request.request.body).toEqual(registrationRequestDto);
      request.flush(null);
    });
  });

  describe('login()', () => {
    it('should send a GET request to login a user and return an Observable of void', () => {
      const username = 'testuser';
      const password = 'testpassword';

      httpAuthService.login(username, password).subscribe((result) => {
        expect(result).toBeUndefined();
      });

      const request = httpTestingController.expectOne('/api/v1/auth/login');

      expect(request.request.method).toEqual('GET');

      const b64EncodedCredentials = Buffer.from(`${username}:${password}`).toString('base64');

      expect(request.request.headers.get('Authorization')).toEqual(`Basic ${b64EncodedCredentials}`);

      request.flush(null);
    });
  });
});
