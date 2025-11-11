/**
 * TASK MANAGER - AUTHENTICATION
 * Login and Registration functionality
 */

// === LOGIN PAGE ===
if (window.location.pathname.endsWith('index.html') || window.location.pathname === '/') {
    // Redirect if already logged in
    if (Storage.isLoggedIn()) {
        window.location.href = 'dashboard.html';
    }

    const loginForm = document.getElementById('loginForm');
    const loginButton = document.getElementById('loginButton');
    const loginErrorBox = document.getElementById('loginError');

    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            // Clear previous errors
            Validation.clearAllErrors('loginForm');
            loginErrorBox.style.display = 'none';

            // Validate form
            if (!Validation.validateLogin()) {
                return;
            }

            // Get form values
            const username = document.getElementById('username').value.trim();
            const password = document.getElementById('password').value.trim();
            const rememberMe = document.getElementById('rememberMe').checked;

            // Show loading state
            setButtonLoading(loginButton, true);

            // Simulate loading delay
            await new Promise(resolve => setTimeout(resolve, 1500));

            // Attempt login
            const result = Storage.login(username, password);

            if (result.success) {
                showToast('Login successful! Redirecting...', 'success');

                // Redirect to dashboard
                setTimeout(() => {
                    window.location.href = 'dashboard.html';
                }, 1000);
            } else {
                setButtonLoading(loginButton, false);
                loginErrorBox.style.display = 'block';
                loginErrorBox.querySelector('[data-testid="error-message"]').textContent = result.message;
                showToast(result.message, 'error');
            }
        });

        // Real-time validation
        Validation.setupRealTimeValidation('username', [
            { rule: 'required', message: 'Username is required' }
        ]);

        Validation.setupRealTimeValidation('password', [
            { rule: 'required', message: 'Password is required' }
        ]);
    }
}

// === REGISTRATION PAGE ===
if (window.location.pathname.endsWith('register.html')) {
    // Redirect if already logged in
    if (Storage.isLoggedIn()) {
        window.location.href = 'dashboard.html';
    }

    const registerForm = document.getElementById('registerForm');
    const registerButton = document.getElementById('registerButton');
    const registerErrorBox = document.getElementById('registerError');

    if (registerForm) {
        registerForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            // Clear previous errors
            Validation.clearAllErrors('registerForm');
            registerErrorBox.style.display = 'none';

            // Validate form
            if (!Validation.validateRegistration()) {
                return;
            }

            // Get form values
            const fullName = document.getElementById('fullName').value.trim();
            const email = document.getElementById('email').value.trim();
            const username = document.getElementById('regUsername').value.trim();
            const password = document.getElementById('regPassword').value.trim();

            // Check if username already exists
            if (Storage.getUserByUsername(username)) {
                Validation.showError('regUsername', 'Username already exists');
                showToast('Username already exists', 'error');
                return;
            }

            // Check if email already exists
            if (Storage.getUserByEmail(email)) {
                Validation.showError('email', 'Email already registered');
                showToast('Email already registered', 'error');
                return;
            }

            // Show loading state
            setButtonLoading(registerButton, true);

            // Simulate loading delay
            await new Promise(resolve => setTimeout(resolve, 1500));

            // Create new user
            try {
                const newUser = Storage.addUser({
                    fullName,
                    email,
                    username,
                    password
                });

                showToast('Registration successful! Redirecting to login...', 'success');

                // Redirect to login page
                setTimeout(() => {
                    window.location.href = 'index.html';
                }, 1500);
            } catch (error) {
                setButtonLoading(registerButton, false);
                registerErrorBox.style.display = 'block';
                registerErrorBox.querySelector('[data-testid="register-error-message"]').textContent =
                    'Registration failed. Please try again.';
                showToast('Registration failed', 'error');
            }
        });

        // Real-time validation
        Validation.setupRealTimeValidation('fullName', [
            { rule: 'required', message: 'Full name is required' },
            { rule: 'minLength', params: [3], message: 'Name must be at least 3 characters' }
        ]);

        Validation.setupRealTimeValidation('email', [
            { rule: 'required', message: 'Email is required' },
            { rule: 'email', message: 'Please enter a valid email address' }
        ]);

        Validation.setupRealTimeValidation('regUsername', [
            { rule: 'required', message: 'Username is required' },
            { rule: 'minLength', params: [3], message: 'Username must be at least 3 characters' },
            { rule: 'alphanumeric', message: 'Username can only contain letters and numbers' }
        ]);

        Validation.setupRealTimeValidation('regPassword', [
            { rule: 'required', message: 'Password is required' },
            { rule: 'minLength', params: [6], message: 'Password must be at least 6 characters' }
        ]);

        Validation.setupRealTimeValidation('confirmPassword', [
            { rule: 'required', message: 'Please confirm your password' },
            { rule: 'match', params: ['regPassword'], message: 'Passwords do not match' }
        ]);
    }
}
