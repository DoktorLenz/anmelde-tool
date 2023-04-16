import { MockBuilder, MockRender, MockService, ngMocks } from 'ng-mocks';
import { ErrorTooltipPipe } from './error-tooltip.pipe';
import { AbstractControl } from '@angular/forms';

describe('ErrorTooltipPipe', () => {
  ngMocks.faster();

  beforeEach(() => MockBuilder(ErrorTooltipPipe));

  it('should return empty string if control has no errors', () => {
    const control: AbstractControl = MockService(AbstractControl);
    const fixture = MockRender('{{ control | errorTooltip }}', {
      control: control,
    });

    expect(fixture.nativeElement.innerHTML).toEqual('');
  });

  it('should return "Erforderlich" if control has required error', () => {
    const control: AbstractControl = MockService(AbstractControl, {
      errors: { 'required': true },
    });
    const fixture = MockRender('{{ control | errorTooltip }}', {
      control: control,
    });

    expect(fixture.nativeElement.innerHTML).toEqual('Erforderlich');
  });

  it('should return "Gib eine gültige E-Mail Adresse ein" if control has email error', () => {
    const control: AbstractControl = MockService(AbstractControl, {
      errors: { 'email': true },
    });
    const fixture = MockRender('{{ control | errorTooltip }}', {
      control: control,
    });

    expect(fixture.nativeElement.innerHTML).toEqual('Gib eine gültige E-Mail Adresse ein');
  });

  it('should return "unknown error {error}" if control has unhandled error', () => {
    const control: AbstractControl = MockService(AbstractControl, {
      errors: { 'unhandled error': true },
    });
    const fixture = MockRender('{{ control | errorTooltip }}', {
      control: control,
    });

    expect(fixture.nativeElement.innerHTML).toEqual('unknown error "unhandled error"');
  });

  it('should join on multiple errors', () => {
    const control: AbstractControl = MockService(AbstractControl, {
      errors: { 'required': true, 'email': true  },
    });
    const fixture = MockRender('{{ control | errorTooltip }}', {
      control: control,
    });

    expect(fixture.nativeElement.innerHTML).toEqual('Erforderlich, Gib eine gültige E-Mail Adresse ein');
  });
});
