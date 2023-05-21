
import { StatehandlerServiceImpl } from './statehandler.service';
import { MockBuilder, MockProviders, MockRender } from 'ng-mocks';
import { OAuthService } from 'angular-oauth2-oidc';
import { StatehandlerProcessorService } from './statehandler-processor.service';

describe('StatehandlerService', () => {
  beforeEach(() =>
    MockBuilder(StatehandlerServiceImpl)
      .provide(MockProviders(OAuthService, StatehandlerProcessorService)),
  );

  it('should be created', () => {
    const service = MockRender(StatehandlerServiceImpl).point.componentInstance;

    expect(service).toBeTruthy();
  });
});
