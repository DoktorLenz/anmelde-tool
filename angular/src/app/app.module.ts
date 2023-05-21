import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';
import { AuthConfig, OAuthModule, OAuthStorage } from 'angular-oauth2-oidc';
import { StatehandlerService, StatehandlerServiceImpl } from './auth/services/statehandler.service';
import { StatehandlerProcessorService, StatehandlerProcessorServiceImpl }
  from './auth/services/statehandler-processor.service';
import { OAuthStorageService } from './auth/services/oauth-storage.service';

const authConfig: AuthConfig = {
  scope: 'openid profile email offline_access',
  responseType: 'code',
  oidc: true,
  clientId: '214961548605587459@anmeldetool_dev',
  issuer: 'http://localhost:8080', // eg. https://acme-jdo9fs.zitadel.cloud
  redirectUri: 'http://localhost:4200/auth/callback',
  postLogoutRedirectUri: 'http://localhost:4200/signedout',
  requireHttps: false, // required for running locally
};

const stateHandlerFn = (stateHandler: StatehandlerService) => {
  return () => {
    return stateHandler.initStateHandler();
  };
};

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    HttpClientXsrfModule.withOptions({
      cookieName: 'XSRF-TOKEN',
      headerName: 'X-XSRF-TOKEN',
    }),
    OAuthModule.forRoot({
      resourceServer: {
        allowedUrls: [
          'http://localhost:8080/admin/v1',
          'http://localhost:8080/management/v1',
          'http://localhost:8080/auth/v1/',
        ],
        sendAccessToken: true,
      },
    }),
    ToastModule,
    ButtonModule,
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: stateHandlerFn,
      multi: true,
      deps: [StatehandlerService],
    },
    {
      provide: AuthConfig,
      useValue: authConfig,
    },
    {
      provide: StatehandlerProcessorService,
      useClass: StatehandlerProcessorServiceImpl,
    },
    {
      provide: StatehandlerService,
      useClass: StatehandlerServiceImpl,
    },
    {
      provide: OAuthStorage,
      useClass: OAuthStorageService,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
