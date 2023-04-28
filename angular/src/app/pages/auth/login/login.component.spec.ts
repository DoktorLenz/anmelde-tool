import { TestBed } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { MockProviders, ngMocks } from 'ng-mocks';
import { MessageService } from 'primeng/api';
import { ReactiveFormsModule } from '@angular/forms';
import { RenderComponentOptions, render, screen } from '@testing-library/angular';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { HttpAuthService } from 'src/app/core/http/auth/http-auth.service';
import { EMPTY, of, throwError } from 'rxjs';
import { PipesModule } from 'src/app/lib/pipes/pipes.module';
import { Router } from '@angular/router';
import userEvent from '@testing-library/user-event';

const ui = {
  title: () => screen.getByTestId('login-title'),
  username: {
    input: () => screen.getByTestId('login-username'),
    errorIcon: () => screen.queryByTestId('login-username-error-icon'),
    errorMessage: () => screen.queryByTestId('login-username-error-message'),
  },
  password: {
    input: () => screen.getByTestId('login-password'),
    errorIcon: () => screen.queryByTestId('login-password-error-icon'),
    errorMessage: () => screen.queryByTestId('login-password-error-message'),
  },
  submitButton: () => screen.getByRole('button'),
};

const options: RenderComponentOptions<LoginComponent> = {
  providers: [
    MockProviders(MessageService, HttpAuthService, Router),
  ],
  imports: [
    CardModule,
    ButtonModule,
    InputTextModule,
    PipesModule,
    ReactiveFormsModule,
  ],
};

describe('When an invalid username is entered', () => {
  it('should display "Invalid Mail" error', async () => {
    await render(LoginComponent, options);

    const user = userEvent.setup();
    await user.type(ui.username.input(), 'notamailaddress');

    expect(ui.username.errorIcon()).toBeInTheDocument();
    expect(ui.username.errorMessage()).toBeInTheDocument();
  });

  it('should disable submit button, cause mail', async () => {
    await render(LoginComponent, options);

    const user = userEvent.setup();
    await user.type(ui.username.input(), 'notamailaddress');

    expect(ui.submitButton()).toBeDisabled();
  });
});

describe('When a password is entered and then removed', () => {
  it('should display "required" error', async () => {
    await render(LoginComponent, options);

    const user = userEvent.setup();
    await user.type(ui.password.input(), 'password');
    await user.clear(ui.password.input());

    expect(ui.password.errorIcon()).toBeInTheDocument();
    expect(ui.password.errorMessage()).toBeInTheDocument();
  });

  it('should disable submit button, cause password', async () => {
    await render(LoginComponent, options);

    const user = userEvent.setup();
    await user.type(ui.password.input(), 'password');
    await user.clear(ui.password.input());

    expect(ui.submitButton()).toBeDisabled();
  });
});

describe('When a password is entered and then removed and an invalid username is entered', () => {
  it('should dispay both errors', async () => {
    await render(LoginComponent, options);

    const user = userEvent.setup();
    await user.type(ui.username.input(), 'notamailaddress');
    await user.type(ui.password.input(), 'password');
    await user.clear(ui.password.input());

    expect(ui.username.errorIcon()).toBeInTheDocument();
    expect(ui.username.errorMessage()).toBeInTheDocument();
    expect(ui.password.errorIcon()).toBeInTheDocument();
    expect(ui.password.errorMessage()).toBeInTheDocument();
  });

  it('should disable submit button, cause password and mail', async () => {
    await render(LoginComponent, options);

    const user = userEvent.setup();
    await user.type(ui.username.input(), 'notamailaddress');
    await user.type(ui.password.input(), 'password');
    await user.clear(ui.password.input());

    expect(ui.submitButton()).toBeDisabled();
  });
});

