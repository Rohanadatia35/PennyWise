<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PennyWise | Sign Up</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
    <header>
        <div class="container">
            <nav>
                <a href="home.html" class="logo">
                    <div class="logo-icon">P</div>
                    <div class="logo-text">PennyWise</div>
                </a>
                <div class="nav-links">
                    <a href="Login.jsp" class="button button-outline">Login</a>
                    <a href="Signup.jsp" class="button button-primary">Sign Up</a>
                </div>
            </nav>
        </div>
    </header>

    <!-- Signup Form -->
    <section id="signup-form" class="auth-container">
        <div class="auth-card">
            <div class="auth-header">
                <div class="logo-icon auth-logo">P</div>
                <h2 class="auth-title">Create Account</h2>
                <p class="auth-subtitle">Start tracking your expenses today</p>
            </div>
            <form action="SignupValidationFilter">
                <div class="form-group">
                    <label for="signup-name" class="form-label">User Name</label>
                    <input type="text" id="signup-name" name="userName" class="form-input" placeholder="Enter your full name" >
                    <div style={{color:"red"}}>${userNameError}</div>
                </div>
                <div class="form-group">
                    <label for="signup-email" class="form-label">Email</label>
                    <input type="email" id="signup-email" name="email" class="form-input" placeholder="Enter your email" >
                    <div style={{color:"red"}}>${emailError}</div>
                </div>
                <div class="form-group">
                    <label for="signup-password" class="form-label">Password</label>
                    <input type="password" id="signup-password" name = "password"class="form-input" placeholder="Choose a password" >
                    <div style={{color:red}}>${passwordError}</div>
                </div>
                <div class="form-group">
                    <label for="signup-confirm" class="form-label">Confirm Password</label>
                    <input type="password" id="signup-confirm" class="form-input" name="confirmPassword" placeholder="Confirm your password" required>
                </div>
                <button type="submit" class="button button-primary" style="width: 100%;" >Create Account</button>
                <div class="form-footer">
                    Already have an account? <a href="Login.jsp">Login</a>
                </div>
            </form>
            
            
        </div>
    </section>

  
</body>
</html>