package com.taskmanager.stepdefinitions;

import com.taskmanager.hooks.Hooks;
import com.taskmanager.pages.DashboardPage;
import com.taskmanager.pages.LoginPage;
import com.taskmanager.pages.RegisterPage;
import com.taskmanager.utils.ConfigReader;
import com.taskmanager.utils.WaitHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * LoginSteps - Step Definitions for Login Feature
 * Maps Gherkin steps to page object methods
 */
public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private DashboardPage dashboardPage;
    private WaitHelper waitHelper;

    public LoginSteps() {
        this.driver = Hooks.getDriver();
        this.loginPage = new LoginPage(driver);
        this.waitHelper = new WaitHelper(driver);
    }

    // ==================== GIVEN STEPS ====================

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        driver.get(ConfigReader.getBaseUrl());
        Assert.assertTrue(loginPage.isOnLoginPage(), "User is not on login page");
    }

    @Given("I am on the registration page")
    public void i_am_on_the_registration_page() {
        driver.get(ConfigReader.getRegisterUrl());
        registerPage = new RegisterPage(driver);
        Assert.assertTrue(registerPage.isOnRegisterPage(), "User is not on registration page");
    }

    @Given("I am logged in as {string} with password {string}")
    public void i_am_logged_in_as_with_password(String username, String password) {
        driver.get(ConfigReader.getBaseUrl());
        loginPage = new LoginPage(driver);
        dashboardPage = loginPage.login(username, password);
        Assert.assertTrue(dashboardPage.isOnDashboardPage(), "Login failed - not on dashboard");
    }

    // ==================== WHEN STEPS ====================

    @When("I enter username {string} and password {string}")
    public void i_enter_username_and_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("I click the login button")
    public void i_click_the_login_button() {
        loginPage.clickLoginButton();
        // Wait for page transition
        waitHelper.waitForPageLoad();
    }

    @When("I check the Remember Me checkbox")
    public void i_check_the_remember_me_checkbox() {
        loginPage.setRememberMe(true);
    }

    @When("I enter full name {string}")
    public void i_enter_full_name(String fullName) {
        registerPage.enterFullName(fullName);
    }

    @When("I enter email {string}")
    public void i_enter_email(String email) {
        registerPage.enterEmail(email);
    }

    @When("I enter registration username {string}")
    public void i_enter_registration_username(String username) {
        registerPage.enterUsername(username);
    }

    @When("I enter registration password {string}")
    public void i_enter_registration_password(String password) {
        registerPage.enterPassword(password);
    }

    @When("I enter confirmation password {string}")
    public void i_enter_confirmation_password(String confirmPassword) {
        registerPage.enterConfirmPassword(confirmPassword);
    }

    @When("I click the register button")
    public void i_click_the_register_button() {
        registerPage.clickRegisterButton();
        waitHelper.waitForPageLoad();
    }

    @When("I click the logout button")
    public void i_click_the_logout_button() {
        dashboardPage = new DashboardPage(driver);
        dashboardPage.logout();
    }

    @When("I confirm the logout")
    public void i_confirm_the_logout() {
        // Logout confirmation is handled in the logout method
        waitHelper.waitForPageLoad();
    }

    // ==================== THEN STEPS ====================

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

    @Then("I should see an error message {string}")
    public void i_should_see_an_error_message(String expectedError) {
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message is not displayed");
        String actualError = loginPage.getErrorMessage();
        Assert.assertTrue(actualError.contains(expectedError),
                "Expected error: " + expectedError + " but got: " + actualError);
    }

    @Then("I should remain on the login page")
    public void i_should_remain_on_the_login_page() {
        Assert.assertTrue(loginPage.isOnLoginPage(), "User is not on login page");
    }

    @Then("I should see username field error {string}")
    public void i_should_see_username_field_error(String expectedError) {
        String actualError = loginPage.getUsernameError();
        Assert.assertTrue(actualError.contains(expectedError),
                "Expected error: " + expectedError + " but got: " + actualError);
    }

    @Then("I should see password field error {string}")
    public void i_should_see_password_field_error(String expectedError) {
        String actualError = loginPage.getPasswordError();
        Assert.assertTrue(actualError.contains(expectedError),
                "Expected error: " + expectedError + " but got: " + actualError);
    }

    @Then("I should see result {string}")
    public void i_should_see_result(String expectedResult) {
        if (expectedResult.equals("success")) {
            dashboardPage = new DashboardPage(driver);
            Assert.assertTrue(dashboardPage.isOnDashboardPage(),
                    "Login was not successful");
        } else {
            Assert.assertTrue(loginPage.isOnLoginPage() || loginPage.isErrorDisplayed(),
                    "Expected login to fail");
        }
    }

    @Then("I should be redirected to the login page")
    public void i_should_be_redirected_to_the_login_page() {
        loginPage = new LoginPage(driver);
        waitHelper.waitForPageLoad();
        Assert.assertTrue(loginPage.isOnLoginPage(),
                "User was not redirected to login page");
    }

    @Then("I should see logout notification {string}")
    public void i_should_see_logout_notification(String notification) {
        // Toast notification check
        String toastText = waitHelper.waitForToastNotification();
        Assert.assertTrue(toastText.contains(notification),
                "Expected notification: " + notification + " but got: " + toastText);
    }
}