describe('When a valid username and a valid password is entered', () => {
  it('displays no error', async () => {
    await render(LoginComponent, options);

    const user = userEvent.setup();
    await user.type(ui.username.input(), 'bar@localhost');
    await user.type(ui.password.input(), 'validpassword');

    expect(ui.username.errorIcon()).not.toBeInTheDocument();
    expect(ui.username.errorMessage()).not.toBeInTheDocument();
    expect(ui.password.errorIcon()).not.toBeInTheDocument();
    expect(ui.password.errorMessage()).not.toBeInTheDocument();
  });

  it('enables submit button', async () => {
    await render(LoginComponent, options);

    const user = userEvent.setup();
    await user.type(ui.username.input(), 'bar@localhost');
    await user.type(ui.password.input(), 'validpassword');

    expect(ui.submitButton()).not.toBeDisabled();
  });

  describe('When button is clicked', async () => {
    it('should disable submit button while call is pending', async () => {
      await render(LoginComponent, options);

      const httpAuthService = TestBed.inject(HttpAuthService);
      ngMocks.stub(httpAuthService, {
        login: jasmine.createSpy().and.returnValue(EMPTY),
      });

      const user = userEvent.setup();
      await user.type(ui.username.input(), 'bar@localhost');
      await user.type(ui.password.input(), 'validpassword');

      await user.click(ui.submitButton());

      expect(ui.submitButton()).toBeDisabled();
    });

    it('should route on successful login', async () => {
      await render(LoginComponent, options);

      const httpAuthService = TestBed.inject(HttpAuthService);
      ngMocks.stub(httpAuthService, {
        login: jasmine.createSpy().and.returnValue(of(void 0)),
      });

      const router = TestBed.inject(Router);
      ngMocks.stub(router, {
        navigateByUrl: jasmine.createSpy().and.callFake(() => {}),
      });

      const user = userEvent.setup();
      await user.type(ui.username.input(), 'bar@localhost');
      await user.type(ui.password.input(), 'validpassword');

      await user.click(ui.submitButton());

      expect(router.navigateByUrl).toHaveBeenCalledWith('');
    });

    it('should remove any message on successful login', async () => {
      await render(LoginComponent, options);

      const httpAuthService = TestBed.inject(HttpAuthService);
      ngMocks.stub(httpAuthService, {
        login: jasmine.createSpy().and.returnValue(of(void 0)),
      });
      const messageService = TestBed.inject(MessageService);
      ngMocks.stub(messageService, {
        clear: jasmine.createSpy().and.callFake(() => {}),
      });

      const user = userEvent.setup();
      await user.type(ui.username.input(), 'bar@localhost');
      await user.type(ui.password.input(), 'validpassword');

      await user.click(ui.submitButton());

      expect(messageService.clear).toHaveBeenCalledWith();
    });

    it('should post error message on failed login', async () => {
      await render(LoginComponent, options);

      const httpAuthService = TestBed.inject(HttpAuthService);
      ngMocks.stub(httpAuthService, {
        login: jasmine.createSpy().and.returnValue(throwError(() => new Error())),
      });
      const messageService = TestBed.inject(MessageService);
      ngMocks.stub(messageService, {
        add: jasmine.createSpy().and.callFake(() => {}),
      });

      const user = userEvent.setup();
      await user.type(ui.username.input(), 'bar@localhost');
      await user.type(ui.password.input(), 'validpassword');

      await user.click(ui.submitButton());

      expect(messageService.add).toHaveBeenCalledWith(jasmine.objectContaining({
        severity: 'error',
      }));
    });

    it('should enable submit button on failed login', async () => {
      await render(LoginComponent, options);

      const httpAuthService = TestBed.inject(HttpAuthService);
      ngMocks.stub(httpAuthService, {
        login: jasmine.createSpy().and.returnValue(throwError(() => new Error())),
      });

      const user = userEvent.setup();
      await user.type(ui.username.input(), 'bar@localhost');
      await user.type(ui.password.input(), 'validpassword');

      await user.click(ui.submitButton());

      expect(ui.submitButton()).not.toBeDisabled();
    });
  });
});
