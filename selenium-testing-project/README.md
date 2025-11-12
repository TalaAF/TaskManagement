# Task Manager - Selenium WebDriver Automation Testing

## 🎯 Project Overview

This is a comprehensive Selenium WebDriver automation testing project for the Task Manager web application, built using:
- **Page Object Model (POM)** design pattern
- **Behavior Driven Development (BDD)** with Cucumber
- **TestNG** framework for test execution
- **Data-Driven Testing** with CSV and JSON
- **Cross-Browser Testing** (Chrome, Firefox, Edge)
- **Parallel Test Execution**

**Course**: SWER312 Software Testing and Quality Assurance
**Team Size**: 2 students
**Project Type**: Academic Assignment

---

## 📋 Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
- [Running Tests](#running-tests)
- [Test Scenarios](#test-scenarios)
- [Design Patterns](#design-patterns)
- [Reporting](#reporting)
- [Configuration](#configuration)

---

## ✨ Features

### Testing Capabilities
✅ **40+ Automated Test Scenarios**
- Login & Authentication (11 scenarios)
- Task Management (25 scenarios)
- Category Management (15 scenarios)

✅ **Testing Techniques Implemented**
- Page Object Model (POM)
- Behavior Driven Development (BDD) with Gherkin
- Data-Driven Testing (CSV/JSON)
- Cross-Browser Testing (Chrome, Firefox, Edge)
- Parallel Test Execution
- Explicit & Fluent Waits
- Screenshot on Failure
- ExtentReports Integration

✅ **Test Coverage**
- Positive & Negative test cases
- Form validation testing
- CRUD operations
- Search & Filter functionality
- Pagination testing
- Modal interactions
- Toast notifications
- Loading states

---

## 📁 Project Structure

```
selenium-testing-project/
├── src/
│   ├── main/
│   │   └── java/com/taskmanager/
│   │       ├── base/
│   │       │   └── BasePage.java              # Base page with common methods
│   │       ├── pages/                          # Page Object Model classes
│   │       │   ├── LoginPage.java
│   │       │   ├── RegisterPage.java
│   │       │   ├── DashboardPage.java
│   │       │   ├── TasksPage.java
│   │       │   └── CategoriesPage.java
│   │       └── utils/                          # Utility classes
│   │           ├── ConfigReader.java           # Config file reader
│   │           ├── DriverFactory.java          # WebDriver management
│   │           └── WaitHelper.java             # Wait strategies
│   └── test/
│       ├── java/com/taskmanager/
│       │   ├── stepdefinitions/                # Cucumber step definitions
│       │   │   ├── LoginSteps.java
│       │   │   ├── TaskSteps.java
│       │   │   └── CategorySteps.java
│       │   ├── runners/                        # Test runners
│       │   │   └── TestRunner.java
│       │   └── hooks/                          # Before/After hooks
│       │       └── Hooks.java
│       └── resources/
│           ├── features/                       # Gherkin feature files
│           │   ├── login.feature
│           │   ├── tasks.feature
│           │   └── categories.feature
│           ├── testdata/                       # Test data files
│           │   ├── logindata.csv
│           │   ├── taskdata.csv
│           │   └── testdata.json
│           ├── config/
│           │   └── config.properties           # Configuration file
│           └── extent.properties               # Report configuration
├── pom.xml                                     # Maven dependencies
├── testng.xml                                  # TestNG suite configuration
└── README.md                                   # This file
```

---

## 🛠️ Technologies Used

| Technology | Version | Purpose |
|-----------|---------|---------|
| Java | 11+ | Programming Language |
| Selenium WebDriver | 4.15.0 | Browser Automation |
| Cucumber | 7.14.0 | BDD Framework |
| TestNG | 7.8.0 | Test Framework |
| WebDriverManager | 5.6.2 | Automatic Driver Management |
| Apache POI | 5.2.5 | Excel Data Reading |
| OpenCSV | 5.9 | CSV Data Reading |
| ExtentReports | 5.1.1 | Test Reporting |
| Maven | 3.6+ | Build Tool |

---

## 📋 Prerequisites

Before running the tests, ensure you have:

1. **Java JDK 11 or higher**
   ```bash
   java -version
   ```

2. **Apache Maven 3.6+**
   ```bash
   mvn -version
   ```

3. **Web Browsers** (latest versions)
   - Google Chrome
   - Mozilla Firefox
   - Microsoft Edge

4. **Task Manager Web Application**
   - Located in: `../task-manager-web/`
   - Can be opened directly in browser (no server required)

---

## 🚀 Setup Instructions

### 1. Clone the Repository
```bash
cd /home/user/TaskManagement/selenium-testing-project
```

### 2. Install Dependencies
```bash
mvn clean install -DskipTests
```

### 3. Update Configuration (Optional)
Edit `src/test/resources/config/config.properties` to customize:
- Browser selection
- Timeouts
- Test data paths
- Screenshot settings

### 4. Verify Web Application Path
Ensure the path in `config.properties` points to your web app:
```properties
base.url=file:///home/user/TaskManagement/task-manager-web/index.html
```

---

## 🎮 Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Feature File
```bash
mvn test -Dcucumber.options="src/test/resources/features/login.feature"
```

### Run Tests with Specific Browser
```bash
mvn test -Dbrowser=chrome
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge
```

### Run Tests with Tags
```bash
# Run smoke tests only
mvn test -Dcucumber.filter.tags="@smoke"

# Run login tests only
mvn test -Dcucumber.filter.tags="@login"

# Run positive tests only
mvn test -Dcucumber.filter.tags="@positive"

# Exclude negative tests
mvn test -Dcucumber.filter.tags="not @negative"
```

### Run Tests in Parallel
```bash
mvn test -Dparallel=tests -DthreadCount=3
```

### Run with TestNG XML
```bash
mvn test -DsuiteXmlFile=testng.xml
```

---

## 📝 Test Scenarios

### Login Feature (11 scenarios)
1. ✅ Successful login with valid credentials
2. ❌ Login with invalid username
3. ❌ Login with invalid password
4. ❌ Login with empty username
5. ❌ Login with empty password
6. ❌ Login with both fields empty
7. ✅ Login with Remember Me checked
8. ✅ Login with multiple credentials (data-driven)
9. ✅ Successful user registration
10. ❌ Registration with mismatched passwords
11. ✅ Successful logout

### Task Management Feature (25 scenarios)
1. ✅ Create a new task successfully
2. ✅ Edit an existing task
3. ✅ Delete a task with confirmation
4. ❌ Cancel task deletion
5. ✅ Mark task as complete
6. ✅ Mark task as incomplete
7. ✅ Search tasks by title
8. ❌ Search with no results
9. ✅ Filter by priority (High/Medium/Low)
10. ✅ Filter by status (Complete/Incomplete)
11. ✅ Filter by category
12. ✅ Clear all filters
13. ✅ Navigate pagination
14. ❌ Validation for required fields
15. ✅ Data-driven task creation
16. ✅ Complete CRUD workflow

### Category Management Feature (15 scenarios)
1. ✅ Create a new category
2. ❌ Create duplicate category
3. ✅ Edit existing category
4. ✅ Edit category updates tasks
5. ✅ Delete category with confirmation
6. ❌ Cancel category deletion
7. ✅ Delete reassigns tasks
8. ❌ Validation tests
9. ✅ View all categories
10. ✅ Category color display
11. ✅ Complete category workflow
12. ❌ Cancel category creation
13. ✅ Close modal interactions

---

## 🏗️ Design Patterns

### 1. Page Object Model (POM)
**Why POM?**
- Separates test logic from page structure
- Improves maintainability and reusability
- Reduces code duplication
- Makes tests more readable

**Example:**
```java
public class LoginPage extends BasePage {
    @FindBy(id = "username")
    private WebElement usernameField;

    public LoginPage enterUsername(String username) {
        sendKeys(usernameField, username);
        return this;
    }
}
```

### 2. Behavior Driven Development (BDD)
**Benefits:**
- Business-readable test scenarios
- Collaboration between technical and non-technical stakeholders
- Living documentation

**Example Gherkin:**
```gherkin
Scenario: Successful login with valid credentials
  Given I am on the login page
  When I enter username "admin" and password "admin123"
  And I click the login button
  Then I should be redirected to the dashboard page
```

### 3. Factory Pattern
Used in `DriverFactory` for browser initialization:
```java
public static WebDriver initializeDriver(String browserName) {
    switch (browserName.toLowerCase()) {
        case "chrome": return createChromeDriver();
        case "firefox": return createFirefoxDriver();
        case "edge": return createEdgeDriver();
    }
}
```

---

## 📊 Reporting

### ExtentReports
After test execution, find the report at:
```
test-output/ExtentReport.html
```

**Features:**
- Test execution timeline
- Pass/Fail statistics
- Screenshots on failure
- Browser and environment details
- Step-by-step logs

### Cucumber Reports
JSON reports generated at:
```
test-output/cucumber-reports/
```

### TestNG Reports
HTML reports at:
```
test-output/index.html
```

---

## ⚙️ Configuration

### config.properties
```properties
# Browser Configuration
browser=chrome                    # chrome, firefox, edge
headless=false                    # true for headless mode
maximize=true                     # Maximize browser window

# Timeouts (seconds)
implicit.wait=10
explicit.wait=20
page.load.timeout=30

# Test Credentials
valid.username=admin
valid.password=admin123

# Reporting
screenshot.on.failure=true
extent.report.path=test-output/ExtentReport.html
```

---

## 🔄 Testing Techniques Demonstrated

### 1. Cross-Browser Testing
Tests run on Chrome, Firefox, and Edge browsers using WebDriverManager for automatic driver management.

### 2. Parallel Execution
TestNG configuration allows parallel execution:
```xml
<suite name="Task Manager Tests" parallel="tests" thread-count="3">
```

### 3. Data-Driven Testing
**CSV Example:**
```csv
username,password,expected_result
admin,admin123,success
invalid,wrong,failure
```

**JSON Example:**
```json
{
  "users": [
    {"username": "admin", "password": "admin123"}
  ]
}
```

### 4. Wait Strategies

**Explicit Wait:**
```java
waitHelper.waitForElementClickable(loginButton);
```

**Fluent Wait:**
```java
waitHelper.fluentWait(locator, 20, 500);
```

**Custom Wait:**
```java
waitHelper.waitForLoadingSpinnerToDisappear();
```

---

## 📈 Test Execution Flow

1. **Setup** (Hooks.java)
   - Initialize WebDriver
   - Navigate to application
   - Set up logging

2. **Execution** (Step Definitions)
   - Execute Gherkin steps
   - Interact with Page Objects
   - Assert expected results

3. **Teardown** (Hooks.java)
   - Take screenshot on failure
   - Close browser
   - Generate reports

---

## 🐛 Troubleshooting

### Common Issues

**Issue**: Driver not found
```bash
Solution: WebDriverManager handles this automatically.
Ensure internet connection for first download.
```

**Issue**: Element not found
```bash
Solution: Check wait strategies and ensure application loads completely.
Use explicit waits for dynamic elements.
```

**Issue**: Tests fail randomly
```bash
Solution: Increase timeout values in config.properties
Add proper waits for loading states
```

---

## 📚 Learning Outcomes

This project demonstrates:
1. ✅ **Page Object Model** implementation
2. ✅ **BDD with Cucumber** and Gherkin syntax
3. ✅ **Cross-browser testing** configuration
4. ✅ **Parallel test execution** with TestNG
5. ✅ **Data-driven testing** with external data sources
6. ✅ **Proper wait strategies** (Explicit, Fluent, Custom)
7. ✅ **Test reporting** with ExtentReports
8. ✅ **Code organization** and best practices

---

## 👥 Team Members

- Student 1: [Name]
- Student 2: [Name]

---

## 📄 License

This project is for educational purposes only.

---

## 🙏 Acknowledgments

- Course: SWER312 Software Testing and Quality Assurance
- Instructor: [Instructor Name]
- Institution: [University Name]

---

**Happy Testing! 🚀**
