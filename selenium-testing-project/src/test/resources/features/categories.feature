Feature: Category Management
  As a logged-in user
  I want to manage task categories
  So that I can organize my tasks effectively

  Background:
    Given I am logged in as "admin" with password "admin123"
    And I am on the categories page

  @smoke @categories @create
  Scenario: Create a new category successfully
    When I click the add category button
    And I enter category name "Urgent"
    And I select category color "#ff0000"
    And I click save category button
    Then I should see success notification "Category created successfully!"
    And the category "Urgent" should appear in the category list

  @categories @create @negative
  Scenario: Create category with duplicate name
    Given a category "Work" already exists
    When I click the add category button
    And I enter category name "Work"
    And I select category color "#0000ff"
    And I click save category button
    Then I should see error "Category name already exists"

  @categories @edit
  Scenario: Edit an existing category
    Given a category "Personal" exists
    When I click edit button for category "Personal"
    And I change category name to "Personal Tasks"
    And I change category color to "#00ff00"
    And I click save category button
    Then I should see success notification "Category updated successfully!"
    And the category "Personal Tasks" should appear in the category list

  @categories @edit
  Scenario: Edit category name updates associated tasks
    Given a category "Shopping" exists with 3 tasks
    When I edit the category name from "Shopping" to "Grocery"
    And I save the category
    Then all tasks should now be in "Grocery" category

  @categories @delete
  Scenario: Delete a category with confirmation
    Given a category "Test Category" exists
    When I click delete button for category "Test Category"
    And I should see delete confirmation modal
    And I confirm the deletion
    Then I should see success notification "Category deleted successfully!"
    And the category "Test Category" should not be in the list

  @categories @delete @negative
  Scenario: Cancel category deletion
    Given a category "Important Category" exists
    When I click delete button for category "Important Category"
    And I should see delete confirmation modal
    And I cancel the deletion
    Then the category "Important Category" should still be in the list

  @categories @delete
  Scenario: Delete category reassigns tasks to Uncategorized
    Given a category "OldCategory" exists with 5 tasks
    When I delete the category "OldCategory"
    And I confirm the deletion
    Then all 5 tasks should be reassigned to "Uncategorized"

  @categories @validation
  Scenario: Create category with empty name
    When I click the add category button
    And I click save category button without entering name
    Then I should see validation error "Category name is required"

  @categories @validation
  Scenario: Create category with name too short
    When I click the add category button
    And I enter category name "A"
    And I click save category button
    Then I should see error "Name must be at least 2 characters"

  @categories @display
  Scenario: View all categories
    Given multiple categories exist
    When I am on the categories page
    Then I should see all categories displayed in a grid
    And each category should show its name and color

  @categories @color
  Scenario: Category displays with correct color
    Given a category "ColorTest" with color "#9333ea" exists
    When I view the category list
    Then the category "ColorTest" should be displayed with color "#9333ea"

  @categories @combined
  Scenario: Complete category and task workflow
    When I create a new category "ProjectX" with color "#ff6600"
    Then the category "ProjectX" should appear in the list
    When I navigate to the tasks page
    And I create a task "ProjectX Task" with category "ProjectX"
    Then the task should be created in "ProjectX" category
    When I navigate back to categories
    And I view category "ProjectX"
    Then it should show 1 task count

  @categories @modal
  Scenario: Cancel category creation
    When I click the add category button
    And I enter category name "Cancelled Category"
    And I click cancel button
    Then the modal should close
    And the category "Cancelled Category" should not be created

  @categories @modal
  Scenario: Close category modal using X button
    When I click the add category button
    And I enter category name "Test"
    And I click the modal close button
    Then the modal should close
    And no category should be created
