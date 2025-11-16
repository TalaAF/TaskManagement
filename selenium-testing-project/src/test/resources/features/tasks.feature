Feature: Task Management
  As a logged-in user
  I want to manage my tasks
  So that I can track my work and stay organized

  Background:
    Given I am logged in as "admin" with password "admin123"
    And I am on the tasks page

  @smoke @tasks
  Scenario: Create a new task successfully
    When I click the add task button
    And I fill the task form with:
      | field       | value                  |
      | title       | Complete Project Report|
      | description | Finish Q4 report by end of week |
      | priority    | High                   |
      | dueDate     | 2025-12-31            |
      | category    | Work                   |
    And I click save task button
    Then I should see success notification "Task created successfully!"
    And the task "Complete Project Report" should appear in the task list

  @smoke @tasks
  Scenario: Search tasks by title
    Given multiple tasks exist in the system
    When I enter "Project" in the search box
    Then only tasks containing "Project" should be displayed
