import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpBase } from '../http-base';
import { BaseRoute } from 'src/app/lib/routes/base-route';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class HttpSessionService extends HttpBase {

  constructor(private readonly httpClient: HttpClient) {
    super();
  }

  public session(): Observable<void> {
    return this.httpClient.get(`${this.baseUrl}/${BaseRoute.SESSION}`).pipe(
      map(() => {return;}),
    );
  }
}
