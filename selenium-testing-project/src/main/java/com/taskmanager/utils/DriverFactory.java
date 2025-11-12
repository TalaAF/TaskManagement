package com.taskmanager.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.Dimension;

import java.time.Duration;

/**
 * DriverFactory - Factory pattern to create and manage WebDriver instances
 * Supports Chrome, Firefox, and Edge browsers
 */
public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Initialize WebDriver based on browser type
     */
    public static WebDriver initializeDriver(String browserName) {
        WebDriver webDriver;

        switch (browserName.toLowerCase()) {
            case "chrome":
                webDriver = createChromeDriver();
                break;
            case "firefox":
                webDriver = createFirefoxDriver();
                break;
            case "edge":
                webDriver = createEdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Browser '" + browserName + "' not supported. Use chrome, firefox, or edge.");
        }

        // Set timeouts
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getPageLoadTimeout()));
        webDriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(ConfigReader.getScriptTimeout()));

        // Maximize window or set custom size
        if (ConfigReader.shouldMaximize()) {
            webDriver.manage().window().maximize();
        } else {
            webDriver.manage().window().setSize(
                new Dimension(ConfigReader.getWindowWidth(), ConfigReader.getWindowHeight())
            );
        }

        driver.set(webDriver);
        return webDriver;
    }

    /**
     * Create Chrome WebDriver
     */
    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless=new");
        }

        // Add custom Chrome options
        String[] customOptions = ConfigReader.getChromeOptions();
        for (String option : customOptions) {
            options.addArguments(option.trim());
        }

        // Common options
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        return new ChromeDriver(options);
    }

    /**
     * Create Firefox WebDriver
     */
    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();

        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless");
        }

        // Add custom Firefox options
        String[] customOptions = ConfigReader.getFirefoxOptions();
        for (String option : customOptions) {
            options.addArguments(option.trim());
        }

        return new FirefoxDriver(options);
    }

    /**
     * Create Edge WebDriver
     */
    private static WebDriver createEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();

        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless=new");
        }

        // Add custom Edge options
        String[] customOptions = ConfigReader.getEdgeOptions();
        for (String option : customOptions) {
            options.addArguments(option.trim());
        }

        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");

        return new EdgeDriver(options);
    }

    /**
     * Get current thread's WebDriver instance
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Quit and remove driver
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
