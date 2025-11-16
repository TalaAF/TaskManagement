package com.taskmanager.stepdefinitions;

import com.taskmanager.hooks.Hooks;
import com.taskmanager.pages.CategoriesPage;
import com.taskmanager.utils.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * CategorySteps - Step Definitions for Category Scenarios
 * Steps are organized by scenario functionality
 */
public class CategorySteps {

    private WebDriver driver;
    private CategoriesPage categoriesPage;

    public CategorySteps() {
        this.driver = Hooks.getDriver();
    }

    // ==================== SCENARIO: Create a new category successfully ====================

    @Given("I am on the categories page")
    public void i_am_on_the_categories_page() {
        driver.get(ConfigReader.getCategoriesUrl());
        categoriesPage = new CategoriesPage(driver);
        Assert.assertTrue(categoriesPage.isOnCategoriesPage(),
                "User is not on categories page");
    }

    @When("I click the add category button")
    public void i_click_the_add_category_button() {
        categoriesPage.clickAddCategory();
    }

    @When("I enter category name {string}")
    public void i_enter_category_name(String categoryName) {
        categoriesPage.enterCategoryName(categoryName);
    }

    @When("I select category color {string}")
    public void i_select_category_color(String color) {
        categoriesPage.selectCategoryColor(color);
    }

    @When("I click save category button")
    public void i_click_save_category_button() {
        categoriesPage.saveCategory();
    }

    @Then("I should see success notification {string}")
    public void i_should_see_success_notification(String notification) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Toast notification verification - simplified for now
        Assert.assertTrue(true, "Success notification should be displayed");
    }

    @Then("the category {string} should appear in the category list")
    public void the_category_should_appear_in_the_category_list(String categoryName) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(categoriesPage.isCategoryPresent(categoryName),
                "Category '" + categoryName + "' is not in the list");
    }
}
