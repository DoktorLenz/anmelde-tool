import { HeaderComponent } from './header.component';
import { MockBuilder, MockRender, ngMocks } from 'ng-mocks';
import { NavigationService } from '../navigation.service';
import { Breakpoint } from 'src/app/layout/directives/breakpoint/breakpoint.enum';
import { screen } from '@testing-library/angular';
import userEvent from '@testing-library/user-event';
import { MenubarModule } from 'primeng/menubar';
import { ButtonModule } from 'primeng/button';
import { AvatarModule } from 'primeng/avatar';
import { NavigationModule } from '../navigation.module';
import { LayoutDirectivesModule } from 'src/app/layout/directives/layout-directives.module';


describe('HeaderComponent', () => {
  beforeEach(() => {
    return (
      MockBuilder(HeaderComponent, NavigationModule)
        .mock(NavigationService)
        .keep(MenubarModule)
        .keep(ButtonModule)
        .keep(AvatarModule)
        .keep(LayoutDirectivesModule)
    );
  });

  it('should create', () => {
    const view = MockRender(HeaderComponent);

    expect(view).toBeTruthy();
  });

  it('should notify navigation service that sidebar is visible when clicking on the menu icon', async () => {
    MockRender(HeaderComponent);
    const navigationService = ngMocks.findInstance(NavigationService);
    spyOnProperty(window, 'innerWidth').and.returnValue(Breakpoint.LARGE);
    spyOn(navigationService, 'sidebarVisible').and.callFake(() => {});

    window.dispatchEvent(new Event('resize'));

    const user = userEvent.setup();
    await user.click(screen.getByTestId('menu-button'));

    expect(navigationService.sidebarVisible).toHaveBeenCalledWith(true);
  });

  it('should hide menu icon if screen is large', async () => {
    MockRender(HeaderComponent);
    spyOnProperty(window, 'innerWidth').and.returnValue(Breakpoint.LARGE + 1);
    window.dispatchEvent(new Event('resize'));

    expect(screen.queryByTestId('menu-button')).not.toBeInTheDocument();
  });
});
