import {
  provideHttpClient,
  withInterceptorsFromDi,
  withXsrfConfiguration,
} from '@angular/common/http';
import { importProvidersFrom, isDevMode } from '@angular/core';
import { BrowserModule, bootstrapApplication } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { EffectsModule } from '@ngrx/effects';
import { StoreModule } from '@ngrx/store';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import Material from '@primeng/themes/aura';
import { providePrimeNG } from 'primeng/config';
import { AppRoutingModule } from './app/app-routing.module';
import { AppComponent } from './app/app.component';
import { AuthConfigModule } from './app/auth/auth-config.module';
import { NavigationModule } from './app/navigation/navigation.module';
import { metaReducers, reducers } from './app/reducers';

bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(
      BrowserModule,
      AppRoutingModule,
      AuthConfigModule,
      NavigationModule,
      StoreModule.forRoot(reducers, { metaReducers }),
      EffectsModule.forRoot(),
      StoreDevtoolsModule.instrument({
        maxAge: 25,
        logOnly: !isDevMode(),
        autoPause: true,
        trace: true,
        traceLimit: 75,
        connectInZone: true,
      })
    ),
    provideHttpClient(
      withInterceptorsFromDi(),
      withXsrfConfiguration({
        cookieName: 'XSRF-TOKEN',
        headerName: 'X-XSRF-TOKEN',
      })
    ),
    provideAnimations(),
    providePrimeNG({
      theme: {
        preset: Material,
      },
    }),
  ],
}).catch(err => console.error(err));
