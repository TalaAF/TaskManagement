package com.taskmanager.stepdefinitions;

import com.taskmanager.hooks.Hooks;
import com.taskmanager.utils.WaitHelper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * CommonSteps - Shared step definitions used across multiple features
 * Prevents duplicate step definition errors
 */
public class CommonSteps {

    private WebDriver driver;
    private WaitHelper waitHelper;

    public CommonSteps() {
        this.driver = Hooks.getDriver();
        this.waitHelper = new WaitHelper(driver);
    }

    // ==================== COMMON THEN STEPS ====================

    @Then("I should see success notification {string}")
    public void i_should_see_success_notification(String notification) {
        // Wait for and verify toast notification
        String toastText = waitHelper.waitForToastNotification();
        Assert.assertTrue(toastText.contains(notification),
                "Expected notification: " + notification + " but got: " + toastText);
    }

    @Then("I should see error {string}")
    public void i_should_see_error(String expectedError) {
        // Wait for and verify error notification
        String toastText = waitHelper.waitForToastNotification();
        Assert.assertTrue(toastText.contains(expectedError),
                "Expected error: " + expectedError + " but got: " + toastText);
    }

    @When("I should see delete confirmation modal")
    public void i_should_see_delete_confirmation_modal() {
        // Wait for delete confirmation modal to appear
        waitHelper.waitForModalVisible("deleteModal");
    }
}
