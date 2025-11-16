package com.taskmanager.stepdefinitions;

import com.taskmanager.hooks.Hooks;
import com.taskmanager.pages.TasksPage;
import com.taskmanager.utils.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

/**
 * TaskSteps - Step Definitions for Task Scenarios
 * Steps are organized by scenario functionality
 */
public class TaskSteps {

    private WebDriver driver;
    private TasksPage tasksPage;

    public TaskSteps() {
        this.driver = Hooks.getDriver();
    }

    // ==================== SCENARIO: Create a new task successfully ====================

    @Given("I am on the tasks page")
    public void i_am_on_the_tasks_page() {
        driver.get(ConfigReader.getTasksUrl());
        tasksPage = new TasksPage(driver);
        Assert.assertTrue(tasksPage.isOnTasksPage(), "User is not on tasks page");
    }

    @When("I click the add task button")
    public void i_click_the_add_task_button() {
        tasksPage.clickAddTask();
    }

    @When("I fill the task form with:")
    public void i_fill_the_task_form_with(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> taskData = data.get(0);

        String title = taskData.get("title");
        String description = taskData.get("description");
        String priority = taskData.get("priority");
        String dueDate = taskData.get("dueDate");
        String category = taskData.get("category");

        tasksPage.fillTaskForm(title, description, priority, dueDate, category);
    }

    @When("I click save task button")
    public void i_click_save_task_button() {
        tasksPage.saveTask();
    }

    @Then("the task {string} should appear in the task list")
    public void the_task_should_appear_in_the_task_list(String taskTitle) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(tasksPage.isTaskPresent(taskTitle),
                "Task '" + taskTitle + "' is not present in the task list");
    }

    // ==================== SCENARIO: Search tasks by title ====================

    @Given("multiple tasks exist in the system")
    public void multiple_tasks_exist_in_the_system() {
        int taskCount = tasksPage.getTaskCount();
        Assert.assertTrue(taskCount > 0, "No tasks found in the system");
    }

    @When("I enter {string} in the search box")
    public void i_enter_in_the_search_box(String searchTerm) {
        tasksPage.searchTasks(searchTerm);
    }

    @Then("only tasks containing {string} should be displayed")
    public void only_tasks_containing_should_be_displayed(String searchTerm) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(tasksPage.getTaskCount() > 0, "No tasks found after search");
    }
}
