import { Given, Then } from '@badeball/cypress-cucumber-preprocessor';

Given('I have a clean browser', () => {
  cy.clearLocalStorage();
});

Given('I visit the site', () => {
  cy.visit(Cypress.config().baseUrl ?? '/');
});

Then('I should be on the main page', () => {
  cy.url().should('eq', `${Cypress.config().baseUrl}/`);
});

Then('I should be on the registration page', () => {
  cy.url().should('eq', `${Cypress.config().baseUrl}/auth/registration`);
});
