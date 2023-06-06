Feature: Login via OAuth
  The Login uses an OAuth provider. The e2e Docker-Compose contains Zitadel as an OAuth Provider

  Scenario: Logging in with admin
    Given I visit the page
    And I login with email "admin-andi@localhost" and password "Password1!"
