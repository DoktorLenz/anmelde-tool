import { TestBed } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { MockProviders, ngMocks } from 'ng-mocks';
import { MessageService } from 'primeng/api';
import { ReactiveFormsModule } from '@angular/forms';
import { RenderComponentOptions, fireEvent, render, screen } from '@testing-library/angular';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { HttpAuthService } from 'src/app/core/http/auth/http-auth.service';
import { of, throwError } from 'rxjs';
import { PipesModule } from 'src/app/lib/pipes/pipes.module';
import { Router } from '@angular/router';
import userEvent from '@testing-library/user-event';

const ui = {
  username: {
    input: () => screen.getByTestId('login-username'),
    validIcon: () => screen.queryByTestId('login-username-valid-icon'),
    errorMessage: () => screen.queryByTestId('login-username-error-message'),
  },
  password: {
    input: () => screen.getByTestId('login-password'),
  },
  formErrorMessage: () => screen.queryByTestId('login-form-error-message'),
  submitButton: () => screen.getByRole('button'),
  linkToRegistration: () => screen.getByTestId('link-to-registration'),
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

describe('When no username is entered but the field is dirty', () => {
  it('should display error "Erforderlich"', async () => {
    await render(LoginComponent, options);

    const user = userEvent.setup();
    await user.type(ui.username.input(), 'notamailaddress');
    await user.clear(ui.username.input());
    fireEvent.blur(ui.username.input());

    expect(ui.username.errorMessage()?.innerHTML).toEqual('Erforderlich');
  });
});

describe('When an invalid username is entered and the field is blured', () => {
  it('should display error "Ungültige E-Mail Adresse"', async () => {
    await render(LoginComponent, options);

    const user = userEvent.setup();
    await user.type(ui.username.input(), 'notamailaddress');
    fireEvent.blur(ui.username.input());

    expect(ui.username.errorMessage()?.innerHTML).toEqual('Ungültige E-Mail Adresse');
  });

  it('should not pass login details when clicking on submit', async () => {
    await render(LoginComponent, options);

    const httpAuthService = TestBed.inject(HttpAuthService);
    ngMocks.stub(httpAuthService, {
      login: jasmine.createSpy(),
    });

    const user = userEvent.setup();

    await user.type(ui.username.input(), 'notamailaddress');
    fireEvent.blur(ui.username.input());

    await user.click(ui.submitButton());

    expect(httpAuthService.login).not.toHaveBeenCalled();
  });

  describe('and a password is entered', () => {
    it('should still not pass login details when clicking on submit', async () => {
      await render(LoginComponent, options);

      const httpAuthService = TestBed.inject(HttpAuthService);
      ngMocks.stub(httpAuthService, {
        login: jasmine.createSpy(),
      });

      const user = userEvent.setup();

      await user.type(ui.username.input(), 'notamailaddress');
      fireEvent.blur(ui.username.input());

      await user.type(ui.password.input(), 'validpassword');
      fireEvent.blur(ui.username.input());

      await user.click(ui.submitButton());

      expect(httpAuthService.login).not.toHaveBeenCalled();
    });
  });
});

describe('Whena a valid username and no password are entered', () => {
  it('should display error after submit click', async () => {
    await render(LoginComponent, options);

    const httpAuthService = TestBed.inject(HttpAuthService);
    ngMocks.stub(httpAuthService, {
      login: jasmine.createSpy().and.returnValue(throwError(() => new Error())),
    });

    const user = userEvent.setup();

    await user.type(ui.username.input(), 'bar@localhost');
    fireEvent.blur(ui.username.input());

    expect(ui.formErrorMessage()).not.toBeInTheDocument();

    await user.click(ui.submitButton());

    expect(ui.formErrorMessage()?.innerHTML).toEqual('Falsche E-Mail Adresse oder falsches Passwort');
  });
});

describe('When a valid username and a password are entered', () => {
  it('should display error on failed login', async () => {
    await render(LoginComponent, options);

    const httpAuthService = TestBed.inject(HttpAuthService);
    ngMocks.stub(httpAuthService, {
      login: jasmine.createSpy().and.returnValue(throwError(() => new Error())),
    });

    const user = userEvent.setup();

    await user.type(ui.username.input(), 'bar@localhost');
    fireEvent.blur(ui.username.input());

    await user.type(ui.password.input(), 'invalidpassword');
    fireEvent.blur(ui.password.input());

    expect(ui.formErrorMessage()).not.toBeInTheDocument();

    await user.click(ui.submitButton());

    expect(ui.formErrorMessage()?.innerHTML).toEqual('Falsche E-Mail Adresse oder falsches Passwort');
  });

  it('should navigate on successful login', async () => {
    await render(LoginComponent, options);

    const httpAuthService = TestBed.inject(HttpAuthService);
    ngMocks.stub(httpAuthService, {
      login: jasmine.createSpy().and.returnValue(of(void 0)),
    });

    const router = TestBed.inject(Router);
    ngMocks.stub(router, {
      navigateByUrl: jasmine.createSpy(),
    });

    const user = userEvent.setup();

    await user.type(ui.username.input(), 'bar@localhost');
    fireEvent.blur(ui.username.input());

    await user.type(ui.password.input(), 'validpassword');
    fireEvent.blur(ui.password.input());

    expect(ui.formErrorMessage()).not.toBeInTheDocument();

    await user.click(ui.submitButton());

    expect(router.navigateByUrl).toHaveBeenCalledWith('');
  });
});

describe('When clicked on "Registrieren"-Link', () => {
  it('should navigate to register form', async () => {
    await render(LoginComponent, options);

    expect(ui.linkToRegistration()).toHaveAttribute('href', '/auth/register');
  });
});
