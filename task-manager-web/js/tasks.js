/**
 * TASK MANAGER - TASKS PAGE
 * Task CRUD operations, filtering, search, and pagination
 */

// Check authentication
if (!Storage.isLoggedIn()) {
    window.location.href = 'index.html';
}

// === STATE ===
let currentPage = 1;
const tasksPerPage = 10;
let filteredTasks = [];
let currentTaskId = null; // For edit/delete operations

// === DOM ELEMENTS ===
const addTaskBtn = document.getElementById('addTaskBtn');
const taskModal = document.getElementById('taskModal');
const deleteModal = document.getElementById('deleteModal');
const taskForm = document.getElementById('taskForm');
const tasksTableBody = document.getElementById('tasksTableBody');
const searchInput = document.getElementById('searchInput');
const statusFilter = document.getElementById('statusFilter');
const priorityFilter = document.getElementById('priorityFilter');
const categoryFilter = document.getElementById('categoryFilter');
const clearFiltersBtn = document.getElementById('clearFiltersBtn');
const paginationContainer = document.getElementById('pagination');
const saveTaskBtn = document.getElementById('saveTaskBtn');
const confirmDeleteBtn = document.getElementById('confirmDeleteBtn');

// === INITIALIZATION ===
function init() {
    loadCategories();
    loadTasks();
    setupEventListeners();
    setupModalHandlers();
}

// === LOAD CATEGORIES INTO DROPDOWN ===
function loadCategories() {
    const categories = Storage.getCategories();
    const categorySelect = document.getElementById('taskCategory');
    const categoryFilterSelect = document.getElementById('categoryFilter');

    if (categorySelect) {
        categorySelect.innerHTML = '<option value="">Select Category</option>';
        categories.forEach(cat => {
            const option = document.createElement('option');
            option.value = cat.name;
            option.textContent = cat.name;
            categorySelect.appendChild(option);
        });
    }

    if (categoryFilterSelect) {
        categoryFilterSelect.innerHTML = '<option value="">All Categories</option>';
        categories.forEach(cat => {
            const option = document.createElement('option');
            option.value = cat.name;
            option.textContent = cat.name;
            categoryFilterSelect.appendChild(option);
        });
    }
}

// === LOAD TASKS ===
function loadTasks() {
    let tasks = Storage.getTasks();

    // Apply filters
    const searchTerm = searchInput.value.toLowerCase();
    const statusValue = statusFilter.value;
    const priorityValue = priorityFilter.value;
    const categoryValue = categoryFilter.value;

    filteredTasks = tasks.filter(task => {
        const matchesSearch = !searchTerm ||
            task.title.toLowerCase().includes(searchTerm) ||
            task.description.toLowerCase().includes(searchTerm);
        const matchesStatus = !statusValue || task.status === statusValue;
        const matchesPriority = !priorityValue || task.priority === priorityValue;
        const matchesCategory = !categoryValue || task.category === categoryValue;

        return matchesSearch && matchesStatus && matchesPriority && matchesCategory;
    });

    renderTasks();
    renderPagination();
}

// === RENDER TASKS ===
function renderTasks() {
    const startIndex = (currentPage - 1) * tasksPerPage;
    const endIndex = startIndex + tasksPerPage;
    const tasksToShow = filteredTasks.slice(startIndex, endIndex);

    if (tasksToShow.length === 0) {
        tasksTableBody.innerHTML = `
            <tr>
                <td colspan="8" class="text-center empty-state">
                    No tasks found. ${filteredTasks.length === 0 && Storage.getTasks().length > 0 ? 'Try adjusting your filters.' : 'Create your first task!'}
                </td>
            </tr>
        `;
        return;
    }

    tasksTableBody.innerHTML = tasksToShow.map(task => `
        <tr data-testid="task-row-${task.id}">
            <td>${task.id.substring(0, 8)}</td>
            <td><strong>${escapeHtml(task.title)}</strong></td>
            <td>${escapeHtml(truncate(task.description, 50))}</td>
            <td>
                <span class="badge badge-${task.priority.toLowerCase()}" data-testid="task-priority-${task.id}">
                    ${task.priority}
                </span>
            </td>
            <td>${task.dueDate}</td>
            <td>${escapeHtml(task.category)}</td>
            <td>
                <span class="task-status status-${task.status.toLowerCase()}" data-testid="task-status-${task.id}">
                    ${task.status}
                </span>
            </td>
            <td>
                <div class="action-btns">
                    <button
                        class="btn btn-sm btn-secondary"
                        onclick="editTask('${task.id}')"
                        data-testid="edit-task-${task.id}"
                        title="Edit task"
                    >
                        ✏️
                    </button>
                    <button
                        class="btn btn-sm btn-danger"
                        onclick="showDeleteModal('${task.id}')"
                        data-testid="delete-task-${task.id}"
                        title="Delete task"
                    >
                        🗑️
                    </button>
                    <button
                        class="btn btn-sm ${task.status === 'Complete' ? 'btn-warning' : 'btn-success'}"
                        onclick="toggleTaskStatus('${task.id}')"
                        data-testid="toggle-status-${task.id}"
                        title="${task.status === 'Complete' ? 'Mark as incomplete' : 'Mark as complete'}"
                    >
                        ${task.status === 'Complete' ? '↩️' : '✓'}
                    </button>
                </div>
            </td>
        </tr>
    `).join('');
}

