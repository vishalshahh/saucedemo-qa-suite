Feature: SauceDemo Login Functionality

  Scenario: Successful login with valid credentials
    Given I am on the SauceDemo login page
    When I login with username "standard_user" and password "secret_sauce"
    Then I should be redirected to the inventory page

  Scenario: Login with invalid credentials
    Given I am on the SauceDemo login page
    When I login with username "invalid_user" and password "invalid_pass"
    Then I should see an error message

  Scenario: Login with locked out user
    Given I am on the SauceDemo login page
    When I login with username "locked_out_user" and password "secret_sauce"
    Then I should see a locked out error