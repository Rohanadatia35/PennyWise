<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
response.setHeader("Pragma", "no-cache"); // HTTP 1.0
response.setHeader("Expires", "0"); // Proxies
if (session != null) {
	session.invalidate();
}
%>
<!DOCTYPE html> 	
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>PennyWise | Login</title>
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
					<a href="Login.jsp" class="button button-outline">Login</a> <a
						href="Signup.jsp" class="button button-primary">Sign Up</a>
				</div>
			</nav>
		</div>
	</header>

	<!-- Login Form -->
	<section id="login-form" class="auth-container">
		<div class="auth-card">
			<div class="auth-header">
				<div class="logo-icon auth-logo">P</div>
				<h2 class="auth-title">Welcome Back!</h2>
				<p class="auth-subtitle">Login to manage your expenses</p>
			</div>
			<form action="Login" method="POST">
				<div class="form-group">
					<label for="login-email" class="form-label">Email</label> <input
						type="email" id="login-email" name="email" class="form-input"
						placeholder="Enter your email" required>
				</div>
				<div class="form-group">
					<label for="login-password" class="form-label">Password</label> <input
						type="password" id="login-password" name="password"
						class="form-input" placeholder="Enter your password" required>
				</div>
				<button type="submit" class="button button-primary"
					style="width: 100%;">Login</button>
				<div class="form-footer">
					Don't have an account? <a href="Signup.jsp">Sign Up</a>
				</div>
				<div class="form-footer">
					<a href="ForgotPassword.jsp">Forgot Password?</a>
				</div>
			</form>
		</div>
	</section>
</body>
</html>
\
