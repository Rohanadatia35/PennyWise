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
    UserBean userBean = userDao.findById(userId);
    String userName = userBean.getUserName();
    String email = userBean.getEmail();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PennyWise | Profile</title>
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
                    <a href="LogoutServlet" class="button button-outline">Logout</a>
                </div>
            </nav>
        </div>
    </header>

    <section class="auth-container">
        <div class="auth-card">
            <div class="auth-header">
                <div class="logo-icon auth-logo">P</div>
                <h2 class="auth-title">Your Profile</h2>
                <p class="auth-subtitle">Manage your account details</p>
            </div>

            <form class="auth-form" action="EditProfileServlet" method="post">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" class="form-input" value="<%= userName %>" readonly>
                </div>
                
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" class="form-input"  value="<%= email %>" readonly>
                </div>

                <button type="submit" class="button button-primary" style="width: 100%;">Update Profile</button>
            <div class="form-footer">
					<a href="ChangePassword.jsp">Change Password?</a>
				</div>
            </form>
        </div>
    </section>
</body>
</html>
