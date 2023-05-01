import { Given } from '@badeball/cypress-cucumber-preprocessor';

Given('I login with email {string} and password {string}', (email: string, password: string) => {
  cy.get('[data-testid="login-username"]').type(email);
  cy.get('[data-testid="login-password"]').type(password);
  cy.findByRole('button').click();
});
