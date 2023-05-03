import { Given, When } from '@badeball/cypress-cucumber-preprocessor';

When('I click on \'Anmelden\'', () => {
  cy.findByTestId('link-to-login').click();
});

Given('I register with firstname {string} and lastname {string} and email {string}',
  (firstname: string, lastname: string, email: string) => {
    cy.findByTestId('registration-firstname').type(firstname);
    cy.findByTestId('registration-lastname').type(lastname);
    cy.findByTestId('registration-mail').type(email);
    cy.findByRole('button').click();
  });