// === RENDER PAGINATION ===
function renderPagination() {
    const totalPages = Math.ceil(filteredTasks.length / tasksPerPage);

    if (totalPages <= 1) {
        paginationContainer.innerHTML = '';
        return;
    }

    let paginationHTML = '';

    // Previous button
    paginationHTML += `
        <button
            ${currentPage === 1 ? 'disabled' : ''}
            onclick="changePage(${currentPage - 1})"
            data-testid="prev-page"
        >
            ← Previous
        </button>
    `;

    // Page numbers
    for (let i = 1; i <= totalPages; i++) {
        if (
            i === 1 ||
            i === totalPages ||
            (i >= currentPage - 2 && i <= currentPage + 2)
        ) {
            paginationHTML += `
                <button
                    class="${i === currentPage ? 'active' : ''}"
                    onclick="changePage(${i})"
                    data-testid="page-${i}"
                >
                    ${i}
                </button>
            `;
        } else if (i === currentPage - 3 || i === currentPage + 3) {
            paginationHTML += '<span>...</span>';
        }
    }

    // Next button
    paginationHTML += `
        <button
            ${currentPage === totalPages ? 'disabled' : ''}
            onclick="changePage(${currentPage + 1})"
            data-testid="next-page"
        >
            Next →
        </button>
    `;

    paginationContainer.innerHTML = paginationHTML;
}

