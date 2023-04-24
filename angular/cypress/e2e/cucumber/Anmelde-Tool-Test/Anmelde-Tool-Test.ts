import { Given, Then } from '@badeball/cypress-cucumber-preprocessor';

Given('I have a clean browser', () => {
  cy.clearLocalStorage();
});

Given('I visit the site', () => {
  cy.visit(Cypress.config().baseUrl ?? '/');
});

Given('I login with email {string} and password {string}', (email: string, password: string) => {
  cy.get('[data-cy="login-username"]').type(email);
  cy.get('[data-cy="login-password"]').type(password);
  cy.get('[data-cy="login-submit-button"]').click();
});

Then('I should be on the main page', () => {
  cy.url().should('eq', `${Cypress.config().baseUrl}/`);
});
