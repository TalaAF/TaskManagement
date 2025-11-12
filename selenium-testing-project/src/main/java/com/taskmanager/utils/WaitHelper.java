package com.taskmanager.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * WaitHelper - Utility class for handling different wait strategies
 * Implements Explicit Wait, Fluent Wait, and custom wait conditions
 */
public class WaitHelper {

    private WebDriver driver;
    private WebDriverWait wait;

    public WaitHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
    }

    /**
     * Wait for element to be visible
     */
    public WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be visible (WebElement)
     */
    public WebElement waitForElementVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for element to be clickable
     */
    public WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait for element to be clickable (WebElement)
     */
    public WebElement waitForElementClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait for element to be present in DOM
     */
    public WebElement waitForElementPresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Wait for element to be invisible
     */
    public boolean waitForElementInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to disappear (becomes stale)
     */
    public boolean waitForElementToDisappear(WebElement element) {
        return wait.until(ExpectedConditions.stalenessOf(element));
    }

    /**
     * Wait for text to be present in element
     */
    public boolean waitForTextPresent(By locator, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    /**
     * Wait for attribute to contain value
     */
    public boolean waitForAttributeContains(By locator, String attribute, String value) {
        return wait.until(ExpectedConditions.attributeContains(locator, attribute, value));
    }

    /**
     * Wait for URL to contain
     */
    public boolean waitForUrlContains(String urlFragment) {
        return wait.until(ExpectedConditions.urlContains(urlFragment));
    }

    /**
     * Wait for URL to be
     */
    public boolean waitForUrlToBe(String url) {
        return wait.until(ExpectedConditions.urlToBe(url));
    }

    /**
     * Wait for title to contain
     */
    public boolean waitForTitleContains(String title) {
        return wait.until(ExpectedConditions.titleContains(title));
    }

    /**
     * Wait for alert to be present
     */
    public Alert waitForAlert() {
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    /**
     * Wait for frame to be available and switch to it
     */
    public WebDriver waitForFrameAndSwitch(By locator) {
        return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    /**
     * Wait for number of windows to be
     */
    public boolean waitForNumberOfWindows(int numberOfWindows) {
        return wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows));
    }

    /**
     * Fluent Wait - for handling dynamic elements
     */
    public WebElement fluentWait(By locator, int timeoutSeconds, int pollingMillis) {
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofMillis(pollingMillis))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        return fluentWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Fluent Wait for element to be clickable
     */
    public WebElement fluentWaitClickable(By locator, int timeoutSeconds, int pollingMillis) {
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofMillis(pollingMillis))
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotInteractableException.class);

        return fluentWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait for loading spinner to disappear
     */
    public void waitForLoadingSpinnerToDisappear() {
        try {
            By spinner = By.id("loadingOverlay");
            if (driver.findElements(spinner).size() > 0) {
                waitForElementInvisible(spinner);
            }
        } catch (TimeoutException e) {
            // Loading spinner not found or already hidden
        }
    }

    /**
     * Wait for toast notification and return its text
     */
    public String waitForToastNotification() {
        By toastLocator = By.id("toast");
        WebElement toast = waitForElementVisible(toastLocator);
        String toastText = toast.getText();
        // Wait for toast to disappear
        hardWait(3000); // Toast displays for 3 seconds
        return toastText;
    }

    /**
     * Wait for modal to be visible
     */
    public WebElement waitForModalVisible(String modalId) {
        By modalLocator = By.id(modalId);
        return waitForElementVisible(modalLocator);
    }

    /**
     * Wait for modal to disappear
     */
    public boolean waitForModalToDisappear(String modalId) {
        By modalLocator = By.id(modalId);
        return waitForElementInvisible(modalLocator);
    }

    /**
     * Custom wait for element to have specific CSS class
     */
    public boolean waitForElementToHaveClass(WebElement element, String className) {
        return wait.until(driver -> {
            String classes = element.getAttribute("class");
            return classes != null && classes.contains(className);
        });
    }

    /**
     * Custom wait for element count
     */
    public boolean waitForElementCount(By locator, int expectedCount) {
        return wait.until(driver ->
            driver.findElements(locator).size() == expectedCount
        );
    }

    /**
     * Hard wait (Thread.sleep) - use sparingly
     */
    public void hardWait(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Wait for AJAX calls to complete (if using jQuery)
     */
    public boolean waitForAjaxComplete() {
        return wait.until(driver -> {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (Boolean) js.executeScript("return jQuery.active == 0");
        });
    }

    /**
     * Wait for page to be fully loaded
     */
    public boolean waitForPageLoad() {
        return wait.until(driver -> {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return js.executeScript("return document.readyState").equals("complete");
        });
    }
}
