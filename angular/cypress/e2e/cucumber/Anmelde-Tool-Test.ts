import { Given, Then } from '@badeball/cypress-cucumber-preprocessor';

Given('I have a clean browser', () => {
  cy.clearLocalStorage();
});

Given('I visit the site', () => {
  cy.visit(Cypress.config().baseUrl ?? '/');
});

Given('I visit the registration page', () => {
  cy.visit((Cypress.config().baseUrl ?? '/') + '/auth/registration');
});

Then('I should be on the main page', () => {
  cy.url().should('eq', `${Cypress.config().baseUrl}/`);
});

Then('I should be on the registration page', () => {
  cy.url().should('eq', `${Cypress.config().baseUrl}/auth/registration`);
});

Then('I should be on the login page', () => {
  cy.url().should('eq', `${Cypress.config().baseUrl}/auth/login`);
});

Then('I should be on the registration-sent page', () => {
  cy.url().should('eq', `${Cypress.config().baseUrl}/auth/registration-sent`);
});
