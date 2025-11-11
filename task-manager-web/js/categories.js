/**
 * TASK MANAGER - CATEGORIES PAGE
 * Category CRUD operations
 */

// Check authentication
if (!Storage.isLoggedIn()) {
    window.location.href = 'index.html';
}

// === STATE ===
let currentCategoryId = null; // For edit/delete operations

// === DOM ELEMENTS ===
const addCategoryBtn = document.getElementById('addCategoryBtn');
const categoryModal = document.getElementById('categoryModal');
const deleteModal = document.getElementById('deleteModal');
const categoryForm = document.getElementById('categoryForm');
const categoriesGrid = document.getElementById('categoriesGrid');
const saveCategoryBtn = document.getElementById('saveCategoryBtn');
const confirmDeleteBtn = document.getElementById('confirmDeleteBtn');
const categoryColorInput = document.getElementById('categoryColor');
const colorPreview = document.getElementById('colorPreview');

// === INITIALIZATION ===
function init() {
    loadCategories();
    setupEventListeners();
    setupModalHandlers();
    Storage.updateCategoryTaskCounts();
}

// === LOAD CATEGORIES ===
function loadCategories() {
    Storage.updateCategoryTaskCounts();
    const categories = Storage.getCategories();

    if (categories.length === 0) {
        categoriesGrid.innerHTML = `
            <div class="empty-state" style="grid-column: 1 / -1;">
                No categories yet. Create your first category!
            </div>
        `;
        return;
    }

    categoriesGrid.innerHTML = categories.map(category => `
        <div class="category-card" style="border-left-color: ${category.color}" data-testid="category-${category.id}">
            <div class="category-header">
                <h3 class="category-name" style="color: ${category.color}">
                    ${escapeHtml(category.name)}
                </h3>
                <div class="category-actions">
                    <button
                        class="btn btn-icon btn-secondary"
                        onclick="editCategory('${category.id}')"
                        data-testid="edit-category-${category.id}"
                        title="Edit category"
                    >
                        ✏️
                    </button>
                    <button
                        class="btn btn-icon btn-danger"
                        onclick="showDeleteModal('${category.id}')"
                        data-testid="delete-category-${category.id}"
                        title="Delete category"
                    >
                        🗑️
                    </button>
                </div>
            </div>
            <div class="category-info">
                <p><strong>Color:</strong> <span style="color: ${category.color}">${category.color}</span></p>
                <p><strong>Tasks:</strong> ${category.taskCount || 0}</p>
            </div>
        </div>
    `).join('');
}

// === SHOW ADD CATEGORY MODAL ===
function showAddCategoryModal() {
    currentCategoryId = null;
    document.getElementById('modalTitle').textContent = 'Add New Category';
    resetForm('categoryForm');
    document.getElementById('categoryColor').value = '#2563eb';
    colorPreview.textContent = '#2563eb';
    openModal('categoryModal');
}

// === EDIT CATEGORY ===
function editCategory(categoryId) {
    currentCategoryId = categoryId;
    const category = Storage.getCategoryById(categoryId);

    if (!category) {
        showToast('Category not found', 'error');
        return;
    }

    document.getElementById('modalTitle').textContent = 'Edit Category';
    document.getElementById('categoryId').value = category.id;
    document.getElementById('categoryName').value = category.name;
    document.getElementById('categoryColor').value = category.color;
    colorPreview.textContent = category.color;

    openModal('categoryModal');
}