// === CHANGE PAGE ===
function changePage(page) {
    currentPage = page;
    renderTasks();
    renderPagination();
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

// === SHOW ADD TASK MODAL ===
function showAddTaskModal() {
    currentTaskId = null;
    document.getElementById('modalTitle').textContent = 'Add New Task';
    resetForm('taskForm');
    document.getElementById('taskStatus').value = 'Incomplete';
    openModal('taskModal');
}

// === EDIT TASK ===
function editTask(taskId) {
    currentTaskId = taskId;
    const task = Storage.getTaskById(taskId);

    if (!task) {
        showToast('Task not found', 'error');
        return;
    }

    document.getElementById('modalTitle').textContent = 'Edit Task';
    document.getElementById('taskId').value = task.id;
    document.getElementById('taskTitle').value = task.title;
    document.getElementById('taskDescription').value = task.description;
    document.getElementById('taskPriority').value = task.priority;
    document.getElementById('taskDueDate').value = task.dueDate;
    document.getElementById('taskCategory').value = task.category;
    document.getElementById('taskStatus').value = task.status;

    openModal('taskModal');
}

// === SAVE TASK (ADD/EDIT) ===
async function saveTask(e) {
    e.preventDefault();

    // Validate form
    if (!Validation.validateTask()) {
        return;
    }

    // Get form values
    const taskData = {
        title: document.getElementById('taskTitle').value.trim(),
        description: document.getElementById('taskDescription').value.trim(),
        priority: document.getElementById('taskPriority').value,
        dueDate: document.getElementById('taskDueDate').value,
        category: document.getElementById('taskCategory').value,
        status: document.getElementById('taskStatus').value
    };

    // Show loading state
    setButtonLoading(saveTaskBtn, true);

    // Simulate loading delay
    await new Promise(resolve => setTimeout(resolve, 1500));

    try {
        if (currentTaskId) {
            // Update existing task
            Storage.updateTask(currentTaskId, taskData);
            showToast('Task updated successfully!', 'success');
        } else {
            // Add new task
            Storage.addTask(taskData);
            showToast('Task created successfully!', 'success');
        }

        closeModal('taskModal');
        loadTasks();
        currentPage = 1; // Reset to first page
    } catch (error) {
        showToast('Failed to save task', 'error');
    } finally {
        setButtonLoading(saveTaskBtn, false);
    }
}

// === SHOW DELETE MODAL ===
function showDeleteModal(taskId) {
    currentTaskId = taskId;
    openModal('deleteModal');
}

// === DELETE TASK ===
async function deleteTask() {
    if (!currentTaskId) return;

    setButtonLoading(confirmDeleteBtn, true);

    // Simulate loading delay
    await new Promise(resolve => setTimeout(resolve, 1500));

    try {
        Storage.deleteTask(currentTaskId);
        showToast('Task deleted successfully!', 'success');
        closeModal('deleteModal');
        loadTasks();

        // Adjust current page if needed
        const totalPages = Math.ceil(filteredTasks.length / tasksPerPage);
        if (currentPage > totalPages && currentPage > 1) {
            currentPage = totalPages;
        }
    } catch (error) {
        showToast('Failed to delete task', 'error');
    } finally {
        setButtonLoading(confirmDeleteBtn, false);
        currentTaskId = null;
    }
}

// === TOGGLE TASK STATUS ===
async function toggleTaskStatus(taskId) {
    const task = Storage.getTaskById(taskId);
    if (!task) return;

    showLoading();

    // Simulate loading delay
    await new Promise(resolve => setTimeout(resolve, 1000));

    const newStatus = task.status === 'Complete' ? 'Incomplete' : 'Complete';
    Storage.updateTask(taskId, { status: newStatus });

    hideLoading();
    showToast(`Task marked as ${newStatus.toLowerCase()}!`, 'success');
    loadTasks();
}

// === CLEAR FILTERS ===
function clearFilters() {
    searchInput.value = '';
    statusFilter.value = '';
    priorityFilter.value = '';
    categoryFilter.value = '';
    currentPage = 1;
    loadTasks();
}

// === SETUP EVENT LISTENERS ===
function setupEventListeners() {
    // Add task button
    if (addTaskBtn) {
        addTaskBtn.addEventListener('click', showAddTaskModal);
    }

    // Task form submit
    if (taskForm) {
        taskForm.addEventListener('submit', saveTask);
    }

    // Cancel buttons
    document.getElementById('cancelTaskBtn')?.addEventListener('click', () => {
        closeModal('taskModal');
    });

    // Delete confirmation
    if (confirmDeleteBtn) {
        confirmDeleteBtn.addEventListener('click', deleteTask);
    }

    document.getElementById('cancelDeleteBtn')?.addEventListener('click', () => {
        closeModal('deleteModal');
    });

    // Search and filters
    if (searchInput) {
        searchInput.addEventListener('input', debounce(() => {
            currentPage = 1;
            loadTasks();
        }, 300));
    }

    if (statusFilter) {
        statusFilter.addEventListener('change', () => {
            currentPage = 1;
            loadTasks();
        });
    }

    if (priorityFilter) {
        priorityFilter.addEventListener('change', () => {
            currentPage = 1;
            loadTasks();
        });
    }

    if (categoryFilter) {
        categoryFilter.addEventListener('change', () => {
            currentPage = 1;
            loadTasks();
        });
    }

    if (clearFiltersBtn) {
        clearFiltersBtn.addEventListener('click', clearFilters);
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
    Validation.setupRealTimeValidation('taskTitle', [
        { rule: 'required', message: 'Task title is required' },
        { rule: 'minLength', params: [3], message: 'Title must be at least 3 characters' }
    ]);

    Validation.setupRealTimeValidation('taskDescription', [
        { rule: 'required', message: 'Description is required' },
        { rule: 'minLength', params: [10], message: 'Description must be at least 10 characters' }
    ]);
}

// === SETUP MODAL HANDLERS ===
function setupModalHandlers() {
    setupModalCloseHandlers('taskModal');
    setupModalCloseHandlers('deleteModal');
}

// === MAKE FUNCTIONS GLOBAL ===
window.editTask = editTask;
window.showDeleteModal = showDeleteModal;
window.deleteTask = deleteTask;
window.toggleTaskStatus = toggleTaskStatus;
window.changePage = changePage;

// === INITIALIZE ===
init();
