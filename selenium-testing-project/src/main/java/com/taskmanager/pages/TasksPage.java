package com.taskmanager.pages;

import com.taskmanager.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * TasksPage - Page Object Model for Tasks Page
 */
public class TasksPage extends BasePage {

    // Navigation
    @FindBy(css = "[data-testid='nav-dashboard']")
    private WebElement navDashboard;

    @FindBy(css = "[data-testid='nav-logout']")
    private WebElement navLogout;

    // Add Task Button
    @FindBy(css = "[data-testid='add-task-button']")
    private WebElement addTaskButton;

    // Search and Filters
    @FindBy(css = "[data-testid='search-input']")
    private WebElement searchInput;

    @FindBy(css = "[data-testid='status-filter']")
    private WebElement statusFilter;

    @FindBy(css = "[data-testid='priority-filter']")
    private WebElement priorityFilter;

    @FindBy(css = "[data-testid='category-filter']")
    private WebElement categoryFilter;

    @FindBy(css = "[data-testid='clear-filters']")
    private WebElement clearFiltersButton;

    // Tasks Table
    @FindBy(css = "[data-testid='tasks-table']")
    private WebElement tasksTable;

    @FindBy(id = "tasksTableBody")
    private WebElement tasksTableBody;

    // Task Modal
    @FindBy(id = "taskModal")
    private WebElement taskModal;

    @FindBy(css = "[data-testid='task-title']")
    private WebElement taskTitleField;

    @FindBy(css = "[data-testid='task-description']")
    private WebElement taskDescriptionField;

    @FindBy(css = "[data-testid='task-priority']")
    private WebElement taskPriorityDropdown;

    @FindBy(css = "[data-testid='task-due-date']")
    private WebElement taskDueDateField;

    @FindBy(css = "[data-testid='task-category']")
    private WebElement taskCategoryDropdown;

    @FindBy(css = "[data-testid='task-status']")
    private WebElement taskStatusDropdown;

    @FindBy(css = "[data-testid='save-task']")
    private WebElement saveTaskButton;

    @FindBy(css = "[data-testid='cancel-task']")
    private WebElement cancelTaskButton;

    @FindBy(css = "[data-testid='modal-close']")
    private WebElement modalCloseButton;

    // Delete Modal
    @FindBy(id = "deleteModal")
    private WebElement deleteModal;

    @FindBy(css = "[data-testid='confirm-delete']")
    private WebElement confirmDeleteButton;

    @FindBy(css = "[data-testid='cancel-delete']")
    private WebElement cancelDeleteButton;

    // Pagination
    @FindBy(id = "pagination")
    private WebElement paginationContainer;

    public TasksPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Verify user is on tasks page
     */
    public boolean isOnTasksPage() {
        return getCurrentUrl().contains("tasks.html");
    }

    /**
     * Click add task button
     */
    public TasksPage clickAddTask() {
        clickElement(addTaskButton);
        waitHelper.waitForModalVisible("taskModal");
        return this;
    }

    /**
     * Fill task form
     */
    public TasksPage fillTaskForm(String title, String description, String priority, String dueDate, String category) {
        sendKeys(taskTitleField, title);
        sendKeys(taskDescriptionField, description);
        selectDropdownByValue(taskPriorityDropdown, priority);
        sendKeys(taskDueDateField, dueDate);
        selectDropdownByValue(taskCategoryDropdown, category);
        return this;
    }

    /**
     * Save task
     */
    public TasksPage saveTask() {
        clickElement(saveTaskButton);
        waitForLoadingToComplete();
        waitHelper.waitForModalToDisappear("taskModal");
        return this;
    }

    /**
     * Create new task (complete flow)
     */
    public TasksPage createTask(String title, String description, String priority, String dueDate, String category) {
        clickAddTask();
        fillTaskForm(title, description, priority, dueDate, category);
        saveTask();
        return this;
    }

    /**
     * Search for tasks
     */
    public TasksPage searchTasks(String searchTerm) {
        sendKeys(searchInput, searchTerm);
        waitHelper.hardWait(500); // Wait for debounce
        return this;
    }

    /**
     * Filter by status
     */
    public TasksPage filterByStatus(String status) {
        selectDropdownByValue(statusFilter, status);
        return this;
    }

