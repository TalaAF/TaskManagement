package com.taskmanager.base;

import com.taskmanager.utils.WaitHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * BasePage - Base class for all Page Object Model classes
 * Contains common methods used across all pages
 */
public class BasePage {

    protected WebDriver driver;
    protected WaitHelper waitHelper;
    protected Actions actions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
        this.actions = new Actions(driver);
    }

    /**
     * Click on element
     */
    protected void clickElement(WebElement element) {
        waitHelper.waitForElementClickable(element);
        element.click();
    }

    /**
     * Click on element by locator
     */
    protected void clickElement(By locator) {
        WebElement element = waitHelper.waitForElementClickable(locator);
        element.click();
    }

    /**
     * Enter text in input field
     */
    protected void sendKeys(WebElement element, String text) {
        waitHelper.waitForElementVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Enter text in input field by locator
     */
    protected void sendKeys(By locator, String text) {
        WebElement element = waitHelper.waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get text from element
     */
    protected String getElementText(WebElement element) {
        waitHelper.waitForElementVisible(element);
        return element.getText();
    }

    /**
     * Get text from element by locator
     */
    protected String getElementText(By locator) {
        WebElement element = waitHelper.waitForElementVisible(locator);
        return element.getText();
    }

    /**
     * Get attribute value from element
     */
    protected String getAttributeValue(WebElement element, String attribute) {
        waitHelper.waitForElementVisible(element);
        return element.getAttribute(attribute);
    }

    /**
     * Check if element is displayed
     */
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * Check if element is displayed by locator
     */
    protected boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Check if element is enabled
     */
    protected boolean isElementEnabled(WebElement element) {
        waitHelper.waitForElementVisible(element);
        return element.isEnabled();
    }

    /**
     * Check if element is selected (for checkboxes/radio buttons)
     */
    protected boolean isElementSelected(WebElement element) {
        waitHelper.waitForElementVisible(element);
        return element.isSelected();
    }

    /**
     * Select dropdown by visible text
     */
    protected void selectDropdownByVisibleText(WebElement element, String text) {
        waitHelper.waitForElementVisible(element);
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    /**
     * Select dropdown by value
     */
    protected void selectDropdownByValue(WebElement element, String value) {
        waitHelper.waitForElementVisible(element);
        Select select = new Select(element);
        select.selectByValue(value);
    }

    /**
     * Select dropdown by index
     */
    protected void selectDropdownByIndex(WebElement element, int index) {
        waitHelper.waitForElementVisible(element);
        Select select = new Select(element);
        select.selectByIndex(index);
    }

    /**
     * Get selected option from dropdown
     */
    protected String getSelectedDropdownOption(WebElement element) {
        waitHelper.waitForElementVisible(element);
        Select select = new Select(element);
        return select.getFirstSelectedOption().getText();
    }

    /**
     * Get all options from dropdown
     */
    protected List<WebElement> getAllDropdownOptions(WebElement element) {
        waitHelper.waitForElementVisible(element);
        Select select = new Select(element);
        return select.getOptions();
    }

    /**
     * Check/Uncheck checkbox
     */
    protected void setCheckboxState(WebElement checkbox, boolean check) {
        waitHelper.waitForElementVisible(checkbox);
        if (checkbox.isSelected() != check) {
            checkbox.click();
        }
    }

    /**
     * Mouse hover over element
     */
    protected void hoverOverElement(WebElement element) {
        waitHelper.waitForElementVisible(element);
        actions.moveToElement(element).perform();
    }

    /**
     * Double click on element
     */
    protected void doubleClick(WebElement element) {
        waitHelper.waitForElementClickable(element);
        actions.doubleClick(element).perform();
    }

    /**
     * Right click on element
     */
    protected void rightClick(WebElement element) {
        waitHelper.waitForElementClickable(element);
        actions.contextClick(element).perform();
    }

    /**
     * Drag and drop
     */
    protected void dragAndDrop(WebElement source, WebElement target) {
        waitHelper.waitForElementVisible(source);
        waitHelper.waitForElementVisible(target);
        actions.dragAndDrop(source, target).perform();
    }

    /**
     * Scroll to element
     */
    protected void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Click using JavaScript
     */
    protected void clickUsingJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    /**
     * Enter text using JavaScript
     */
    protected void sendKeysUsingJS(WebElement element, String text) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='" + text + "';", element);
    }

    /**
     * Get page title
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Get current URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Navigate to URL
     */
    protected void navigateToUrl(String url) {
        driver.get(url);
        waitHelper.waitForPageLoad();
    }

    /**
     * Refresh page
     */
    protected void refreshPage() {
        driver.navigate().refresh();
        waitHelper.waitForPageLoad();
    }

    /**
     * Navigate back
     */
    protected void navigateBack() {
        driver.navigate().back();
        waitHelper.waitForPageLoad();
    }

    /**
     * Navigate forward
     */
    protected void navigateForward() {
        driver.navigate().forward();
        waitHelper.waitForPageLoad();
    }

    /**
     * Switch to frame
     */
    protected void switchToFrame(WebElement frameElement) {
        waitHelper.waitForElementVisible(frameElement);
        driver.switchTo().frame(frameElement);
    }

    /**
     * Switch to default content
     */
    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    /**
     * Accept alert
     */
    protected void acceptAlert() {
        Alert alert = waitHelper.waitForAlert();
        alert.accept();
    }

    /**
     * Dismiss alert
     */
    protected void dismissAlert() {
        Alert alert = waitHelper.waitForAlert();
        alert.dismiss();
    }

    /**
     * Get alert text
     */
    protected String getAlertText() {
        Alert alert = waitHelper.waitForAlert();
        return alert.getText();
    }

    /**
     * Send text to alert
     */
    protected void sendKeysToAlert(String text) {
        Alert alert = waitHelper.waitForAlert();
        alert.sendKeys(text);
        alert.accept();
    }

    /**
     * Get CSS value
     */
    protected String getCssValue(WebElement element, String propertyName) {
        waitHelper.waitForElementVisible(element);
        return element.getCssValue(propertyName);
    }

    /**
     * Take screenshot
     */
    protected byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Wait for loading spinner to disappear
     */
    protected void waitForLoadingToComplete() {
        waitHelper.waitForLoadingSpinnerToDisappear();
    }

    /**
     * Wait for toast notification and get text
     */
    protected String getToastNotificationText() {
        return waitHelper.waitForToastNotification();
    }

    /**
     * Find elements
     */
    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    /**
     * Get element count
     */
    protected int getElementCount(By locator) {
        return driver.findElements(locator).size();
    }
}
