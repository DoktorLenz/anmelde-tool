import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';


import { provideHttpClient, withInterceptorsFromDi, withXsrfConfiguration } from '@angular/common/http';
import { BrowserModule, bootstrapApplication } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app/app-routing.module';
import { AuthConfigModule } from './app/auth/auth-config.module';
import { NavigationModule } from './app/navigation/navigation.module';
import { StoreModule } from '@ngrx/store';
import { reducers, metaReducers } from './app/reducers';
import { EffectsModule } from '@ngrx/effects';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { isDevMode, importProvidersFrom } from '@angular/core';
import { AppComponent } from './app/app.component';

bootstrapApplication(AppComponent, {
    providers: [
        importProvidersFrom(BrowserModule, AppRoutingModule, AuthConfigModule, NavigationModule, StoreModule.forRoot(reducers, { metaReducers }), EffectsModule.forRoot(), StoreDevtoolsModule.instrument({
            maxAge: 25,
            logOnly: !isDevMode(),
            autoPause: true,
            trace: true,
            traceLimit: 75,
            connectInZone: true,
        })),
        provideHttpClient(withInterceptorsFromDi(), withXsrfConfiguration({
            cookieName: 'XSRF-TOKEN',
            headerName: 'X-XSRF-TOKEN',
        })),
        provideAnimations(),
    ]
})
  .catch(err => console.error(err));