    /**
     * Filter by priority
     */
    public TasksPage filterByPriority(String priority) {
        selectDropdownByValue(priorityFilter, priority);
        return this;
    }

    /**
     * Filter by category
     */
    public TasksPage filterByCategory(String category) {
        selectDropdownByValue(categoryFilter, category);
        return this;
    }

    /**
     * Clear all filters
     */
    public TasksPage clearFilters() {
        clickElement(clearFiltersButton);
        return this;
    }

    /**
     * Get task count
     */
    public int getTaskCount() {
        By rowLocator = By.cssSelector("#tasksTableBody tr");
        return getElementCount(rowLocator);
    }

    /**
     * Check if task exists by title
     */
    public boolean isTaskPresent(String title) {
        By taskLocator = By.xpath("//td[contains(text(), '" + title + "')]");
        return isElementDisplayed(taskLocator);
    }

    /**
     * Edit task by title
     */
    public TasksPage editTaskByTitle(String title) {
        By editButtonLocator = By.xpath("//td[contains(text(), '" + title + "')]/following-sibling::td//button[contains(@data-testid, 'edit-task')]");
        clickElement(editButtonLocator);
        waitHelper.waitForModalVisible("taskModal");
        return this;
    }

    /**
     * Delete task by title
     */
    public TasksPage deleteTaskByTitle(String title) {
        By deleteButtonLocator = By.xpath("//td[contains(text(), '" + title + "')]/following-sibling::td//button[contains(@data-testid, 'delete-task')]");
        clickElement(deleteButtonLocator);
        waitHelper.waitForModalVisible("deleteModal");
        return this;
    }

    /**
     * Confirm delete
     */
    public TasksPage confirmDelete() {
        clickElement(confirmDeleteButton);
        waitForLoadingToComplete();
        waitHelper.waitForModalToDisappear("deleteModal");
        return this;
    }

    /**
     * Cancel delete
     */
    public TasksPage cancelDelete() {
        clickElement(cancelDeleteButton);
        return this;
    }

    /**
     * Toggle task status (mark complete/incomplete)
     */
    public TasksPage toggleTaskStatus(String title) {
        By toggleButtonLocator = By.xpath("//td[contains(text(), '" + title + "')]/following-sibling::td//button[contains(@data-testid, 'toggle-status')]");
        clickElement(toggleButtonLocator);
        waitForLoadingToComplete();
        return this;
    }

    /**
     * Get task status
     */
    public String getTaskStatus(String title) {
        By statusLocator = By.xpath("//td[contains(text(), '" + title + "')]/following-sibling::td//span[contains(@class, 'task-status')]");
        return getElementText(statusLocator);
    }

    /**
     * Get task priority
     */
    public String getTaskPriority(String title) {
        By priorityLocator = By.xpath("//td[contains(text(), '" + title + "')]/following-sibling::td//span[contains(@class, 'badge')]");
        return getElementText(priorityLocator);
    }

    /**
     * Navigate to next page
     */
    public TasksPage goToNextPage() {
        By nextButton = By.cssSelector("[data-testid='next-page']");
        clickElement(nextButton);
        return this;
    }

    /**
     * Navigate to previous page
     */
    public TasksPage goToPreviousPage() {
        By prevButton = By.cssSelector("[data-testid='prev-page']");
        clickElement(prevButton);
        return this;
    }

    /**
     * Navigate to specific page
     */
    public TasksPage goToPage(int pageNumber) {
        By pageButton = By.cssSelector("[data-testid='page-" + pageNumber + "']");
        clickElement(pageButton);
        return this;
    }

    /**
     * Close modal
     */
    public TasksPage closeModal() {
        clickElement(modalCloseButton);
        return this;
    }

    /**
     * Cancel task form
     */
    public TasksPage cancelTaskForm() {
        clickElement(cancelTaskButton);
        return this;
    }

    /**
     * Navigate back to dashboard
     */
    public DashboardPage navigateToDashboard() {
        clickElement(navDashboard);
        return new DashboardPage(driver);
    }

    /**
     * Logout
     */
    public LoginPage logout() {
        clickElement(navLogout);
        acceptAlert();
        waitHelper.hardWait(1000);
        return new LoginPage(driver);
    }

    /**
     * Get toast notification text
     */
    public String getNotificationText() {
        return getToastNotificationText();
    }
}
