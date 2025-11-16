Feature: Category Management
  As a logged-in user
  I want to manage task categories
  So that I can organize my tasks effectively

  Background:
    Given I am logged in as "admin" with password "admin123"
    And I am on the categories page

  @smoke @categories
  Scenario: Create a new category successfully
    When I click the add category button
    And I enter category name "Urgent"
    And I select category color "#ff0000"
    And I click save category button
    Then I should see success notification "Category created successfully!"
    And the category "Urgent" should appear in the category list
