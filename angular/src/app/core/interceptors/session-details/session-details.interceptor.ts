import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpResponse } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { SessionService } from '../../services/session/session.service';
import { Authority } from '../../services/session/authority';

@Injectable()
export class SessionDetailsInterceptor implements HttpInterceptor {

  constructor(private readonly sessionService: SessionService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(
      tap((httpEvent: HttpEvent<unknown>) => {
        if (httpEvent.type === 0) {
          return;
        }
        if (httpEvent instanceof HttpResponse) {
          let authenticated = false;
          let authorities: Authority[] = [];
          if (httpEvent.headers.has('session-authenticated')) {
            authenticated = httpEvent.headers.get('session-authenticated') === 'true' ? true : false;
          }
          if (httpEvent.headers.has('session-authorities')) {
            let authorityString = httpEvent.headers.get('session-authorities') ?? '';
            if (authorityString) {
              authorityString = authorityString.replace('[', '')
                .replace(']', '');
            }
            authorities = authorityString.split(',')
              .map((authority) => Authority[authority.trim() as keyof typeof Authority]);
          }
          this.sessionService.setSessionDetails(authenticated, authorities);
        }
      }),
    );
  }
}
