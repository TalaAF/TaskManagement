/**
 * TASK MANAGER - UTILITY FUNCTIONS
 * Helper functions for toast notifications, loading, modals, etc.
 */

// === TOAST NOTIFICATIONS ===
function showToast(message, type = 'info', duration = 3000) {
    const toast = document.getElementById('toast');
    if (!toast) return;

    toast.textContent = message;
    toast.className = `toast show ${type}`;

    setTimeout(() => {
        toast.classList.add('hide');
        setTimeout(() => {
            toast.classList.remove('show', 'hide', type);
        }, 300);
    }, duration);
}

// === LOADING OVERLAY ===
function showLoading() {
    const overlay = document.getElementById('loadingOverlay');
    if (overlay) {
        overlay.style.display = 'flex';
    }
}

function hideLoading() {
    const overlay = document.getElementById('loadingOverlay');
    if (overlay) {
        overlay.style.display = 'none';
    }
}

// Simulate async operation with loading delay
async function simulateLoading(callback, delay = 1500) {
    showLoading();
    await new Promise(resolve => setTimeout(resolve, delay));
    const result = callback();
    hideLoading();
    return result;
}

// === BUTTON LOADING STATE ===
function setButtonLoading(button, isLoading) {
    if (!button) return;

    const btnText = button.querySelector('.btn-text');
    const btnLoader = button.querySelector('.btn-loader');

    if (isLoading) {
        button.disabled = true;
        if (btnText) btnText.style.display = 'none';
        if (btnLoader) btnLoader.style.display = 'inline-block';
    } else {
        button.disabled = false;
        if (btnText) btnText.style.display = 'inline';
        if (btnLoader) btnLoader.style.display = 'none';
    }
}

// === MODAL FUNCTIONS ===
function openModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.add('active');
        document.body.style.overflow = 'hidden';
    }
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.remove('active');
        document.body.style.overflow = 'auto';
    }
}

// Setup modal close handlers
function setupModalCloseHandlers(modalId) {
    const modal = document.getElementById(modalId);
    if (!modal) return;

    // Close on backdrop click
    modal.addEventListener('click', (e) => {
        if (e.target === modal) {
            closeModal(modalId);
        }
    });

    // Close on close button click
    const closeBtn = modal.querySelector('.modal-close');
    if (closeBtn) {
        closeBtn.addEventListener('click', () => closeModal(modalId));
    }

    // Close on ESC key
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape' && modal.classList.contains('active')) {
            closeModal(modalId);
        }
    });
}

// === DATE FORMATTING ===
function formatDate(dateString) {
    const date = new Date(dateString);
    const options = { year: 'numeric', month: 'short', day: 'numeric' };
    return date.toLocaleDateString('en-US', options);
}

