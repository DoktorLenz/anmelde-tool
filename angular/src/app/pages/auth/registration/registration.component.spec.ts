import { TestBed } from '@angular/core/testing';

import { RegistrationComponent } from './registration.component';
import { MockProviders, ngMocks } from 'ng-mocks';
import { HttpAuthService } from 'src/app/core/http/auth/http-auth.service';
import { Router } from '@angular/router';
import { Message, MessageService } from 'primeng/api';
import { RenderComponentOptions, render, screen } from '@testing-library/angular';
import userEvent from '@testing-library/user-event';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { PipesModule } from 'src/app/lib/pipes/pipes.module';
import { ReactiveFormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';
import { BaseRoute } from 'src/app/lib/routes/base-route';
import { AuthRoute } from 'src/app/lib/routes/auth-route';

const ui = {
  firstname: {
    input: () => screen.getByTestId('registration-firstname'),
    validIcon: () => screen.queryByTestId('registration-firstname-valid-icon'),
    errorMessage: () => screen.queryByTestId('registration-firstname-error-message'),
  },
  lastname: {
    input: () => screen.getByTestId('registration-lastname'),
    validIcon: () => screen.queryByTestId('registration-lastname-valid-icon'),
    errorMessage: () => screen.queryByTestId('registration-lastname-error-message'),
  },
  mail: {
    input: () => screen.getByTestId('registration-mail'),
    validIcon: () => screen.queryByTestId('registration-mail-valid-icon'),
    errorMessage: () => screen.queryByTestId('registration-mail-error-message'),
  },
  submitButton: () => screen.getByRole('button'),
  linkToLogin: () => screen.getByTestId('link-to-login'),
};

const options: RenderComponentOptions<RegistrationComponent> = {
  providers: [
    MockProviders(MessageService, HttpAuthService, Router),
  ],
  imports: [
    CardModule, ButtonModule, InputTextModule, PipesModule, ReactiveFormsModule,
  ],
};

describe('When no firstname is entered but field is dirty', () => {
  it('should display error "Erforderlich" on firstname field', async () => {
    await render(RegistrationComponent, options);

    const user = userEvent.setup();
    await user.type(ui.firstname.input(), 'a');
    await user.clear(ui.firstname.input());

    expect(ui.firstname.errorMessage()?.innerHTML).toEqual('Erforderlich');
  });
});

describe('When no lastname is entered but field is dirty', () => {
  it('should display error "Erforderlich" on lastname field', async () => {
    await render(RegistrationComponent, options);

    const user = userEvent.setup();
    await user.type(ui.lastname.input(), 'a');
    await user.clear(ui.lastname.input());

    expect(ui.lastname.errorMessage()?.innerHTML).toEqual('Erforderlich');
  });
});

describe('When no mail is entered but field is dirty', () => {
  it('should display error "Erforderlich" on mail field', async () => {
    await render(RegistrationComponent, options);

    const user = userEvent.setup();
    await user.type(ui.mail.input(), 'a');
    await user.clear(ui.mail.input());

    expect(ui.mail.errorMessage()?.innerHTML).toEqual('Erforderlich');
  });
});

describe('When an invalid mail is entered', () => {
  it('should display error "Ungültige E-Mail Adresse" on mail field', async () => {
    await render(RegistrationComponent, options);

    const user = userEvent.setup();
    await user.type(ui.mail.input(), 'notamailaddress');

    expect(ui.mail.errorMessage()?.innerHTML).toEqual('Ungültige E-Mail Adresse');
  });
});

describe('When all fields receive a valid input', () => {
  it('should display check icon on each input', async () => {
    await render(RegistrationComponent, options);

    const user = userEvent.setup();
    await user.type(ui.firstname.input(), 'firstname');
    await user.type(ui.lastname.input(), 'lastname');
    await user.type(ui.mail.input(), 'bar@localhost');

    expect(ui.firstname.validIcon()).toBeInTheDocument();
    expect(ui.lastname.validIcon()).toBeInTheDocument();
    expect(ui.mail.validIcon()).toBeInTheDocument();
  });

  it('should enable submit button', async () => {
    await render(RegistrationComponent, options);

    const user = userEvent.setup();
    await user.type(ui.firstname.input(), 'firstname');
    await user.type(ui.lastname.input(), 'lastname');
    await user.type(ui.mail.input(), 'bar@localhost');

    expect(ui.submitButton()).not.toBeDisabled();
  });

  describe('and submit button is clicked', () => {
    it('should navigate to registration sent page on success', async () => {
      await render(RegistrationComponent, options);

      const httpAuthService = TestBed.inject(HttpAuthService);
      ngMocks.stub(httpAuthService, {
        register: jasmine.createSpy().and.returnValue(of(void 0)),
      });
      const router = TestBed.inject(Router);
      ngMocks.stub(router, {
        navigateByUrl: jasmine.createSpy(),
      });

      const user = userEvent.setup();
      await user.type(ui.firstname.input(), 'firstname');
      await user.type(ui.lastname.input(), 'lastname');
      await user.type(ui.mail.input(), 'bar@localhost');

      await user.click(ui.submitButton());

      expect(router.navigateByUrl).toHaveBeenCalledWith(`/${BaseRoute.AUTH}/${AuthRoute.REGISTRATION_SENT}`);
    });
  });

  it('should post an error message on failure', async () => {
    await render(RegistrationComponent, options);

    const httpAuthService = TestBed.inject(HttpAuthService);
    ngMocks.stub(httpAuthService, {
      register: jasmine.createSpy().and.returnValue(throwError(() => new Error())),
    });
    const messageService = TestBed.inject(MessageService);
    ngMocks.stub(messageService, {
      add: jasmine.createSpy(),
    });

    const user = userEvent.setup();
    await user.type(ui.firstname.input(), 'firstname');
    await user.type(ui.lastname.input(), 'lastname');
    await user.type(ui.mail.input(), 'bar@localhost');

    await user.click(ui.submitButton());

    expect(messageService.add).toHaveBeenCalledWith(jasmine.objectContaining<Message>({ severity: 'error' }));
  });
});

describe('"Anmelden"-Link', () => {
  it('should contain link to login form', async () => {
    await render(RegistrationComponent, options);

    expect(ui.linkToLogin()).toHaveAttribute('href', `/${BaseRoute.AUTH}/${AuthRoute.LOGIN}`);
  });
});
