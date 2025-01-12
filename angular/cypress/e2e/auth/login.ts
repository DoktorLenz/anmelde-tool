import { Given } from '@badeball/cypress-cucumber-preprocessor';

Given('I visit the page', () => {
  cy.visit('/');
});

Given(
  'I login with email {string} and password {string}',
  (username: string, password: string) => {
    cy.get('#loginName').type(username);
    cy.get('#submit-button').click();
    cy.get('#password').type(password);
    cy.get('#submit-button').click();
    cy.url().should('eq', 'http://localhost:4200/home');
  }
);
