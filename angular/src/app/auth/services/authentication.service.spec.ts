import { AuthenticationService } from './authentication.service';
import { MockBuilder, MockProviders, MockRender } from 'ng-mocks';
import { AuthConfig, OAuthService } from 'angular-oauth2-oidc';
import { StatehandlerService } from './statehandler.service';

describe('AuthenticationService', () => {
  beforeEach(() =>
    MockBuilder(AuthenticationService)
      .provide(MockProviders(OAuthService, AuthConfig, StatehandlerService)),
  );

  it('should be created', () => {
    const service = MockRender(AuthenticationService).point.componentInstance;

    expect(service).toBeTruthy();
  });
});
