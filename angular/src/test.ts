import { getTestBed } from '@angular/core/testing';
import { BrowserDynamicTestingModule, platformBrowserDynamicTesting }
  from '@angular/platform-browser-dynamic/testing';
import { ngMocks } from 'ng-mocks';

// First, initialize the Angular testing environment.
getTestBed().initTestEnvironment(BrowserDynamicTestingModule, platformBrowserDynamicTesting());

ngMocks.autoSpy('jasmine');
jasmine.getEnv().allowRespy(true);
