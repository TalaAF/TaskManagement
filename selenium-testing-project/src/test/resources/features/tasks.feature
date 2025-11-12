Feature: Task Management
  As a logged-in user
  I want to manage my tasks
  So that I can track my work and stay organized

  Background:
    Given I am logged in as "admin" with password "admin123"
    And I am on the tasks page

  @smoke @tasks @create
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

  @tasks @edit
  Scenario: Edit an existing task
    Given a task "Sample Task" exists with priority "Low"
    When I click edit button for task "Sample Task"
    And I change task title to "Updated Sample Task"
    And I change task priority to "High"
    And I click save task button
    Then I should see success notification "Task updated successfully!"
    And the task "Updated Sample Task" should have priority "High"

  @tasks @delete
  Scenario: Delete a task with confirmation
    Given a task "Task to Delete" exists
    When I click delete button for task "Task to Delete"
    And I should see delete confirmation modal
    And I click confirm delete button
    Then I should see success notification "Task deleted successfully!"
    And the task "Task to Delete" should not be in the task list

  @tasks @delete @negative
  Scenario: Cancel task deletion
    Given a task "Important Task" exists
    When I click delete button for task "Important Task"
    And I should see delete confirmation modal
    And I click cancel delete button
    Then the task "Important Task" should still be in the task list

  @tasks @status
  Scenario: Mark task as complete
    Given a task "Incomplete Task" exists with status "Incomplete"
    When I click the status toggle for task "Incomplete Task"
    Then the task status should change to "Complete"
    And I should see success notification "Task marked as complete!"

  @tasks @status
  Scenario: Mark task as incomplete
    Given a task "Completed Task" exists with status "Complete"
    When I click the status toggle for task "Completed Task"
    Then the task status should change to "Incomplete"
    And I should see success notification "Task marked as incomplete!"

  @tasks @search @smoke
  Scenario: Search tasks by title
    Given multiple tasks exist in the system
    When I enter "Project" in the search box
    Then only tasks containing "Project" should be displayed

  @tasks @search
  Scenario: Search with no results
    When I enter "NonExistentTask" in the search box
    Then I should see message "No tasks found"

  @tasks @filter
  Scenario: Filter tasks by priority - High
    Given tasks exist with different priorities
    When I select "High" from the priority filter
    Then only tasks with "High" priority should be displayed

  @tasks @filter
  Scenario: Filter tasks by priority - Medium
    Given tasks exist with different priorities
    When I select "Medium" from the priority filter
    Then only tasks with "Medium" priority should be displayed

  @tasks @filter
  Scenario: Filter tasks by priority - Low
    Given tasks exist with different priorities
    When I select "Low" from the priority filter
    Then only tasks with "Low" priority should be displayed

  @tasks @filter
  Scenario: Filter tasks by status - Complete
    Given tasks exist with different statuses
    When I select "Complete" from the status filter
    Then only tasks with "Complete" status should be displayed

  @tasks @filter
  Scenario: Filter tasks by status - Incomplete
    Given tasks exist with different statuses
    When I select "Incomplete" from the status filter
    Then only tasks with "Incomplete" status should be displayed

  @tasks @filter
  Scenario: Filter tasks by category
    Given tasks exist in different categories
    When I select "Work" from the category filter
    Then only tasks in "Work" category should be displayed

  @tasks @filter
  Scenario: Clear all filters
    Given I have applied multiple filters
    When I click the clear filters button
    Then all tasks should be displayed

  @tasks @pagination
  Scenario: Navigate to next page
    Given more than 10 tasks exist
    When I click the next page button
    Then I should see page 2 of tasks

  @tasks @pagination
  Scenario: Navigate to previous page
    Given I am on page 2 of tasks
    When I click the previous page button
    Then I should see page 1 of tasks

  @tasks @pagination
  Scenario: Navigate to specific page
    Given more than 20 tasks exist
    When I click on page number 3
    Then I should see page 3 of tasks

  @tasks @validation
  Scenario: Create task with missing required fields
    When I click the add task button
    And I click save task button without filling the form
    Then I should see validation errors for required fields

  @tasks @validation
  Scenario: Create task with title too short
    When I click the add task button
    And I enter task title "AB"
    And I click save task button
    Then I should see error "Title must be at least 3 characters"

  @tasks @datadriven
  Scenario Outline: Create multiple tasks from Excel data
    When I create a task with data from Excel row <row>
    Then the task should be created successfully

    Examples:
      | row |
      | 1   |
      | 2   |
      | 3   |

  @tasks @combined
  Scenario: Complete workflow - Create, Edit, Complete, and Delete task
    When I create a new task "Workflow Test Task"
    Then the task "Workflow Test Task" should appear in the task list
    When I edit the task "Workflow Test Task" and change priority to "High"
    Then the task should have priority "High"
    When I mark the task "Workflow Test Task" as complete
    Then the task should have status "Complete"
    When I delete the task "Workflow Test Task"
    Then the task should not exist
