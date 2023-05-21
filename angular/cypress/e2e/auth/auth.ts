import { Then, When } from '@badeball/cypress-cucumber-preprocessor';

When('I visit the Page', () => {
  cy.visit('http://localhost');
});

Then('I should see a login mask', () => {
  cy.url().should('include', 'http://localhost:8080/ui/login/login');
  cy.get('#loginName').should('be.visible');
});
