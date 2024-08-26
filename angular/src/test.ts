import { getTestBed } from '@angular/core/testing';
import { BrowserDynamicTestingModule, platformBrowserDynamicTesting } from '@angular/platform-browser-dynamic/testing';
import { ngMocks } from 'ng-mocks';

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import JasmineDOM from '@testing-library/jasmine-dom/dist';

// First, initialize the Angular testing environment.
getTestBed().initTestEnvironment(
  BrowserDynamicTestingModule,
  platformBrowserDynamicTesting(),
  { teardown: { destroyAfterEach: false } },
);

ngMocks.autoSpy('jasmine');
jasmine.getEnv().allowRespy(true);

beforeEach(() => {
  jasmine.addMatchers(JasmineDOM);
});


