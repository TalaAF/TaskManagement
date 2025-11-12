Feature: User Authentication
  As a user of the Task Manager application
  I want to be able to login and register
  So that I can access my tasks and manage them

  Background:
    Given I am on the login page

  @smoke @login @positive
  Scenario: Successful login with valid credentials
    When I enter username "admin" and password "admin123"
    And I click the login button
    Then I should be redirected to the dashboard page
    And I should see the welcome message "Welcome, admin!"

  @login @negative
  Scenario: Login with invalid username
    When I enter username "invaliduser" and password "admin123"
    And I click the login button
    Then I should see an error message "Invalid credentials"
    And I should remain on the login page

  @login @negative
  Scenario: Login with invalid password
    When I enter username "admin" and password "wrongpassword"
    And I click the login button
    Then I should see an error message "Invalid credentials"
    And I should remain on the login page

  @login @negative
  Scenario: Login with empty username
    When I enter username "" and password "admin123"
    And I click the login button
    Then I should see username field error "Username is required"
    And I should remain on the login page

  @login @negative
  Scenario: Login with empty password
    When I enter username "admin" and password ""
    And I click the login button
    Then I should see password field error "Password is required"
    And I should remain on the login page

  @login @negative
  Scenario: Login with both fields empty
    When I enter username "" and password ""
    And I click the login button
    Then I should see username field error "Username is required"
    And I should see password field error "Password is required"
    And I should remain on the login page

  @login @positive
  Scenario: Login with Remember Me checked
    When I enter username "admin" and password "admin123"
    And I check the Remember Me checkbox
    And I click the login button
    Then I should be redirected to the dashboard page
    And I should see the welcome message "Welcome, admin!"

  @login @positive @datadriven
  Scenario Outline: Login with multiple credentials from CSV
    When I enter username "<username>" and password "<password>"
    And I click the login button
    Then I should see result "<result>"

    Examples:
      | username | password   | result  |
      | admin    | admin123   | success |
      | invalid  | wrong      | failure |
      |          | admin123   | failure |
      | admin    |            | failure |

  @registration @positive
  Scenario: Successful user registration
    Given I am on the registration page
    When I enter full name "Test User"
    And I enter email "test@example.com"
    And I enter registration username "testuser"
    And I enter registration password "test123"
    And I enter confirmation password "test123"
    And I click the register button
    Then I should see success notification "Registration successful"
    And I should be redirected to the login page

  @registration @negative
  Scenario: Registration with mismatched passwords
    Given I am on the registration page
    When I enter full name "Test User"
    And I enter email "test@example.com"
    And I enter registration username "testuser"
    And I enter registration password "test123"
    And I enter confirmation password "differentpass"
    And I click the register button
    Then I should see error "Passwords do not match"

  @logout @positive
  Scenario: Successful logout
    Given I am logged in as "admin" with password "admin123"
    When I click the logout button
    And I confirm the logout
    Then I should be redirected to the login page
    And I should see logout notification "Logged out successfully"
