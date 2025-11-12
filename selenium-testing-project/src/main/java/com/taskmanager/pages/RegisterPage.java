package com.taskmanager.pages;

import com.taskmanager.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * RegisterPage - Page Object Model for Registration Page
 */
public class RegisterPage extends BasePage {

    @FindBy(id = "fullName")
    private WebElement fullNameField;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "regUsername")
    private WebElement usernameField;

    @FindBy(id = "regPassword")
    private WebElement passwordField;

    @FindBy(id = "confirmPassword")
    private WebElement confirmPasswordField;

    @FindBy(id = "registerButton")
    private WebElement registerButton;

    @FindBy(id = "registerError")
    private WebElement registerErrorBox;

    @FindBy(css = "[data-testid='login-link']")
    private WebElement loginLink;

    public RegisterPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public RegisterPage enterFullName(String fullName) {
        sendKeys(fullNameField, fullName);
        return this;
    }

    public RegisterPage enterEmail(String email) {
        sendKeys(emailField, email);
        return this;
    }

    public RegisterPage enterUsername(String username) {
        sendKeys(usernameField, username);
        return this;
    }

    public RegisterPage enterPassword(String password) {
        sendKeys(passwordField, password);
        return this;
    }

    public RegisterPage enterConfirmPassword(String confirmPassword) {
        sendKeys(confirmPasswordField, confirmPassword);
        return this;
    }

    public void clickRegisterButton() {
        clickElement(registerButton);
        waitForLoadingToComplete();
    }

    public LoginPage register(String fullName, String email, String username, String password) {
        enterFullName(fullName);
        enterEmail(email);
        enterUsername(username);
        enterPassword(password);
        enterConfirmPassword(password);
        clickRegisterButton();
        waitHelper.hardWait(2000); // Wait for redirection
        return new LoginPage(driver);
    }

    public LoginPage clickLoginLink() {
        clickElement(loginLink);
        return new LoginPage(driver);
    }

    public boolean isOnRegisterPage() {
        return getCurrentUrl().contains("register.html");
    }
}
