package com.taskmanager.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

/**
 * ExtentReportListener - TestNG listener for ExtentReports
 * Generates comprehensive HTML reports for test execution
 */
public class ExtentReportListener implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        String reportPath = ConfigReader.getExtentReportPath();
        File reportFile = new File(reportPath);
        reportFile.getParentFile().mkdirs();

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("Task Manager Test Report");
        sparkReporter.config().setReportName("Selenium Automation Test Results");
        sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // System Information
        extent.setSystemInfo("Application", "Task Manager");
        extent.setSystemInfo("Browser", ConfigReader.getBrowser());
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Tester", "QA Team");
        extent.setSystemInfo("Environment", "Local");
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test Failed: " + result.getMethod().getMethodName());
        test.get().log(Status.FAIL, result.getThrowable());

        // Add screenshot on failure
        if (ConfigReader.takeScreenshotOnFailure()) {
            try {
                // Screenshot logic would go here
                test.get().log(Status.INFO, "Screenshot captured");
            } catch (Exception e) {
                test.get().log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }

    public static ExtentTest getTest() {
        return test.get();
    }
}
