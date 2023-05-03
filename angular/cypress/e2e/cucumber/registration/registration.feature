Feature: Registration

  Scenario: Register new User
    Given I have a clean browser
    Given I have a clean mailhog server
    Given I visit the site
    Then I click on 'Registrieren'
    And I register with firstname "Peter" and lastname "Müller" and email "peter.mueller@gmail.com"
    Then I should be on the registration-sent page
    And I should receive a registration confirm mail on email "peter.mueller@gmail.com"
