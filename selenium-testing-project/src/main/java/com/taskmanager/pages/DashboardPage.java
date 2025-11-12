package com.taskmanager.pages;

import com.taskmanager.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * DashboardPage - Page Object Model for Dashboard Page
 */
public class DashboardPage extends BasePage {

    // Navigation
    @FindBy(css = "[data-testid='nav-dashboard']")
    private WebElement navDashboard;

    @FindBy(css = "[data-testid='nav-tasks']")
    private WebElement navTasks;

    @FindBy(css = "[data-testid='nav-categories']")
    private WebElement navCategories;

    @FindBy(css = "[data-testid='nav-logout']")
    private WebElement navLogout;

    // Welcome message and statistics
    @FindBy(id = "welcomeMessage")
    private WebElement welcomeMessage;

    @FindBy(id = "totalTasks")
    private WebElement totalTasksCount;

    @FindBy(id = "completedTasks")
    private WebElement completedTasksCount;

    @FindBy(id = "pendingTasks")
    private WebElement pendingTasksCount;

    @FindBy(id = "highPriorityTasks")
    private WebElement highPriorityTasksCount;

    // Quick action buttons
    @FindBy(css = "[data-testid='quick-add-task']")
    private WebElement quickAddTaskButton;

    @FindBy(css = "[data-testid='quick-view-tasks']")
    private WebElement quickViewTasksButton;

    @FindBy(css = "[data-testid='quick-manage-categories']")
    private WebElement quickManageCategoriesButton;

    // Recent tasks section
    @FindBy(id = "recentTasksList")
    private WebElement recentTasksList;

    public DashboardPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Get welcome message text
     */
    public String getWelcomeMessage() {
        waitHelper.waitForElementVisible(welcomeMessage);
        return getElementText(welcomeMessage);
    }

    /**
     * Verify user is on dashboard page
     */
    public boolean isOnDashboardPage() {
        return getCurrentUrl().contains("dashboard.html");
    }

    /**
     * Get total tasks count
     */
    public int getTotalTasksCount() {
        String count = getElementText(totalTasksCount);
        return Integer.parseInt(count);
    }

    /**
     * Get completed tasks count
     */
    public int getCompletedTasksCount() {
        String count = getElementText(completedTasksCount);
        return Integer.parseInt(count);
    }

    /**
     * Get pending tasks count
     */
    public int getPendingTasksCount() {
        String count = getElementText(pendingTasksCount);
        return Integer.parseInt(count);
    }

    /**
     * Get high priority tasks count
     */
    public int getHighPriorityTasksCount() {
        String count = getElementText(highPriorityTasksCount);
        return Integer.parseInt(count);
    }

    /**
     * Navigate to Tasks page
     */
    public TasksPage navigateToTasks() {
        clickElement(navTasks);
        return new TasksPage(driver);
    }

    /**
     * Navigate to Categories page
     */
    public CategoriesPage navigateToCategories() {
        clickElement(navCategories);
        return new CategoriesPage(driver);
    }

    /**
     * Navigate to Dashboard page
     */
    public DashboardPage navigateToDashboard() {
        clickElement(navDashboard);
        return this;
    }

    /**
     * Logout
     */
    public LoginPage logout() {
        clickElement(navLogout);
        // Handle confirmation dialog
        acceptAlert();
        waitHelper.hardWait(1000);
        return new LoginPage(driver);
    }

    /**
     * Click quick add task button
     */
    public TasksPage clickQuickAddTask() {
        clickElement(quickAddTaskButton);
        return new TasksPage(driver);
    }

    /**
     * Click quick view tasks button
     */
    public TasksPage clickQuickViewTasks() {
        clickElement(quickViewTasksButton);
        return new TasksPage(driver);
    }

    /**
     * Click quick manage categories button
     */
    public CategoriesPage clickQuickManageCategories() {
        clickElement(quickManageCategoriesButton);
        return new CategoriesPage(driver);
    }

    /**
     * Check if recent tasks are displayed
     */
    public boolean areRecentTasksDisplayed() {
        return isElementDisplayed(recentTasksList);
    }
}
