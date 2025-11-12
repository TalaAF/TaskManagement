package com.taskmanager.hooks;

import com.taskmanager.utils.ConfigReader;
import com.taskmanager.utils.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Hooks - Setup and Teardown for Cucumber scenarios
 * Executes before and after each scenario
 */
public class Hooks {

    private WebDriver driver;

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("========================================");
        System.out.println("Starting Scenario: " + scenario.getName());
        System.out.println("========================================");

        // Initialize WebDriver
        String browser = System.getProperty("browser", ConfigReader.getBrowser());
        driver = DriverFactory.initializeDriver(browser);

        // Navigate to application
        driver.get(ConfigReader.getBaseUrl());
        System.out.println("Browser: " + browser);
        System.out.println("Navigated to: " + ConfigReader.getBaseUrl());
    }

    @After
    public void tearDown(Scenario scenario) {
        // Take screenshot if scenario failed
        if (scenario.isFailed()) {
            System.out.println("Scenario FAILED: " + scenario.getName());

            if (ConfigReader.takeScreenshotOnFailure()) {
                try {
                    byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                            .getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", scenario.getName());
                    System.out.println("Screenshot captured for failed scenario");
                } catch (Exception e) {
                    System.err.println("Failed to capture screenshot: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Scenario PASSED: " + scenario.getName());
        }

        // Close browser
        DriverFactory.quitDriver();
        System.out.println("Browser closed");
        System.out.println("========================================\n");
    }

    /**
     * Get current WebDriver instance
     */
    public static WebDriver getDriver() {
        return DriverFactory.getDriver();
    }
}
