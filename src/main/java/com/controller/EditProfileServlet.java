package com.controller;

import com.bean.UserBean;
import com.dao.UserDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/EditProfileServlet")
public class EditProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        // Retrieve form data
        int userId = (int)(session.getAttribute("userId"));
        System.out.println(userId);
        String userName = request.getParameter("editedUserName");
        String email = request.getParameter("editedEmail");

        // Update user in the database
        UserDao userDao = new UserDao();
        boolean isUpdated = userDao.editUser(userId, userName,email);

        if (isUpdated) {
        	System.out.println("update successful");
            // Update session attributes if needed
            session.setAttribute("userName", userName);
            
            RequestDispatcher rd= request.getRequestDispatcher("Profile.jsp");
            rd.forward(request,response);
           
        } else {
            response.sendRedirect("EditProfile.jsp?error=UpdateFailed");
        }
    }
}
