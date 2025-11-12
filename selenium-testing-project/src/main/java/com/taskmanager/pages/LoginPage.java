package com.taskmanager.pages;

import com.taskmanager.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * LoginPage - Page Object Model for Login Page
 * Contains locators and methods for login functionality
 */
public class LoginPage extends BasePage {

    // Locators using @FindBy annotation
    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "rememberMe")
    private WebElement rememberMeCheckbox;

    @FindBy(id = "loginButton")
    private WebElement loginButton;

    @FindBy(id = "loginError")
    private WebElement loginErrorBox;

    @FindBy(css = "[data-testid='error-message']")
    private WebElement errorMessage;

    @FindBy(css = "[data-testid='register-link']")
    private WebElement registerLink;

    @FindBy(id = "usernameError")
    private WebElement usernameError;

    @FindBy(id = "passwordError")
    private WebElement passwordError;

    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Enter username
     */
    public LoginPage enterUsername(String username) {
        sendKeys(usernameField, username);
        return this;
    }

    /**
     * Enter password
     */
    public LoginPage enterPassword(String password) {
        sendKeys(passwordField, password);
        return this;
    }

    /**
     * Click login button
     */
    public void clickLoginButton() {
        clickElement(loginButton);
        waitForLoadingToComplete();
    }

    /**
     * Check/Uncheck Remember Me checkbox
     */
    public LoginPage setRememberMe(boolean check) {
        setCheckboxState(rememberMeCheckbox, check);
        return this;
    }

    /**
     * Complete login (username + password + click)
     */
    public DashboardPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        return new DashboardPage(driver);
    }

    /**
     * Login with remember me
     */
    public DashboardPage loginWithRememberMe(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        setRememberMe(true);
        clickLoginButton();
        return new DashboardPage(driver);
    }

    /**
     * Attempt login (for negative scenarios, returns LoginPage)
     */
    public LoginPage attemptLogin(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        return this;
    }

    /**
     * Check if error message is displayed
     */
    public boolean isErrorDisplayed() {
        waitHelper.hardWait(500); // Small wait for error to appear
        return isElementDisplayed(loginErrorBox);
    }

    /**
     * Get error message text
     */
    public String getErrorMessage() {
        waitHelper.waitForElementVisible(loginErrorBox);
        return getElementText(errorMessage);
    }

    /**
     * Get username field error
     */
    public String getUsernameError() {
        return getElementText(usernameError);
    }

    /**
     * Get password field error
     */
    public String getPasswordError() {
        return getElementText(passwordError);
    }

    /**
     * Check if username field has error class
     */
    public boolean hasUsernameError() {
        String classes = getAttributeValue(usernameField, "class");
        return classes != null && classes.contains("error");
    }

    /**
     * Check if password field has error class
     */
    public boolean hasPasswordError() {
        String classes = getAttributeValue(passwordField, "class");
        return classes != null && classes.contains("error");
    }

    /**
     * Click register link
     */
    public RegisterPage clickRegisterLink() {
        clickElement(registerLink);
        return new RegisterPage(driver);
    }

    /**
     * Verify user is on login page
     */
    public boolean isOnLoginPage() {
        return getCurrentUrl().contains("index.html") || getCurrentUrl().endsWith("/");
    }

    /**
     * Get page title
     */
    public String getLoginPageTitle() {
        return getPageTitle();
    }

    /**
     * Clear login form
     */
    public LoginPage clearLoginForm() {
        usernameField.clear();
        passwordField.clear();
        return this;
    }

    /**
     * Check if login button is enabled
     */
    public boolean isLoginButtonEnabled() {
        return isElementEnabled(loginButton);
    }

    /**
     * Check if Remember Me is checked
     */
    public boolean isRememberMeChecked() {
        return isElementSelected(rememberMeCheckbox);
    }
}
