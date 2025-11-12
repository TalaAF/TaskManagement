package com.taskmanager.pages;

import com.taskmanager.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * CategoriesPage - Page Object Model for Categories Page
 */
public class CategoriesPage extends BasePage {

    // Navigation
    @FindBy(css = "[data-testid='nav-dashboard']")
    private WebElement navDashboard;

    @FindBy(css = "[data-testid='nav-tasks']")
    private WebElement navTasks;

    @FindBy(css = "[data-testid='nav-logout']")
    private WebElement navLogout;

    // Add Category Button
    @FindBy(css = "[data-testid='add-category-button']")
    private WebElement addCategoryButton;

    // Categories Grid
    @FindBy(css = "[data-testid='categories-grid']")
    private WebElement categoriesGrid;

    // Category Modal
    @FindBy(id = "categoryModal")
    private WebElement categoryModal;

    @FindBy(css = "[data-testid='category-name']")
    private WebElement categoryNameField;

    @FindBy(css = "[data-testid='category-color']")
    private WebElement categoryColorPicker;

    @FindBy(css = "[data-testid='save-category']")
    private WebElement saveCategoryButton;

    @FindBy(css = "[data-testid='cancel-category']")
    private WebElement cancelCategoryButton;

    @FindBy(css = "[data-testid='modal-close']")
    private WebElement modalCloseButton;

    // Delete Modal
    @FindBy(id = "deleteModal")
    private WebElement deleteModal;

    @FindBy(css = "[data-testid='confirm-delete']")
    private WebElement confirmDeleteButton;

    @FindBy(css = "[data-testid='cancel-delete']")
    private WebElement cancelDeleteButton;

    public CategoriesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Verify user is on categories page
     */
    public boolean isOnCategoriesPage() {
        return getCurrentUrl().contains("categories.html");
    }

    /**
     * Click add category button
     */
    public CategoriesPage clickAddCategory() {
        clickElement(addCategoryButton);
        waitHelper.waitForModalVisible("categoryModal");
        return this;
    }

    /**
     * Enter category name
     */
    public CategoriesPage enterCategoryName(String name) {
        sendKeys(categoryNameField, name);
        return this;
    }

    /**
     * Select category color
     */
    public CategoriesPage selectCategoryColor(String colorHex) {
        sendKeysUsingJS(categoryColorPicker, colorHex);
        return this;
    }

    /**
     * Save category
     */
    public CategoriesPage saveCategory() {
        clickElement(saveCategoryButton);
        waitForLoadingToComplete();
        waitHelper.waitForModalToDisappear("categoryModal");
        return this;
    }

    /**
     * Create new category (complete flow)
     */
    public CategoriesPage createCategory(String name, String color) {
        clickAddCategory();
        enterCategoryName(name);
        selectCategoryColor(color);
        saveCategory();
        return this;
    }

    /**
     * Check if category exists by name
     */
    public boolean isCategoryPresent(String name) {
        By categoryLocator = By.xpath("//h3[contains(text(), '" + name + "')]");
        return isElementDisplayed(categoryLocator);
    }

    /**
     * Get category count
     */
    public int getCategoryCount() {
        By categoryCards = By.cssSelector(".category-card");
        return getElementCount(categoryCards);
    }

    /**
     * Edit category by name
     */
    public CategoriesPage editCategoryByName(String name) {
        By editButtonLocator = By.xpath("//h3[contains(text(), '" + name + "')]/ancestor::div[@class='category-header']/div[@class='category-actions']//button[contains(@data-testid, 'edit-category')]");
        clickElement(editButtonLocator);
        waitHelper.waitForModalVisible("categoryModal");
        return this;
    }

    /**
     * Delete category by name
     */
    public CategoriesPage deleteCategoryByName(String name) {
        By deleteButtonLocator = By.xpath("//h3[contains(text(), '" + name + "')]/ancestor::div[@class='category-header']/div[@class='category-actions']//button[contains(@data-testid, 'delete-category')]");
        clickElement(deleteButtonLocator);
        waitHelper.waitForModalVisible("deleteModal");
        return this;
    }

    /**
     * Confirm delete
     */
    public CategoriesPage confirmDelete() {
        clickElement(confirmDeleteButton);
        waitForLoadingToComplete();
        waitHelper.waitForModalToDisappear("deleteModal");
        return this;
    }

    /**
     * Cancel delete
     */
    public CategoriesPage cancelDelete() {
        clickElement(cancelDeleteButton);
        return this;
    }

    /**
     * Close modal
     */
    public CategoriesPage closeModal() {
        clickElement(modalCloseButton);
        return this;
    }

    /**
     * Cancel category form
     */
    public CategoriesPage cancelCategoryForm() {
        clickElement(cancelCategoryButton);
        return this;
    }

    /**
     * Get category color
     */
    public String getCategoryColor(String name) {
        By colorLocator = By.xpath("//h3[contains(text(), '" + name + "')]");
        return getCssValue(driver.findElement(colorLocator), "color");
    }

    /**
     * Navigate to Dashboard
     */
    public DashboardPage navigateToDashboard() {
        clickElement(navDashboard);
        return new DashboardPage(driver);
    }

    /**
     * Navigate to Tasks
     */
    public TasksPage navigateToTasks() {
        clickElement(navTasks);
        return new TasksPage(driver);
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
