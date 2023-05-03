Feature: Login

  Scenario: Login with propper credentials
    Given I have a clean browser
    Given I visit the site
    And I login with email "bar@localhost" and password "validpassword"
    Then I should be on the main page

  Scenario: Login with false credentials
    Given I have a clean browser
    Given I visit the site
    And I login with email "unknown@mail" and password "somepassword"
    Then I should see a login error

  Scenario: From Login to Registration
    Given I have a clean browser
    Given I visit the site
    When I click on 'Registrieren'
    Then I should be on the registration page
