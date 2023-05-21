import {  StatehandlerProcessorServiceImpl } from './statehandler-processor.service';
import { MockBuilder, MockProvider, MockRender } from 'ng-mocks';
import { Location } from '@angular/common';

describe('StatehandlerProcessorService', () => {
  beforeEach(() =>
    MockBuilder(StatehandlerProcessorServiceImpl)
      .provide(MockProvider(Location)),
  );

  it('should be created', () => {
    const service = MockRender(StatehandlerProcessorServiceImpl).point.componentInstance;

    expect(service).toBeTruthy();
  });
});