function getTodayDate() {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

function isPastDate(dateString) {
    const date = new Date(dateString);
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    return date < today;
}

// === STRING UTILITIES ===
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

function truncate(text, maxLength) {
    if (text.length <= maxLength) return text;
    return text.substring(0, maxLength) + '...';
}

function capitalizeFirst(str) {
    return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
}

// === ARRAY UTILITIES ===
function sortBy(array, key, order = 'asc') {
    return array.sort((a, b) => {
        let aVal = a[key];
        let bVal = b[key];

        if (typeof aVal === 'string') {
            aVal = aVal.toLowerCase();
            bVal = bVal.toLowerCase();
        }

        if (order === 'asc') {
            return aVal > bVal ? 1 : aVal < bVal ? -1 : 0;
        } else {
            return aVal < bVal ? 1 : aVal > bVal ? -1 : 0;
        }
    });
}

function filterBySearch(array, searchTerm, fields) {
    if (!searchTerm) return array;

    const term = searchTerm.toLowerCase();
    return array.filter(item => {
        return fields.some(field => {
            const value = item[field];
            return value && value.toString().toLowerCase().includes(term);
        });
    });
}

// === ID GENERATION ===
function generateId() {
    return Date.now().toString(36) + Math.random().toString(36).substring(2);
}

// === DEBOUNCE ===
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// === CONFIRMATION DIALOG ===
function confirmAction(message) {
    return confirm(message);
}

// === ERROR HANDLING ===
function handleError(error, userMessage = 'An error occurred') {
    console.error('Error:', error);
    showToast(userMessage, 'error');
}

// === LOCAL STORAGE HELPERS ===
function setLocalStorage(key, value) {
    try {
        localStorage.setItem(key, JSON.stringify(value));
        return true;
    } catch (error) {
        handleError(error, 'Failed to save data');
        return false;
    }
}

function getLocalStorage(key, defaultValue = null) {
    try {
        const item = localStorage.getItem(key);
        return item ? JSON.parse(item) : defaultValue;
    } catch (error) {
        handleError(error, 'Failed to load data');
        return defaultValue;
    }
}

function removeLocalStorage(key) {
    try {
        localStorage.removeItem(key);
        return true;
    } catch (error) {
        handleError(error, 'Failed to remove data');
        return false;
    }
}

// === FORM UTILITIES ===
function getFormData(formId) {
    const form = document.getElementById(formId);
    if (!form) return null;

    const formData = new FormData(form);
    const data = {};

    for (let [key, value] of formData.entries()) {
        data[key] = value;
    }

    return data;
}

function resetForm(formId) {
    const form = document.getElementById(formId);
    if (form) {
        form.reset();
        // Clear error messages
        const errorMessages = form.querySelectorAll('.error-message');
        errorMessages.forEach(msg => msg.textContent = '');
        // Remove error classes
        const errorInputs = form.querySelectorAll('.error');
        errorInputs.forEach(input => input.classList.remove('error'));
    }
}

function setFormData(formId, data) {
    const form = document.getElementById(formId);
    if (!form) return;

    Object.keys(data).forEach(key => {
        const input = form.querySelector(`[name="${key}"]`);
        if (input) {
            if (input.type === 'checkbox') {
                input.checked = data[key];
            } else {
                input.value = data[key];
            }
        }
    });
}

// === PAGINATION ===
function paginate(array, page, perPage) {
    const start = (page - 1) * perPage;
    const end = start + perPage;
    return array.slice(start, end);
}

function getTotalPages(totalItems, perPage) {
    return Math.ceil(totalItems / perPage);
}

// === ELEMENT UTILITIES ===
function show(elementId) {
    const element = document.getElementById(elementId);
    if (element) element.style.display = 'block';
}

function hide(elementId) {
    const element = document.getElementById(elementId);
    if (element) element.style.display = 'none';
}

function toggle(elementId) {
    const element = document.getElementById(elementId);
    if (element) {
        element.style.display = element.style.display === 'none' ? 'block' : 'none';
    }
}

// === VALIDATION HELPERS ===
function isEmpty(value) {
    return value === null || value === undefined || value.trim() === '';
}

function isEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

function isStrongPassword(password) {
    // At least 6 characters
    return password.length >= 6;
}

// === COLOR UTILITIES ===
function getRandomColor() {
    const colors = ['#2563eb', '#10b981', '#ef4444', '#f59e0b', '#06b6d4', '#8b5cf6'];
    return colors[Math.floor(Math.random() * colors.length)];
}

// Export functions for use in other files
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        showToast,
        showLoading,
        hideLoading,
        simulateLoading,
        setButtonLoading,
        openModal,
        closeModal,
        setupModalCloseHandlers,
        formatDate,
        getTodayDate,
        isPastDate,
        escapeHtml,
        truncate,
        capitalizeFirst,
        sortBy,
        filterBySearch,
        generateId,
        debounce,
        confirmAction,
        handleError,
        setLocalStorage,
        getLocalStorage,
        removeLocalStorage,
        getFormData,
        resetForm,
        setFormData,
        paginate,
        getTotalPages,
        show,
        hide,
        toggle,
        isEmpty,
        isEmail,
        isStrongPassword,
        getRandomColor
    };
}