// === SAVE CATEGORY (ADD/EDIT) ===
async function saveCategory(e) {
    e.preventDefault();

    // Validate form
    if (!Validation.validateCategory()) {
        return;
    }

    // Get form values
    const categoryData = {
        name: document.getElementById('categoryName').value.trim(),
        color: document.getElementById('categoryColor').value
    };

    // Check if category name already exists (case insensitive)
    const categories = Storage.getCategories();
    const existingCategory = categories.find(cat =>
        cat.name.toLowerCase() === categoryData.name.toLowerCase() &&
        cat.id !== currentCategoryId
    );

    if (existingCategory) {
        Validation.showError('categoryName', 'Category name already exists');
        showToast('Category name already exists', 'error');
        return;
    }

    // Show loading state
    setButtonLoading(saveCategoryBtn, true);

    // Simulate loading delay
    await new Promise(resolve => setTimeout(resolve, 1500));

    try {
        if (currentCategoryId) {
            // Update existing category
            const oldCategory = Storage.getCategoryById(currentCategoryId);
            Storage.updateCategory(currentCategoryId, categoryData);

            // Update tasks with old category name to new name
            if (oldCategory.name !== categoryData.name) {
                const tasks = Storage.getTasks();
                tasks.forEach(task => {
                    if (task.category === oldCategory.name) {
                        Storage.updateTask(task.id, { category: categoryData.name });
                    }
                });
            }

            showToast('Category updated successfully!', 'success');
        } else {
            // Add new category
            Storage.addCategory(categoryData);
            showToast('Category created successfully!', 'success');
        }

        closeModal('categoryModal');
        loadCategories();
    } catch (error) {
        showToast('Failed to save category', 'error');
    } finally {
        setButtonLoading(saveCategoryBtn, false);
    }
}

// === SHOW DELETE MODAL ===
function showDeleteModal(categoryId) {
    currentCategoryId = categoryId;
    openModal('deleteModal');
}

// === DELETE CATEGORY ===
async function deleteCategory() {
    if (!currentCategoryId) return;

    setButtonLoading(confirmDeleteBtn, true);

    // Simulate loading delay
    await new Promise(resolve => setTimeout(resolve, 1500));

    try {
        Storage.deleteCategory(currentCategoryId);
        showToast('Category deleted successfully!', 'success');
        closeModal('deleteModal');
        loadCategories();
    } catch (error) {
        showToast('Failed to delete category', 'error');
    } finally {
        setButtonLoading(confirmDeleteBtn, false);
        currentCategoryId = null;
    }
}

// === SETUP EVENT LISTENERS ===
function setupEventListeners() {
    // Add category button
    if (addCategoryBtn) {
        addCategoryBtn.addEventListener('click', showAddCategoryModal);
    }

    // Category form submit
    if (categoryForm) {
        categoryForm.addEventListener('submit', saveCategory);
    }

    // Cancel buttons
    document.getElementById('cancelCategoryBtn')?.addEventListener('click', () => {
        closeModal('categoryModal');
    });

    // Delete confirmation
    if (confirmDeleteBtn) {
        confirmDeleteBtn.addEventListener('click', deleteCategory);
    }

    document.getElementById('cancelDeleteBtn')?.addEventListener('click', () => {
        closeModal('deleteModal');
    });

    // Color picker - update preview
    if (categoryColorInput && colorPreview) {
        categoryColorInput.addEventListener('input', (e) => {
            colorPreview.textContent = e.target.value;
            colorPreview.style.color = e.target.value;
        });
    }

    // Logout
    document.getElementById('logoutBtn')?.addEventListener('click', (e) => {
        e.preventDefault();
        if (confirm('Are you sure you want to logout?')) {
            Storage.logout();
            showToast('Logged out successfully', 'success');
            setTimeout(() => {
                window.location.href = 'index.html';
            }, 1000);
        }
    });

    // Real-time validation
    Validation.setupRealTimeValidation('categoryName', [
        { rule: 'required', message: 'Category name is required' },
        { rule: 'minLength', params: [2], message: 'Name must be at least 2 characters' }
    ]);
}

// === SETUP MODAL HANDLERS ===
function setupModalHandlers() {
    setupModalCloseHandlers('categoryModal');
    setupModalCloseHandlers('deleteModal');
}

// === MAKE FUNCTIONS GLOBAL ===
window.editCategory = editCategory;
window.showDeleteModal = showDeleteModal;
window.deleteCategory = deleteCategory;

// === INITIALIZE ===
init();
