# Task Manager Web Application

A fully functional Task Management System built with pure HTML5, CSS3, and Vanilla JavaScript for Selenium WebDriver testing practice.

## 🎯 Features

### Authentication
- ✅ User Login with validation
- ✅ User Registration with form validation
- ✅ Session management with LocalStorage
- ✅ Default admin account (username: `admin`, password: `admin123`)

### Dashboard
- ✅ Welcome message with username
- ✅ Statistics cards (Total, Completed, Pending, High Priority tasks)
- ✅ Quick action buttons
- ✅ Recent tasks preview

### Task Management
- ✅ Create, Read, Update, Delete (CRUD) operations
- ✅ Search tasks by title/description
- ✅ Filter by Status (Complete/Incomplete)
- ✅ Filter by Priority (High/Medium/Low)
- ✅ Filter by Category
- ✅ Pagination (10 tasks per page)
- ✅ Mark tasks as complete/incomplete
- ✅ Priority badges with color coding
- ✅ Due date tracking

### Category Management
- ✅ Create, Edit, Delete categories
- ✅ Color picker for categories
- ✅ Task count per category
- ✅ Auto-update tasks when category is renamed/deleted

### UI/UX Features
- ✅ Loading spinners with 1-2 second delays
- ✅ Toast notifications for all actions
- ✅ Modal dialogs for forms and confirmations
- ✅ Responsive design (desktop and tablet)
- ✅ Form validation with error messages
- ✅ Smooth animations and transitions
- ✅ Professional color scheme

## 📁 Project Structure

```
task-manager-web/
├── index.html              # Login page
├── register.html           # Registration page
├── dashboard.html          # Dashboard with statistics
├── tasks.html              # Task management page
├── categories.html         # Category management page
├── css/
│   ├── style.css          # Main styles, layouts, components
│   ├── forms.css          # Form styling
│   └── components.css     # Modals, buttons, toast notifications
├── js/
│   ├── utils.js           # Utility functions (toast, loading, etc.)
│   ├── storage.js         # LocalStorage operations
│   ├── validation.js      # Form validation logic
│   ├── auth.js            # Login/Registration logic
│   ├── tasks.js           # Task CRUD operations
│   └── categories.js      # Category management
└── README.md              # This file
```

## 🚀 Getting Started

### 1. Open the Application

Simply open `index.html` in a web browser. No server required!

```bash
cd task-manager-web
# Open index.html in your browser
# Or use a local server:
python -m http.server 8000
# Then visit: http://localhost:8000
```

### 2. Login

**Default Credentials:**
- Username: `admin`
- Password: `admin123`

Or register a new account.

### 3. Explore Features

- View the dashboard with task statistics
- Create new tasks with priorities and due dates
- Filter and search tasks
- Manage categories with custom colors
- Mark tasks as complete/incomplete

## 📊 Sample Data

The application comes pre-loaded with:
- **15 sample tasks** with varying:
  - 5 High priority tasks
  - 5 Medium priority tasks
  - 5 Low priority tasks
  - 8 Incomplete tasks
  - 7 Complete tasks
- **4 default categories**: Work, Personal, Shopping, Health
- **1 admin user** for testing

## 🧪 Selenium Test Identifiers

All interactive elements have `data-testid` attributes for easy Selenium testing:

### Login Page
- `data-testid="login-username"`
- `data-testid="login-password"`
- `data-testid="remember-me"`
- `data-testid="login-button"`
- `data-testid="error-message"`

### Tasks Page
- `data-testid="add-task-button"`
- `data-testid="search-input"`
- `data-testid="status-filter"`
- `data-testid="priority-filter"`
- `data-testid="category-filter"`
- `data-testid="task-title"`
- `data-testid="task-description"`
- `data-testid="task-priority"`
- `data-testid="save-task"`
- `data-testid="edit-task-{id}"`
- `data-testid="delete-task-{id}"`
- `data-testid="toggle-status-{id}"`

### Categories Page
- `data-testid="add-category-button"`
- `data-testid="category-name"`
- `data-testid="category-color"`
- `data-testid="save-category"`

## 🎨 Color Scheme

- **Primary Blue**: `#2563eb` - Main actions, navigation
- **Success Green**: `#10b981` - Success states, completed tasks
- **Danger Red**: `#ef4444` - Delete actions, errors, high priority
- **Warning Orange**: `#f59e0b` - Medium priority, incomplete tasks
- **Info Cyan**: `#06b6d4` - Low priority, informational

## 💾 Data Persistence

All data is stored in browser LocalStorage:
- Users and authentication sessions
- Tasks with all properties
- Categories with colors and task counts
- Data persists across browser sessions

### Reset Data

To reset all data to default, open browser console and run:
```javascript
Storage.reset();
location.reload();
```

## ✨ Key Features for Testing

### 1. Loading States
All CRUD operations have 1-2 second delays to simulate real API calls:
- Login/Register
- Add/Edit/Delete tasks
- Add/Edit/Delete categories

### 2. Form Validation
- Real-time validation on input
- Clear error messages
- Required field validation
- Email format validation
- Password strength validation
- Matching password confirmation

### 3. Toast Notifications
All actions show toast notifications:
- Success (green)
- Error (red)
- Warning (orange)
- Info (blue)

### 4. Modal Dialogs
- Add/Edit task modal
- Delete confirmation modal
- Add/Edit category modal
- Close on backdrop click, ESC key, or close button

### 5. Filtering & Search
- Real-time search (debounced)
- Multiple filter criteria
- Clear all filters button
- Maintains pagination on filter changes

### 6. Pagination
- 10 tasks per page
- Previous/Next buttons
- Page number buttons
- Smart ellipsis for many pages

## 🔒 Security Notes

**⚠️ This is a DEMO application for testing purposes:**
- Passwords are stored in plain text (DO NOT use in production)
- No server-side validation
- LocalStorage is not encrypted
- For educational/testing purposes only

## 🛠️ Technologies Used

- **HTML5**: Semantic markup, accessibility
- **CSS3**: Grid, Flexbox, animations, transitions
- **Vanilla JavaScript**: No frameworks or libraries
- **LocalStorage API**: Client-side data persistence

## 📱 Browser Compatibility

Tested on:
- Chrome 90+
- Firefox 88+
- Edge 90+
- Safari 14+

## 🎓 Perfect for Selenium Testing

This application is specifically designed for Selenium WebDriver testing practice:

✅ All elements have proper IDs and test attributes
✅ Loading states to practice waits
✅ Modal dialogs for complex interactions
✅ Form validation for error handling tests
✅ Pagination for navigation testing
✅ Filters and search for dynamic content testing
✅ CRUD operations for comprehensive test coverage

## 📝 Test Scenarios Covered

1. ✅ Login with valid/invalid credentials
2. ✅ User registration with validation
3. ✅ Create new task
4. ✅ Edit existing task
5. ✅ Delete task with confirmation
6. ✅ Mark task as complete/incomplete
7. ✅ Search tasks by keyword
8. ✅ Filter tasks by priority/status/category
9. ✅ Pagination navigation
10. ✅ Create and manage categories
11. ✅ Form validation error handling
12. ✅ Logout functionality

## 🤝 Contributing

This is a educational project for learning Selenium WebDriver testing.

## 📄 License

Free to use for educational and testing purposes.

---

**Happy Testing! 🚀**

Default Login: `admin` / `admin123`
