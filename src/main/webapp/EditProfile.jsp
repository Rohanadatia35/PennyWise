<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bean.UserBean"%>
<%@ page import="com.dao.UserDao"%>
<%@ page import="javax.servlet.http.HttpSession"%>

<%
    // Check if user is logged in
    if (session == null || session.getAttribute("userId") == null) {
        response.sendRedirect("Login.jsp");
        return;
    }

    Integer userId = (Integer) session.getAttribute("userId");

    UserDao userDao = new UserDao();
    UserBean user = userDao.findById(userId);
    String userName = user.getUserName();
    String email = user.getEmail();
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Edit Profile</title>
<link rel="stylesheet" href="assets/css/style.css">
</head>
<body>

    <!-- Header -->
    <header>
        <div class="container">
            <nav>
                <a href="Home.jsp" class="logo">
                    <div class="logo-icon">P</div>
                    <div class="logo-text">PennyWise</div>
                </a>
                <div class="nav-links">
                    <a href="LogoutServlet" class="button button-outline">Logout</a>
                    <a href="Profile.jsp" class="button button-primary">Profile</a>
                </div>
            </nav>
        </div>
    </header>

    <!-- Edit Profile Form -->
    <section id="edit-profile-form" class="auth-container">
        <div class="auth-card">
            <div class="auth-header">
                <div class="logo-icon auth-logo">P</div>
                <h2 class="auth-title">Edit Profile</h2>
                <p class="auth-subtitle">Edit your account details</p>
            </div>

            <form action="EditProfileServlet" method="post">
                <input type="hidden" name="userId" value="<%= userId %>">
                
                <div class="form-group">
                    <label class="form-label">Username</label>
                    <input type="text" name="editedUserName" class="form-input" value="<%= userName %>" required>
                </div>
                
                <div class="form-group">
                    <label class="form-label">Email</label>
                    <input type="email" name="editedEmail" class="form-input" value="<%= email %>" required>
                </div>

                <div class="profile-actions">
                    <button type="submit" class="button button-primary" style="width: 100%;">Save Changes</button> <br><br>
                </div>
            </form>

            <div class="profile-actions">
                <button class="button button-outline" onclick="window.location.href='Profile.jsp'" style="width: 100%;">Cancel</button>
            </div>
        </div>
    </section>

</body>
</html>
