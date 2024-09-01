import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {
  provideHttpClient,
  withInterceptorsFromDi,
  withXsrfConfiguration,
} from '@angular/common/http';
import { AuthConfigModule } from './auth/auth-config.module';
import { AuthDirectivesModule } from './auth/directives/auth-directives.module';
import { NavigationModule } from './navigation/navigation.module';
import { StoreModule } from '@ngrx/store';

@NgModule({
  declarations: [AppComponent],
  bootstrap: [AppComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    AuthConfigModule,
    AuthDirectivesModule,
    NavigationModule,
    StoreModule.forRoot({}, {}),
  ],
  providers: [
    provideHttpClient(
      withInterceptorsFromDi(),
      withXsrfConfiguration({
        cookieName: 'XSRF-TOKEN',
        headerName: 'X-XSRF-TOKEN',
      })
    ),
  ],
})
export class AppModule {}
