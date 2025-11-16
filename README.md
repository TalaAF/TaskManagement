# Task Management Testing Project

**Course:** SWER312 - Software Testing and Quality Assurance
**Academic Year:** 2024-2025

## 📋 Project Overview

This is a comprehensive software testing project that demonstrates both web application development and automated testing practices. The project consists of two main components:

1. **Task Manager Web Application** - A client-side task management system
2. **Selenium Test Automation Suite** - Comprehensive automated tests using BDD approach

## 🏗️ Project Structure

```
TaskManagement/
├── task-manager-web/          # Frontend web application
│   ├── index.html             # Main application page
│   ├── css/                   # Stylesheets
│   ├── js/                    # JavaScript application logic
│   └── README.md              # Web app documentation
│
└── selenium-testing-project/  # Selenium automation tests
    ├── src/                   # Test source code
    ├── features/              # Cucumber BDD feature files
    ├── pom.xml                # Maven configuration
    └── README.md              # Testing documentation
```

## 🚀 Quick Start

### Prerequisites

- **Web Application:**
  - Modern web browser (Chrome, Firefox, or Edge)
  - No backend server required

- **Test Automation:**
  - Java 11 or higher
  - Maven 3.6+
  - Chrome/Firefox/Edge browser installed

### Running the Web Application

1. Navigate to the web app directory:
   ```bash
   cd task-manager-web
   ```

2. Open `index.html` in your browser:
   - **Option 1:** Double-click the `index.html` file
   - **Option 2:** Use a local server:
     ```bash
     python -m http.server 8000
     # Then visit http://localhost:8000
     ```

### Running the Tests

1. Navigate to the testing directory:
   ```bash
   cd selenium-testing-project
   ```

2. Run all tests:
   ```bash
   mvn clean test
   ```

3. Run with specific browser:
   ```bash
   mvn clean test -Dbrowser=chrome
   # Options: chrome, firefox, edge
   ```

4. View test reports:
   - **ExtentReports:** `test-output/ExtentReport.html`
   - **Cucumber Reports:** `test-output/cucumber-reports/`

## 📚 Component Details

### Task Manager Web Application

A pure frontend web application built with:
- HTML5, CSS3, Vanilla JavaScript
- LocalStorage for data persistence
- Responsive design with purple theme
- Features:
  - User authentication (login/register)
  - Task CRUD operations
  - Category management
  - Task filtering and search
  - Due date tracking

For detailed information, see [task-manager-web/README.md](task-manager-web/README.md)

### Selenium Test Automation

Comprehensive test suite implementing:
- **Page Object Model (POM)** design pattern
- **Cucumber BDD** framework
- **Data-Driven Testing** with CSV, JSON, and Excel
- **Cross-Browser Testing** (Chrome, Firefox, Edge)
- **Parallel Execution** support
- **ExtentReports** for detailed reporting

Test Coverage:
- Login/Registration functionality
- Task management operations
- Category management
- Data validation
- Edge cases and error handling

For detailed information, see [selenium-testing-project/README.md](selenium-testing-project/README.md)

## 🧪 Testing Approach

This project demonstrates multiple testing techniques:

1. **Functional Testing** - Validating core features work correctly
2. **Data-Driven Testing** - Testing with multiple data sets (CSV, JSON, Excel)
3. **Behavior-Driven Development** - Using Gherkin syntax for test scenarios
4. **Cross-Browser Testing** - Ensuring compatibility across browsers
5. **Page Object Model** - Maintainable test architecture

## 📊 Test Reports

After running tests, you can view comprehensive reports:

- **Extent Reports:** Visual HTML reports with screenshots
  - Location: `selenium-testing-project/test-output/ExtentReport.html`

- **Cucumber Reports:** BDD-style test results
  - Location: `selenium-testing-project/test-output/cucumber-reports/`

## 🛠️ Development

### Project Technologies

**Web Application:**
- HTML5
- CSS3
- JavaScript (ES6+)
- LocalStorage API

**Test Automation:**
- Java 11
- Selenium WebDriver 4.x
- Cucumber 7.x
- TestNG
- Maven
- WebDriverManager
- ExtentReports

### Adding New Tests

1. Write feature file in `features/` directory using Gherkin syntax
2. Implement step definitions in `src/test/java/stepdefinitions/`
3. Add page objects if testing new pages in `src/test/java/pages/`
4. Run tests and verify results

## 📖 Documentation

- [Web Application README](task-manager-web/README.md) - Web app features and usage
- [Selenium Testing README](selenium-testing-project/README.md) - Test suite details
- Feature files in `selenium-testing-project/features/` - Test scenarios in plain English

## 🤝 Team

This is an academic project for Software Testing and Quality Assurance course.

## 📝 License

This project is for educational purposes only.

## 🔗 Additional Resources

- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [Cucumber Documentation](https://cucumber.io/docs/cucumber/)
- [TestNG Documentation](https://testng.org/doc/)
- [Page Object Model Pattern](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/)

---

**Note:** This project demonstrates best practices in test automation including proper project structure, design patterns, and comprehensive test coverage for academic evaluation.
