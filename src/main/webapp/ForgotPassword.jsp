<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PennyWise | Forgot Password</title>
    
    <!-- Link to the same stylesheet as Login Page -->
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
    <header>
        <div class="container">
            <nav>
                <a href="Home.jsp" class="logo">
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

    <!-- Forgot Password Form -->
    <section id="forgot-password-form" class="auth-container">
        <div class="auth-card">
            <div class="auth-header">
                <div class="logo-icon auth-logo">P</div>
                <h2 class="auth-title">Account Recovery</h2>
                <p class="auth-subtitle">Enter your email to receive a password reset link</p>
            </div>

            <form action="ForgetPasswordServlet" method="post">
                <div class="form-group">
                    <label for="email" class="form-label">Email Address</label>
                    <input type="email" id="email" name="email" class="form-input" placeholder="Enter your email" required>
                </div>

                <button type="submit" class="button button-primary" style="width: 100%;">Send Password</button>
                
                <!-- Back to login -->
                <div class="form-footer">
                    <a href="Login.jsp">Back to Login</a>
                </div>
            </form>
        </div>
    </section>
</body>
</html>
