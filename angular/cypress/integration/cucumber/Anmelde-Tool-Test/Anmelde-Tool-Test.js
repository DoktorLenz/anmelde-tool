/// <reference types="cypress" />
import {Given, And, Then } from "cypress-cucumber-preprocessor/steps"

Given('I have a clean browser', () => {
  cy.clearLocalStorage();
})

Given('I visit the site', () => {
  cy.visit('http://localhost:4200');
})

Given('I login with email {string} and password {string}', (email, password) => {
  cy.get('[data-cy="login-username"]').type(email);
  cy.get('[data-cy="login-password"]').type(password);
  cy.get('[data-cy="login-submit-button"]').click();
})

Then('I should be on the main page', () => {
  cy.url().should('equal', 'http://localhost:4200/')
})
