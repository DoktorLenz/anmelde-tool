import { MockBuilder, ngMocks } from 'ng-mocks';
import { NavigationService } from './navigation.service';
import { fakeAsync, flushMicrotasks } from '@angular/core/testing';

describe('NavigationService sidebarVisible$', () => {

  ngMocks.faster();
  beforeEach(() => MockBuilder(NavigationService));

  it('should hide sidebar initially', fakeAsync(() => {
    const navigationService = ngMocks.findInstance(NavigationService);
    let sidebarVisible;
    navigationService.sidebarVisible$.subscribe((visible: boolean) => {
      sidebarVisible = visible;
    });

    flushMicrotasks();

    expect(sidebarVisible).toBeFalse();
  }));

  it('should publish visibility change of the sidebar', fakeAsync(() => {
    const navigationService = ngMocks.findInstance(NavigationService);
    let sidebarVisible;
    navigationService.sidebarVisible$.subscribe((visible: boolean) => {
      sidebarVisible = visible;
    });

    navigationService.sidebarVisible(true);
    flushMicrotasks();

    expect(sidebarVisible).toBeTrue();
  }));
});
