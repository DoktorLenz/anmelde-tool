Feature: Test

  Scenario: Login
    Given I have a clean browser
    Given I visit the site
    And I login with email "bar@localhost" and password "validpassword"
    Then I should be on the main page
