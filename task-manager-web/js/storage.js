/**
 * TASK MANAGER - STORAGE UTILITIES
 * LocalStorage management for users, tasks, and categories
 */

const Storage = {
    // Storage Keys
    KEYS: {
        USERS: 'taskmanager_users',
        CURRENT_USER: 'taskmanager_current_user',
        TASKS: 'taskmanager_tasks',
        CATEGORIES: 'taskmanager_categories',
        INITIALIZED: 'taskmanager_initialized'
    },

    // === INITIALIZATION ===
    init() {
        // Check if already initialized
        if (!localStorage.getItem(this.KEYS.INITIALIZED)) {
            this.initializeDefaultData();
            localStorage.setItem(this.KEYS.INITIALIZED, 'true');
        }
    },

    initializeDefaultData() {
        // Create default admin user
        const defaultUser = {
            id: generateId(),
            username: 'admin',
            password: 'admin123', // In production, this should be hashed
            fullName: 'Admin User',
            email: 'admin@taskmanager.com',
            createdAt: new Date().toISOString()
        };
        localStorage.setItem(this.KEYS.USERS, JSON.stringify([defaultUser]));

        // Create default categories
        const defaultCategories = [
            { id: generateId(), name: 'Work', color: '#eab308', taskCount: 0 },
            { id: generateId(), name: 'Personal', color: '#10b981', taskCount: 0 },
            { id: generateId(), name: 'Shopping', color: '#f59e0b', taskCount: 0 },
            { id: generateId(), name: 'Health', color: '#ef4444', taskCount: 0 }
        ];
        localStorage.setItem(this.KEYS.CATEGORIES, JSON.stringify(defaultCategories));

        // Create 15 sample tasks
        const today = new Date();
        const tomorrow = new Date(today);
        tomorrow.setDate(tomorrow.getDate() + 1);
        const nextWeek = new Date(today);
        nextWeek.setDate(nextWeek.getDate() + 7);
        const yesterday = new Date(today);
        yesterday.setDate(yesterday.getDate() - 1);

        const sampleTasks = [
            // High Priority Tasks (5)
            {
                id: generateId(),
                title: 'Complete Project Presentation',
                description: 'Prepare and finalize the Q4 project presentation slides',
                priority: 'High',
                dueDate: tomorrow.toISOString().split('T')[0],
                category: defaultCategories[0].name,
                status: 'Incomplete',
                createdAt: new Date().toISOString()
            },
            {
                id: generateId(),
                title: 'Client Meeting Preparation',
                description: 'Gather all documents and prepare agenda for client meeting',
                priority: 'High',
                dueDate: today.toISOString().split('T')[0],
                category: defaultCategories[0].name,
                status: 'Incomplete',
                createdAt: new Date().toISOString()
            },
            {
                id: generateId(),
                title: 'Fix Critical Bug in Production',
                description: 'Debug and fix the login issue reported by users',
                priority: 'High',
                dueDate: today.toISOString().split('T')[0],
                category: defaultCategories[0].name,
                status: 'Complete',
                createdAt: new Date().toISOString()
            },
            {
                id: generateId(),
                title: 'Submit Tax Documents',
                description: 'Compile and submit all required tax documentation',
                priority: 'High',
                dueDate: nextWeek.toISOString().split('T')[0],
                category: defaultCategories[1].name,
                status: 'Incomplete',
                createdAt: new Date().toISOString()
            },
            {
                id: generateId(),
                title: 'Renew Insurance Policy',
                description: 'Review and renew health insurance policy before expiration',
                priority: 'High',
                dueDate: nextWeek.toISOString().split('T')[0],
                category: defaultCategories[3].name,
                status: 'Complete',
                createdAt: new Date().toISOString()
            },
            // Medium Priority Tasks (5)
            {
                id: generateId(),
                title: 'Update Project Documentation',
                description: 'Update the README and API documentation for the project',
                priority: 'Medium',
                dueDate: nextWeek.toISOString().split('T')[0],
                category: defaultCategories[0].name,
                status: 'Incomplete',
                createdAt: new Date().toISOString()
            },
            {
                id: generateId(),
                title: 'Code Review for New Feature',
                description: 'Review pull request #234 for the new authentication feature',
                priority: 'Medium',
                dueDate: tomorrow.toISOString().split('T')[0],
                category: defaultCategories[0].name,
                status: 'Complete',
                createdAt: new Date().toISOString()
            },
            {
                id: generateId(),
                title: 'Plan Weekend Grocery Shopping',
                description: 'Create shopping list for weekend groceries',
                priority: 'Medium',
                dueDate: tomorrow.toISOString().split('T')[0],
                category: defaultCategories[2].name,
                status: 'Incomplete',
                createdAt: new Date().toISOString()
            },
            {
                id: generateId(),
                title: 'Organize Home Office',
                description: 'Clean and reorganize the home office workspace',
                priority: 'Medium',
                dueDate: nextWeek.toISOString().split('T')[0],
                category: defaultCategories[1].name,
                status: 'Complete',
                createdAt: new Date().toISOString()
            },
            {
                id: generateId(),
                title: 'Schedule Dental Checkup',
                description: 'Book appointment for 6-month dental checkup',
                priority: 'Medium',
                dueDate: nextWeek.toISOString().split('T')[0],
                category: defaultCategories[3].name,
                status: 'Complete',
                createdAt: new Date().toISOString()
            },
            // Low Priority Tasks (5)
            {
                id: generateId(),
                title: 'Read New Technical Book',
                description: 'Start reading "Clean Code" by Robert Martin',
                priority: 'Low',
                dueDate: nextWeek.toISOString().split('T')[0],
                category: defaultCategories[1].name,
                status: 'Incomplete',
                createdAt: new Date().toISOString()
            },
            {
                id: generateId(),
                title: 'Research New Framework',
                description: 'Explore Next.js framework for potential future projects',
                priority: 'Low',
                dueDate: nextWeek.toISOString().split('T')[0],
                category: defaultCategories[0].name,
                status: 'Incomplete',
                createdAt: new Date().toISOString()
            },
            {
                id: generateId(),
                title: 'Update LinkedIn Profile',
                description: 'Add recent projects and skills to LinkedIn profile',
                priority: 'Low',
                dueDate: yesterday.toISOString().split('T')[0],
                category: defaultCategories[1].name,
                status: 'Complete',
                createdAt: new Date().toISOString()
            },
            {
                id: generateId(),
                title: 'Buy New Office Chair',
                description: 'Research and purchase ergonomic office chair',
                priority: 'Low',
                dueDate: nextWeek.toISOString().split('T')[0],
                category: defaultCategories[2].name,
                status: 'Incomplete',
                createdAt: new Date().toISOString()
            },
            {
                id: generateId(),
                title: 'Water Plants',
                description: 'Water all indoor and outdoor plants',
                priority: 'Low',
                dueDate: today.toISOString().split('T')[0],
                category: defaultCategories[1].name,
                status: 'Complete',
                createdAt: new Date().toISOString()
            }
        ];

        localStorage.setItem(this.KEYS.TASKS, JSON.stringify(sampleTasks));
    },

    // === USER MANAGEMENT ===
    getUsers() {
        return JSON.parse(localStorage.getItem(this.KEYS.USERS) || '[]');
    },

    addUser(user) {
        const users = this.getUsers();
        const newUser = {
            id: generateId(),
            ...user,
            createdAt: new Date().toISOString()
        };
        users.push(newUser);
        localStorage.setItem(this.KEYS.USERS, JSON.stringify(users));
        return newUser;
    },

    getUserByUsername(username) {
        const users = this.getUsers();
        return users.find(u => u.username.toLowerCase() === username.toLowerCase());
    },

    getUserByEmail(email) {
        const users = this.getUsers();
        return users.find(u => u.email.toLowerCase() === email.toLowerCase());
    },

    // === AUTHENTICATION ===
    login(username, password) {
        const user = this.getUserByUsername(username);
        if (user && user.password === password) {
            const userData = { ...user };
            delete userData.password; // Don't store password in session
            localStorage.setItem(this.KEYS.CURRENT_USER, JSON.stringify(userData));
            return { success: true, user: userData };
        }
        return { success: false, message: 'Invalid credentials' };
    },

    logout() {
        localStorage.removeItem(this.KEYS.CURRENT_USER);
    },

    getCurrentUser() {
        const user = localStorage.getItem(this.KEYS.CURRENT_USER);
        return user ? JSON.parse(user) : null;
    },

    isLoggedIn() {
        return this.getCurrentUser() !== null;
    },

    // === TASK MANAGEMENT ===
    getTasks() {
        return JSON.parse(localStorage.getItem(this.KEYS.TASKS) || '[]');
    },

    getTaskById(id) {
        const tasks = this.getTasks();
        return tasks.find(t => t.id === id);
    },

    addTask(task) {
        const tasks = this.getTasks();
        const newTask = {
            id: generateId(),
            ...task,
            createdAt: new Date().toISOString()
        };
        tasks.unshift(newTask); // Add to beginning
        localStorage.setItem(this.KEYS.TASKS, JSON.stringify(tasks));
        return newTask;
    },

    updateTask(id, updates) {
        const tasks = this.getTasks();
        const index = tasks.findIndex(t => t.id === id);
        if (index !== -1) {
            tasks[index] = { ...tasks[index], ...updates };
            localStorage.setItem(this.KEYS.TASKS, JSON.stringify(tasks));
            return tasks[index];
        }
        return null;
    },

    deleteTask(id) {
        const tasks = this.getTasks();
        const filtered = tasks.filter(t => t.id !== id);
        localStorage.setItem(this.KEYS.TASKS, JSON.stringify(filtered));
        return true;
    },

    // === CATEGORY MANAGEMENT ===
    getCategories() {
        return JSON.parse(localStorage.getItem(this.KEYS.CATEGORIES) || '[]');
    },

    getCategoryById(id) {
        const categories = this.getCategories();
        return categories.find(c => c.id === id);
    },

    addCategory(category) {
        const categories = this.getCategories();
        const newCategory = {
            id: generateId(),
            ...category,
            taskCount: 0
        };
        categories.push(newCategory);
        localStorage.setItem(this.KEYS.CATEGORIES, JSON.stringify(categories));
        return newCategory;
    },

    updateCategory(id, updates) {
        const categories = this.getCategories();
        const index = categories.findIndex(c => c.id === id);
        if (index !== -1) {
            categories[index] = { ...categories[index], ...updates };
            localStorage.setItem(this.KEYS.CATEGORIES, JSON.stringify(categories));
            return categories[index];
        }
        return null;
    },

    deleteCategory(id) {
        const category = this.getCategoryById(id);
        if (!category) return false;

        // Update tasks with this category to "Uncategorized"
        const tasks = this.getTasks();
        tasks.forEach(task => {
            if (task.category === category.name) {
                task.category = 'Uncategorized';
            }
        });
        localStorage.setItem(this.KEYS.TASKS, JSON.stringify(tasks));

        // Delete category
        const categories = this.getCategories();
        const filtered = categories.filter(c => c.id !== id);
        localStorage.setItem(this.KEYS.CATEGORIES, JSON.stringify(filtered));
        return true;
    },

    updateCategoryTaskCounts() {
        const tasks = this.getTasks();
        const categories = this.getCategories();

        categories.forEach(category => {
            category.taskCount = tasks.filter(t => t.category === category.name).length;
        });

        localStorage.setItem(this.KEYS.CATEGORIES, JSON.stringify(categories));
    },

    // === STATISTICS ===
    getStatistics() {
        const tasks = this.getTasks();
        return {
            total: tasks.length,
            completed: tasks.filter(t => t.status === 'Complete').length,
            pending: tasks.filter(t => t.status === 'Incomplete').length,
            highPriority: tasks.filter(t => t.priority === 'High').length,
            mediumPriority: tasks.filter(t => t.priority === 'Medium').length,
            lowPriority: tasks.filter(t => t.priority === 'Low').length,
            overdue: tasks.filter(t => {
                return t.status === 'Incomplete' && isPastDate(t.dueDate);
            }).length
        };
    },

    // === DATA RESET ===
    reset() {
        Object.values(this.KEYS).forEach(key => {
            localStorage.removeItem(key);
        });
        this.init();
    }
};

// Initialize storage on load
Storage.init();

// Export for use in other files
if (typeof module !== 'undefined' && module.exports) {
    module.exports = Storage;
}
