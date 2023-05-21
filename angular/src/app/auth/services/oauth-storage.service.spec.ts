import { OAuthStorageService } from './oauth-storage.service';
import { MockBuilder, MockRender } from 'ng-mocks';

describe('OAuthStorageService', () => {
  beforeEach(() =>
    MockBuilder(OAuthStorageService),
  );

  it('should be created', () => {
    const service = MockRender(OAuthStorageService).point.componentInstance;

    expect(service).toBeTruthy();
  });
});
