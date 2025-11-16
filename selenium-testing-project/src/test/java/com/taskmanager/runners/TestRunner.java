package com.taskmanager.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * TestRunner - Main Cucumber TestNG Runner
 * Executes all feature files and generates reports
 */
@CucumberOptions(
        features = "src/test/resources/features",           // Path to feature files
        glue = {"com.taskmanager.stepdefinitions",          // Package for step definitions
                "com.taskmanager.hooks"},                    // Package for hooks
        plugin = {
                "pretty",                                     // Console output
                "html:test-output/cucumber-reports/cucumber.html",  // HTML report
                "json:test-output/cucumber-reports/cucumber.json",  // JSON report
                "junit:test-output/cucumber-reports/cucumber.xml"   // JUnit XML report
        },
        monochrome = true,                                   // Readable console output
        dryRun = false,                                      // Set to true to check step definitions
        tags = "@smoke"                                      // Run only smoke tests
)
public class TestRunner extends AbstractTestNGCucumberTests {

    /**
     * Enable parallel execution with TestNG
     * Scenarios will run in parallel based on testng.xml configuration
     */
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
