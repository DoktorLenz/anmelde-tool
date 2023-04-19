import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RegistrationRequestDto } from './model/registration-request.dto';
import { Observable, map } from 'rxjs';
import { HttpBase } from '../http-base';
import { BaseRoute } from 'src/app/lib/routes/base-route';
import { AuthRoute } from 'src/app/lib/routes/auth-route';

@Injectable({
  providedIn: 'root',
})
export class HttpAuthService extends HttpBase {

  constructor(private httpClient: HttpClient) {
    super();
  }

  public register(dto: RegistrationRequestDto): Observable<void> {
    return this.httpClient.post(`${this.baseUrl}/${BaseRoute.AUTH}/${AuthRoute.REGISTER}`, dto)
      .pipe(
        map(() => {return;}),
      );
  }

  public login(username: string, password: string): Observable<void> {
    return this.httpClient.get(`${this.baseUrl}/${BaseRoute.AUTH}/${AuthRoute.LOGIN}`, {
      headers: new HttpHeaders({
        // eslint-disable-next-line deprecation/deprecation
        'Authorization': `Basic ${btoa(`${username}:${password}`)}`,
      }),
    }).pipe(map(() => {return;}));
  }
}

