import { HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { AuthInterceptor, AuthModule, StsConfigHttpLoader, StsConfigLoader }
  from 'angular-auth-oidc-client';
import { map } from 'rxjs';
import { Configuration } from '../lib/models/configuration/configuration';
import { BaseRoute } from '../lib/routes/base-route';
import { AuthRoute } from '../lib/routes/auth-route';

export const httpLoaderFactory = (httpClient: HttpClient) => {
  const config$ = httpClient.get<Configuration>('/api/v1/configuration').pipe(
    map((config: Configuration) => ({
      authority: config.oauth2Configuration.authority,
      redirectUrl: `${window.location.origin}/${BaseRoute.AUTH}/${AuthRoute.CALLBACK}`,
      postLogoutRedirectUri: window.location.origin,
      clientId: config.oauth2Configuration.clientId,
      secureRoutes: config.oauth2Configuration.secureRoutes,
      scope: 'openid profile offline_access',
      responseType: 'code',
      silentRenew: true,
      useRefreshToken: true,
      renewTimeBeforeTokenExpiresInSeconds: 30,
    })),
  );

  return new StsConfigHttpLoader(config$);
};


@NgModule({
  imports: [AuthModule.forRoot({
    loader: {
      provide: StsConfigLoader,
      useFactory: httpLoaderFactory,
      deps: [HttpClient],
    },
  })],
  exports: [AuthModule],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
  ],
})
export class AuthConfigModule {}
