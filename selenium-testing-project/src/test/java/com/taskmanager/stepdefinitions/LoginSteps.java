package com.taskmanager.stepdefinitions;

import com.taskmanager.hooks.Hooks;
import com.taskmanager.pages.DashboardPage;
import com.taskmanager.pages.LoginPage;
import com.taskmanager.utils.ConfigReader;
import com.taskmanager.utils.WaitHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * LoginSteps - Step Definitions for Login Scenarios
 * Steps are organized by scenario functionality
 */
public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private WaitHelper waitHelper;

    public LoginSteps() {
        this.driver = Hooks.getDriver();
        this.loginPage = new LoginPage(driver);
        this.waitHelper = new WaitHelper(driver);
    }

    // ==================== SCENARIO: Successful login with valid credentials ====================

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        driver.get(ConfigReader.getBaseUrl());
        Assert.assertTrue(loginPage.isOnLoginPage(), "User is not on login page");
    }

    @When("I enter username {string} and password {string}")
    public void i_enter_username_and_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("I click the login button")
    public void i_click_the_login_button() {
        loginPage.clickLoginButton();
        waitHelper.waitForPageLoad();
    }

    @Then("I should be redirected to the dashboard page")
    public void i_should_be_redirected_to_the_dashboard_page() {
        dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(dashboardPage.isOnDashboardPage(),
                "User was not redirected to dashboard page");
    }

    @Then("I should see the welcome message {string}")
    public void i_should_see_the_welcome_message(String expectedMessage) {
        dashboardPage = new DashboardPage(driver);
        String actualMessage = dashboardPage.getWelcomeMessage();
        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Expected message: " + expectedMessage + " but got: " + actualMessage);
    }

    // ==================== SHARED STEP: Login (used in Background) ====================

    @Given("I am logged in as {string} with password {string}")
    public void i_am_logged_in_as_with_password(String username, String password) {
        driver.get(ConfigReader.getBaseUrl());
        loginPage = new LoginPage(driver);
        dashboardPage = loginPage.login(username, password);
        Assert.assertTrue(dashboardPage.isOnDashboardPage(), "Login failed - not on dashboard");
    }
}
