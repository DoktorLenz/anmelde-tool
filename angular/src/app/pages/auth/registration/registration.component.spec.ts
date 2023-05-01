import { ComponentFixture, TestBed, async } from '@angular/core/testing';

import { RegistrationComponent } from './registration.component';
import { MockProviders, ngMocks } from 'ng-mocks';
import { HttpAuthService } from 'src/app/core/http/auth/http-auth.service';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { RenderComponentOptions, render, screen } from '@testing-library/angular';
import userEvent from '@testing-library/user-event';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { PipesModule } from 'src/app/lib/pipes/pipes.module';
import { ReactiveFormsModule } from '@angular/forms';
import { EMPTY, of, throwError } from 'rxjs';
import { BaseRoute } from 'src/app/lib/routes/base-route';
import { AuthRoute } from 'src/app/lib/routes/auth-route';

const ui = {
  firstname: {
    input: () => screen.getByTestId('registration-firstname'),
    errorIcon: () => screen.queryByTestId('registration-firstname-error-icon'),
    errorMessage: () => screen.queryByTestId('registration-firstname-error-message'),
  },
  lastname: {
    input: () => screen.getByTestId('registration-lastname'),
    errorIcon: () => screen.queryByTestId('registration-lastname-error-icon'),
    errorMessage: () => screen.queryByTestId('registration-lastname-error-message'),
  },
  mail: {
    input: () => screen.getByTestId('registration-mail'),
    errorIcon: () => screen.queryByTestId('registration-mail-error-icon'),
    errorMessage: () => screen.queryByTestId('registration-mail-error-message'),
  },
  submitButton: () => screen.getByRole('button'),
};

const options: RenderComponentOptions<RegistrationComponent> = {
  providers: [
    MockProviders(MessageService, HttpAuthService, Router),
  ],
  imports: [
    CardModule, ButtonModule, InputTextModule, PipesModule, ReactiveFormsModule,
  ],
};

describe('When a firstname is entered and then removed', () => {
  it('should display "required" error on firstname', async () => {
    await render(RegistrationComponent, options);

    const user = userEvent.setup();
    await user.type(ui.firstname.input(), 'firstname');
    await user.clear(ui.firstname.input());

    expect(ui.firstname.errorIcon()).toBeInTheDocument();
    expect(ui.firstname.errorMessage()).toBeInTheDocument();
    expect(ui.firstname.errorMessage()?.innerHTML).toEqual('Erforderlich');
  });

  it('should disable submit button, cause firstname', async () => {
    await render(RegistrationComponent, options);

    const user = userEvent.setup();
    await user.type(ui.firstname.input(), 'firstname');
    await user.clear(ui.firstname.input());

    expect(ui.submitButton()).toBeDisabled();
  });
});

describe('When a lastname is entered and then removed', () => {
  it('should display "required" error on lastname', async () => {
    await render(RegistrationComponent, options);

    const user = userEvent.setup();
    await user.type(ui.lastname.input(), 'lastname');
    await user.clear(ui.lastname.input());

    expect(ui.lastname.errorIcon()).toBeInTheDocument();
    expect(ui.lastname.errorMessage()).toBeInTheDocument();
    expect(ui.lastname.errorMessage()?.innerHTML).toEqual('Erforderlich');
  });

  it('should disable submit button, cause lastname', async () => {
    await render(RegistrationComponent, options);

    const user = userEvent.setup();
    await user.type(ui.lastname.input(), 'lastname');
    await user.clear(ui.lastname.input());

    expect(ui.submitButton()).toBeDisabled();
  });
});

describe('When an invalid mail is entered', () => {
  it('should display "Invalid Mail" error', async () => {
    await render(RegistrationComponent, options);

    const user = userEvent.setup();
    await user.type(ui.mail.input(), 'notamailaddress');

    expect(ui.mail.errorIcon()).toBeInTheDocument();
    expect(ui.mail.errorMessage()).toBeInTheDocument();
    expect(ui.mail.errorMessage()?.innerHTML).toEqual('Ungültige E-Mail Adresse');
  });

  it('should disable submit button, cause mail', async () => {
    await render(RegistrationComponent, options);

    const user = userEvent.setup();
    await user.type(ui.mail.input(), 'notamailaddress');

    expect(ui.submitButton()).toBeDisabled();
  });
});

describe('When a firstname and lastname are entered and then removed and an invalid mail is entered', () => {
  it('should display all errors', async () => {
    await render(RegistrationComponent, options);

    const user = userEvent.setup();
    await user.type(ui.firstname.input(), 'firstname');
    await user.clear(ui.firstname.input());
    await user.type(ui.lastname.input(), 'lastname');
    await user.clear(ui.lastname.input());
    await user.type(ui.mail.input(), 'notamailaddress');

    expect(ui.firstname.errorIcon()).toBeInTheDocument();
    expect(ui.firstname.errorMessage()).toBeInTheDocument();
    expect(ui.firstname.errorMessage()?.innerHTML).toEqual('Erforderlich');

    expect(ui.lastname.errorIcon()).toBeInTheDocument();
    expect(ui.lastname.errorMessage()).toBeInTheDocument();
    expect(ui.lastname.errorMessage()?.innerHTML).toEqual('Erforderlich');

    expect(ui.mail.errorIcon()).toBeInTheDocument();
    expect(ui.mail.errorMessage()).toBeInTheDocument();
    expect(ui.mail.errorMessage()?.innerHTML).toEqual('Ungültige E-Mail Adresse');
  });

  it('should disable submit button, cause firstname, lastname and mail', async () => {
    await render(RegistrationComponent, options);

    const user = userEvent.setup();
    await user.type(ui.firstname.input(), 'firstname');
    await user.clear(ui.firstname.input());
    await user.type(ui.lastname.input(), 'lastname');
    await user.clear(ui.lastname.input());
    await user.type(ui.mail.input(), 'notamailaddress');

    expect(ui.submitButton()).toBeDisabled();
  });
});

