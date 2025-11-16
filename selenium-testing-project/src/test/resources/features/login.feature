Feature: User Authentication
  As a user of the Task Manager application
  I want to be able to login
  So that I can access my tasks and manage them

  Background:
    Given I am on the login page

  @smoke @login
  Scenario: Successful login with valid credentials
    When I enter username "admin" and password "admin123"
    And I click the login button
    Then I should be redirected to the dashboard page
    And I should see the welcome message "Welcome, admin!"
