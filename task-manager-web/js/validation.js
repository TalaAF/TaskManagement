/**
 * TASK MANAGER - VALIDATION UTILITIES
 * Form validation functions
 */

const Validation = {
    // === VALIDATION RULES ===
    rules: {
        required: (value) => {
            return value !== null && value !== undefined && value.trim() !== '';
        },
        email: (value) => {
            const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return re.test(value);
        },
        minLength: (value, length) => {
            return value.length >= length;
        },
        maxLength: (value, length) => {
            return value.length <= length;
        },
        match: (value, matchValue) => {
            return value === matchValue;
        },
        alphanumeric: (value) => {
            const re = /^[a-zA-Z0-9]+$/;
            return re.test(value);
        },
        noSpecialChars: (value) => {
            const re = /^[a-zA-Z0-9\s]+$/;
            return re.test(value);
        }
    },

    // === ERROR MESSAGES ===
    messages: {
        required: 'This field is required',
        email: 'Please enter a valid email address',
        minLength: (length) => `Must be at least ${length} characters`,
        maxLength: (length) => `Must not exceed ${length} characters`,
        match: (fieldName) => `Must match ${fieldName}`,
        alphanumeric: 'Only letters and numbers are allowed',
        noSpecialChars: 'Special characters are not allowed'
    },

    // === DISPLAY ERROR ===
    showError(inputId, message) {
        const input = document.getElementById(inputId);
        const errorElement = document.getElementById(`${inputId}Error`);

        if (input) {
            input.classList.add('error');
        }

        if (errorElement) {
            errorElement.textContent = message;
            errorElement.style.display = 'block';
        }
    },

    // === CLEAR ERROR ===
    clearError(inputId) {
        const input = document.getElementById(inputId);
        const errorElement = document.getElementById(`${inputId}Error`);

        if (input) {
            input.classList.remove('error');
        }

        if (errorElement) {
            errorElement.textContent = '';
            errorElement.style.display = 'none';
        }
    },

    // === CLEAR ALL ERRORS ===
    clearAllErrors(formId) {
        const form = document.getElementById(formId);
        if (!form) return;

        const errorMessages = form.querySelectorAll('.error-message');
        errorMessages.forEach(msg => {
            msg.textContent = '';
            msg.style.display = 'none';
        });

        const errorInputs = form.querySelectorAll('.error');
        errorInputs.forEach(input => input.classList.remove('error'));
    },

    // === VALIDATE FIELD ===
    validateField(inputId, validations) {
        const input = document.getElementById(inputId);
        if (!input) return false;

        const value = input.value.trim();

        // Clear previous error
        this.clearError(inputId);

        // Run validations
        for (let validation of validations) {
            const { rule, params = [], message } = validation;

            let isValid;
            if (rule === 'required') {
                isValid = this.rules.required(value);
            } else if (rule === 'email') {
                if (value) { // Only validate email format if value exists
                    isValid = this.rules.email(value);
                } else {
                    continue; // Skip if empty (required rule handles empty)
                }
            } else if (rule === 'minLength') {
                if (value) {
                    isValid = this.rules.minLength(value, params[0]);
                } else {
                    continue;
                }
            } else if (rule === 'maxLength') {
                if (value) {
                    isValid = this.rules.maxLength(value, params[0]);
                } else {
                    continue;
                }
            } else if (rule === 'match') {
                const matchInput = document.getElementById(params[0]);
                isValid = this.rules.match(value, matchInput ? matchInput.value.trim() : '');
            } else if (rule === 'alphanumeric') {
                if (value) {
                    isValid = this.rules.alphanumeric(value);
                } else {
                    continue;
                }
            } else if (rule === 'noSpecialChars') {
                if (value) {
                    isValid = this.rules.noSpecialChars(value);
                } else {
                    continue;
                }
            }

            if (!isValid) {
                this.showError(inputId, message);
                return false;
            }
        }

        return true;
    },

    // === LOGIN VALIDATION ===
    validateLogin() {
        let isValid = true;

        const usernameValid = this.validateField('username', [
            { rule: 'required', message: 'Username is required' }
        ]);

        const passwordValid = this.validateField('password', [
            { rule: 'required', message: 'Password is required' }
        ]);

        return usernameValid && passwordValid;
    },

    // === REGISTRATION VALIDATION ===
    validateRegistration() {
        let isValid = true;

        const fullNameValid = this.validateField('fullName', [
            { rule: 'required', message: 'Full name is required' },
            { rule: 'minLength', params: [3], message: 'Name must be at least 3 characters' }
        ]);

        const emailValid = this.validateField('email', [
            { rule: 'required', message: 'Email is required' },
            { rule: 'email', message: 'Please enter a valid email address' }
        ]);

        const usernameValid = this.validateField('regUsername', [
            { rule: 'required', message: 'Username is required' },
            { rule: 'minLength', params: [3], message: 'Username must be at least 3 characters' },
            { rule: 'alphanumeric', message: 'Username can only contain letters and numbers' }
        ]);

        const passwordValid = this.validateField('regPassword', [
            { rule: 'required', message: 'Password is required' },
            { rule: 'minLength', params: [6], message: 'Password must be at least 6 characters' }
        ]);

        const confirmPasswordValid = this.validateField('confirmPassword', [
            { rule: 'required', message: 'Please confirm your password' },
            { rule: 'match', params: ['regPassword'], message: 'Passwords do not match' }
        ]);

        return fullNameValid && emailValid && usernameValid && passwordValid && confirmPasswordValid;
    },

    // === TASK VALIDATION ===
    validateTask() {
        const titleValid = this.validateField('taskTitle', [
            { rule: 'required', message: 'Task title is required' },
            { rule: 'minLength', params: [3], message: 'Title must be at least 3 characters' },
            { rule: 'maxLength', params: [100], message: 'Title must not exceed 100 characters' }
        ]);

        const descriptionValid = this.validateField('taskDescription', [
            { rule: 'required', message: 'Description is required' },
            { rule: 'minLength', params: [10], message: 'Description must be at least 10 characters' }
        ]);

        const priorityValid = this.validateField('taskPriority', [
            { rule: 'required', message: 'Please select a priority' }
        ]);

        const dueDateValid = this.validateField('taskDueDate', [
            { rule: 'required', message: 'Due date is required' }
        ]);

        const categoryValid = this.validateField('taskCategory', [
            { rule: 'required', message: 'Please select a category' }
        ]);

        return titleValid && descriptionValid && priorityValid && dueDateValid && categoryValid;
    },

    // === CATEGORY VALIDATION ===
    validateCategory() {
        const nameValid = this.validateField('categoryName', [
            { rule: 'required', message: 'Category name is required' },
            { rule: 'minLength', params: [2], message: 'Name must be at least 2 characters' },
            { rule: 'maxLength', params: [30], message: 'Name must not exceed 30 characters' }
        ]);

        const colorValid = this.validateField('categoryColor', [
            { rule: 'required', message: 'Please select a color' }
        ]);

        return nameValid && colorValid;
    },

    // === REAL-TIME VALIDATION ===
    setupRealTimeValidation(inputId, validations) {
        const input = document.getElementById(inputId);
        if (!input) return;

        // Validate on blur
        input.addEventListener('blur', () => {
            this.validateField(inputId, validations);
        });

        // Clear error on focus
        input.addEventListener('focus', () => {
            this.clearError(inputId);
        });

        // Validate on input (debounced)
        let timeout;
        input.addEventListener('input', () => {
            clearTimeout(timeout);
            timeout = setTimeout(() => {
                if (input.value.trim()) {
                    this.validateField(inputId, validations);
                } else {
                    this.clearError(inputId);
                }
            }, 500);
        });
    }
};

// Export for use in other files
if (typeof module !== 'undefined' && module.exports) {
    module.exports = Validation;
}
