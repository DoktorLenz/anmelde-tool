import { Given, Then, When } from '@badeball/cypress-cucumber-preprocessor';

Given('I login with email {string} and password {string}', (email: string, password: string) => {
  cy.get('[data-testid="login-username"]').type(email);
  cy.get('[data-testid="login-password"]').type(password);
  cy.findByRole('button').click();
});

Then('I should see a login error', () => {
  cy.findByTestId('login-form-error-message').contains('Falsche E-Mail Adresse oder falsches Passwort');
});


When('I click on \'Registrieren\'', () => {
  cy.findByTestId('link-to-registration').click();
});