describe('When all registration inputs receive a valid input', () => {
  it('displays no error', async () => {
    await render(RegistrationComponent, options);

    const user = userEvent.setup();
    await user.type(ui.firstname.input(), 'firstname');
    await user.type(ui.lastname.input(), 'lastname');
    await user.type(ui.mail.input(), 'bar@localhost');

    expect(ui.firstname.errorIcon()).not.toBeInTheDocument();
    expect(ui.firstname.errorMessage()).not.toBeInTheDocument();

    expect(ui.lastname.errorIcon()).not.toBeInTheDocument();
    expect(ui.lastname.errorMessage()).not.toBeInTheDocument();

    expect(ui.mail.errorIcon()).not.toBeInTheDocument();
    expect(ui.mail.errorMessage()).not.toBeInTheDocument();
  });

  it('enables submit button', async () => {
    await render(RegistrationComponent, options);

    const user = userEvent.setup();
    await user.type(ui.firstname.input(), 'firstname');
    await user.type(ui.lastname.input(), 'lastname');
    await user.type(ui.mail.input(), 'bar@localhost');

    expect(ui.submitButton()).not.toBeDisabled();
  });

  describe('When button is clicked', () => {
    const firstname = 'firstname';
    const lastname = 'lastname';
    const mail = 'bar@localhost';

    it('should disable submit button while call is pending', async () => {
      await render(RegistrationComponent, options);

      const httpAuthService = TestBed.inject(HttpAuthService);
      ngMocks.stub(httpAuthService, {
        register: jasmine.createSpy().and.returnValue(EMPTY),
      });

      const user = userEvent.setup();
      await user.type(ui.firstname.input(), firstname);
      await user.type(ui.lastname.input(), lastname);
      await user.type(ui.mail.input(), mail);

      await user.click(ui.submitButton());

      expect(ui.submitButton()).toBeDisabled();
    });

    it('should pass registration values to service', async () => {
      await render(RegistrationComponent, options);

      const httpAuthService = TestBed.inject(HttpAuthService);
      ngMocks.stub(httpAuthService, {
        register: jasmine.createSpy().and.returnValue(EMPTY),
      });

      const user = userEvent.setup();
      await user.type(ui.firstname.input(), firstname);
      await user.type(ui.lastname.input(), lastname);
      await user.type(ui.mail.input(), mail);

      await user.click(ui.submitButton());

      expect(httpAuthService.register).toHaveBeenCalledWith({ firstname, lastname, email: mail });
    });

    it('should route on successful login', async () => {
      await render(RegistrationComponent, options);

      const httpAuthService = TestBed.inject(HttpAuthService);
      ngMocks.stub(httpAuthService, {
        register: jasmine.createSpy().and.returnValue(of(void 0)),
      });

      const router = TestBed.inject(Router);
      ngMocks.stub(router, {
        navigateByUrl: jasmine.createSpy().and.callFake(() => {}),
      });

      const user = userEvent.setup();
      await user.type(ui.firstname.input(), firstname);
      await user.type(ui.lastname.input(), lastname);
      await user.type(ui.mail.input(), mail);

      await user.click(ui.submitButton());

      expect(router.navigateByUrl).toHaveBeenCalledWith('/auth/registration-sent');
    });

    it('should remove any message on successful registration', async () => {
      await render(RegistrationComponent, options);

      const httpAuthService = TestBed.inject(HttpAuthService);
      ngMocks.stub(httpAuthService, {
        register: jasmine.createSpy().and.returnValue(of(void 0)),
      });

      const messageService = TestBed.inject(MessageService);
      ngMocks.stub(messageService, {
        clear: jasmine.createSpy().and.callFake(() => {}),
      });

      const user = userEvent.setup();
      await user.type(ui.firstname.input(), firstname);
      await user.type(ui.lastname.input(), lastname);
      await user.type(ui.mail.input(), mail);

      await user.click(ui.submitButton());

      expect(messageService.clear).toHaveBeenCalledWith();
    });

    it('should post error message on failed registration', async () => {
      await render(RegistrationComponent, options);

      const httpAuthService = TestBed.inject(HttpAuthService);
      ngMocks.stub(httpAuthService, {
        register: jasmine.createSpy().and.returnValue(throwError(() => new Error())),
      });

      const messageService = TestBed.inject(MessageService);
      ngMocks.stub(messageService, {
        add: jasmine.createSpy().and.callFake(() => {}),
      });

      const user = userEvent.setup();
      await user.type(ui.firstname.input(), firstname);
      await user.type(ui.lastname.input(), lastname);
      await user.type(ui.mail.input(), mail);

      await user.click(ui.submitButton());

      expect(messageService.add).toHaveBeenCalledWith(jasmine.objectContaining({
        severity: 'error',
      }));
    });

    it('should enable submit button on failed registration', async () => {
      await render(RegistrationComponent, options);

      const httpAuthService = TestBed.inject(HttpAuthService);
      ngMocks.stub(httpAuthService, {
        register: jasmine.createSpy().and.returnValue(throwError(() => new Error())),
      });

      const user = userEvent.setup();
      await user.type(ui.firstname.input(), firstname);
      await user.type(ui.lastname.input(), lastname);
      await user.type(ui.mail.input(), mail);

      await user.click(ui.submitButton());

      expect(ui.submitButton()).not.toBeDisabled();
    });
  });
});
