package com.controller;

import com.bean.UserBean;
import com.dao.UserDao;
import com.service.ProfileService;
import com.util.DbConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Connection con = DbConnection.getConnection();
    public ProfileServlet() {
        super();
        ProfileService profileService = new ProfileService(); // Initialize service
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the updated user details from the form
    	
    	HttpSession session = request.getSession(false);  // Get session if it exists
        if (session == null) {
            response.sendRedirect("Login.jsp");  // Redirect to login if no session exists
            return;
        }
//    	String userName = request.getParameter("userName");
//        String email = request.getParameter("email");

        int userId = (Integer) session.getAttribute("userId");
        // Retrieve the userId from the session
        // Retrieve the user details from the database using the userId
        UserDao userDao = new UserDao();
        UserBean userBean = userDao.findById(userId);
        String userName=userBean.getUserName();

        if (userBean != null) {
            // Update the user's information
            userBean.setUserName(userName);  // Update userName with the form data
            
            // Call the DAO method to update the user profile
            boolean isUpdated = userDao.updateUser(userBean);

            if (isUpdated) {
                // If updated successfully, redirect to profile page with success status
                 // Update session with new userName
                response.sendRedirect("Profile.jsp?status=success");
            } else {
                // If update fails, show error
                response.sendRedirect("Profile.jsp?status=error");
            }
        } else {
            // Handle case where the user is not found
            response.sendRedirect("Profile.jsp?status=user-not-found");
        }
    }
}
