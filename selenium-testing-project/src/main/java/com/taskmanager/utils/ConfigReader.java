package com.taskmanager.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigReader - Utility class to read configuration from config.properties file
 */
public class ConfigReader {

    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config/config.properties";

    static {
        try {
            properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load config.properties file");
        }
    }

    /**
     * Get property value by key
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config.properties");
        }
        return value;
    }

    /**
     * Get property with default value if not found
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    // Application URLs
    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static String getDashboardUrl() {
        return getProperty("dashboard.url");
    }

    public static String getTasksUrl() {
        return getProperty("tasks.url");
    }

    public static String getCategoriesUrl() {
        return getProperty("categories.url");
    }

    public static String getRegisterUrl() {
        return getProperty("register.url");
    }

    // Browser Configuration
    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }

    public static boolean shouldMaximize() {
        return Boolean.parseBoolean(getProperty("maximize", "true"));
    }

    public static int getWindowWidth() {
        return Integer.parseInt(getProperty("window.width", "1920"));
    }

    public static int getWindowHeight() {
        return Integer.parseInt(getProperty("window.height", "1080"));
    }

    // Test Credentials
    public static String getValidUsername() {
        return getProperty("valid.username");
    }

    public static String getValidPassword() {
        return getProperty("valid.password");
    }

    public static String getTestEmail() {
        return getProperty("test.email");
    }

    // Timeouts
    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "20"));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("page.load.timeout", "30"));
    }

    public static int getScriptTimeout() {
        return Integer.parseInt(getProperty("script.timeout", "30"));
    }

    // Wait Intervals
    public static long getPollingInterval() {
        return Long.parseLong(getProperty("polling.interval", "500"));
    }

    public static long getShortWait() {
        return Long.parseLong(getProperty("short.wait", "2000"));
    }

    public static long getMediumWait() {
        return Long.parseLong(getProperty("medium.wait", "5000"));
    }

    public static long getLongWait() {
        return Long.parseLong(getProperty("long.wait", "10000"));
    }

    // Screenshot Configuration
    public static boolean takeScreenshotOnFailure() {
        return Boolean.parseBoolean(getProperty("screenshot.on.failure", "true"));
    }

    public static String getScreenshotDirectory() {
        return getProperty("screenshot.directory", "test-output/screenshots");
    }

    // Reporting
    public static String getExtentReportPath() {
        return getProperty("extent.report.path", "test-output/ExtentReport.html");
    }

    public static String getCucumberReportPath() {
        return getProperty("cucumber.report.path", "test-output/cucumber-reports");
    }

    // Test Data Paths
    public static String getCsvDataPath() {
        return getProperty("testdata.csv.path");
    }

    public static String getExcelDataPath() {
        return getProperty("testdata.excel.path");
    }

    public static String getJsonDataPath() {
        return getProperty("testdata.json.path");
    }

    // Browser Options
    public static String[] getChromeOptions() {
        String options = getProperty("chrome.options", "");
        return options.isEmpty() ? new String[0] : options.split(",");
    }

    public static String[] getFirefoxOptions() {
        String options = getProperty("firefox.options", "");
        return options.isEmpty() ? new String[0] : options.split(",");
    }

    public static String[] getEdgeOptions() {
        String options = getProperty("edge.options", "");
        return options.isEmpty() ? new String[0] : options.split(",");
    }

    // Cross-Browser flags
    public static boolean runOnChrome() {
        return Boolean.parseBoolean(getProperty("run.on.chrome", "true"));
    }

    public static boolean runOnFirefox() {
        return Boolean.parseBoolean(getProperty("run.on.firefox", "true"));
    }

    public static boolean runOnEdge() {
        return Boolean.parseBoolean(getProperty("run.on.edge", "true"));
    }
}
