<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bean.UserBean"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="com.dao.UserDao" %>
<%
    if (session == null) {
        response.sendRedirect("Login.jsp");
        return;
    }
    
    UserDao userDao = new UserDao();
    int userId = (Integer) session.getAttribute("userId");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>PennyWise | Change Password</title>
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
			</nav>
		</div>
	</header>

	<!-- Signup Form -->
	<section id="signup-form" class="auth-container">
		<div class="auth-card">

			<form action="ChangePasswordServlet" method="post">


				<div class="form-group">
					<label for="signup-password" class="form-label">New
						Password</label> <input type="password" id="signup-password"
						name="newPassword" class="form-input"
						placeholder="Choose a password" required>
				</div>
				<div class="form-group">
					<label for="signup-confirm" class="form-label">Confirm New
						Password</label> <input type="password" id="signup-confirm"
						class="form-input" name="confirmNewPassword"
						placeholder="Confirm your password" required>
				</div>
				<button type="submit" class="button button-primary"
					style="width: 100%;">Confirm password</button>

			</form>
		</div>
	</section>


</body>
</html>