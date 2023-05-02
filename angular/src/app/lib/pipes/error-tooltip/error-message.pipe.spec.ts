import { MockBuilder, MockRender, MockService, ngMocks } from 'ng-mocks';
import { ErrorMessagePipe } from './error-message.pipe';
import { AbstractControl } from '@angular/forms';

describe('ErrorMessagePipe', () => {
  ngMocks.faster();

  beforeEach(() => MockBuilder(ErrorMessagePipe));

  it('should return empty string if control has no errors', () => {
    const control: AbstractControl = MockService(AbstractControl);
    const view = MockRender('{{ control.errors | errorMessage }}', {
      control: control,
    });

    expect(view.nativeElement.innerHTML).toEqual('');
  });

  it('should return "Erforderlich" if control has required error', () => {
    const control: AbstractControl = MockService(AbstractControl, {
      errors: { 'required': true },
    });
    const view = MockRender('{{ control.errors | errorMessage }}', {
      control: control,
    });

    expect(view.nativeElement.innerHTML).toEqual('Erforderlich');
  });

  it('should return "Ungültige E-Mail Adresse" if control has email error', () => {
    const control: AbstractControl = MockService(AbstractControl, {
      errors: { 'email': true },
    });
    const view = MockRender('{{ control.errors | errorMessage }}', {
      control: control,
    });

    expect(view.nativeElement.innerHTML).toEqual('Ungültige E-Mail Adresse');
  });

  it('should return "unknown error {error}" if control has unhandled error', () => {
    const control: AbstractControl = MockService(AbstractControl, {
      errors: { 'unhandled error': true },
    });
    const view = MockRender('{{ control.errors | errorMessage }}', {
      control: control,
    });

    expect(view.nativeElement.innerHTML).toEqual('unknown error "unhandled error"');
  });

  it('should join on multiple errors', () => {
    const control: AbstractControl = MockService(AbstractControl, {
      errors: { 'required': true, 'email': true  },
    });
    const view = MockRender('{{ control.errors | errorMessage }}', {
      control: control,
    });

    expect(view.nativeElement.innerHTML).toEqual('Erforderlich, Ungültige E-Mail Adresse');
  });
});
