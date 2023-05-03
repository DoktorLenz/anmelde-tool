import { Given, Then } from '@badeball/cypress-cucumber-preprocessor';

Given('I have a clean mailhog server', () => {
  cy.visit('http://localhost:38025');
  cy.get('a[ng-click="deleteAll()"]').click();
  cy.get('button[ng-click="deleteAllConfirm()"]').click();
});

Then('I should receive a registration confirm mail on email {string}', (email: string) => {
  cy.visit('http://localhost:38025');
  cy.findByText(email).should('be.visible');
  cy.findByText('Registrierung Abschließen').should('be.visible');
});
