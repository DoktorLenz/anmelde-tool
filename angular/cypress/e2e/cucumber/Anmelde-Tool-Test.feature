Feature: Visite the page without login

  Scenario: Visite the page with clean browser
    Given I have a clean browser
    Given I visit the site
    Then I should be on the login page
