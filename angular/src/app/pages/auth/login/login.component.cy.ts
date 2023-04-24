import { MockProvider } from 'ng-mocks';
import { LoginComponent } from './login.component';
import { HttpAuthService } from 'src/app/core/http/auth/http-auth.service';
import { MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { PipesModule } from 'src/app/lib/pipes/pipes.module';
import { Subject } from 'rxjs';

describe('LoginComponent', () => {

  const loginResponse = new Subject<void>();

  function setup(): void {
    cy.mount(LoginComponent, {
      providers: [
        MockProvider(HttpAuthService, {
          login: () => loginResponse.asObservable(),
        }),
        MessageService,
      ],
      imports: [
        CardModule,
        InputTextModule,
        ButtonModule,
        ReactiveFormsModule,
        PipesModule,
      ],
    });
  }

  describe('Visuals', () => {
    beforeEach(() => {
      setup();
    });

    it('should have title with text "Anmelden"', () => {
      cy.get('.p-card-title').should('contain.text', 'Anmelden');
    });

    it('should have a button with text "Anmelden"', () => {
      cy.get('[data-cy="login-submit-button"] > .p-button').should('contain.text', 'Anmelden');
    });
  });

  describe('Invalid username is entered', () => {
    beforeEach(() => {
      setup();
      cy.get('[data-cy="login-username"]').type('notamailadress');
    });

    it('should indicate "invalid email" error', () => {
      cy.get('[data-cy="login-username-error-icon"]').should('be.visible');
      cy.get('[data-cy="login-username-error-message"]')
        .should('have.text', 'Gib eine gültige E-Mail Adresse ein');
    });

    it('should disable submit-button, cause invalid email', () => {
      cy.get('[data-cy="login-submit-button"] > .p-button').should('be.disabled');
    });
  });

  describe('Password is entered and then removed', () => {
    beforeEach(() => {
      setup();
      cy.get('[data-cy="login-password"]').type('a');
      cy.get('[data-cy="login-password"]').clear();
    });

    it('should indicate "required" error', () => {
      cy.get('[data-cy="login-password-error-icon"]').should('be.visible');
      cy.get('[data-cy="login-password-error-message"]')
        .should('have.text', 'Erforderlich');
    });

    it('should disable submit-button, cause no password', () => {
      cy.get('[data-cy="login-submit-button"] > .p-button').should('be.disabled');
    });
  });

  describe('Valid email and password are entered', () => {
    const email = 'bar@localhost';
    const password = 'validpassword';

    beforeEach(() => {
      setup();
      cy.get('[data-cy="login-username"]').type(email);
      cy.get('[data-cy="login-password"]').type(password);
    });

    it('should display no error', () => {
      cy.get('[data-cy="login-username-error-icon"]').should('not.exist');
      cy.get('[data-cy="login-username-error-message"]').should('not.exist');
      cy.get('[data-cy="login-password-error-icon"]').should('not.exist');
      cy.get('[data-cy="login-password-error-message"]').should('not.exist');
    });

    it('should enable submit-button', () => {
      cy.get('[data-cy="login-submit-button"] > .p-button').should('not.be.disabled');
    });

    describe('submit button is clicked', () => {
      beforeEach(() => {
        cy.get('[data-cy="login-submit-button"]').click();
      });

      it('should indicate loading after 500ms', () => {
        cy.get('[data-cy="login-submit-button"] .p-button .p-button-loading-icon').should('not.exist');
        cy.get('[data-cy="login-submit-button"] .p-button .p-button-loading-icon').should('be.visible');
      });

      it('should remove loading indicator on login success', () => {
        loginResponse.next();
        cy.get('[data-cy="login-submit-button"] .p-button .p-button-loading-icon').should('not.exist');
      });

      it('should remove loading indicator on login failed', () => {
        loginResponse.error('');
        cy.get('[data-cy="login-submit-button"] .p-button .p-button-loading-icon').should('not.exist');
      });
    });
  });
});
