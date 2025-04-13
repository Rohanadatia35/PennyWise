package com.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.hibernate.internal.build.AllowSysOut;

import com.bean.UserBean;
import com.dao.UserDao;
import com.util.Bcrypt;
import com.util.DbConnection;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private final Connection con = DbConnection.getConnection();
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDao userDao = new UserDao();
        UserBean userBean = userDao.authenticate(email);

        if (userBean == null) {
        	System.out.println("user not exists");
            response.sendRedirect("Login.jsp");  // Incorrect email
            System.out.println("Incorrect email");
        } else {
            Bcrypt bcrypt = new Bcrypt();

            boolean isPasswordCorrect = bcrypt.checkPassword(password, userBean.getPassword());
            System.out.println("Password Match: " + isPasswordCorrect);
            if (isPasswordCorrect) {
                // Store user data in session
                HttpSession session = request.getSession();
                session.setAttribute("userId", userBean.getUserId());
                session.setAttribute("emailCounter", 0);
//                session.setAttribute(password, userBean);
//                session.setAttribute("userName", userBean.getUserName());
                // Redirect to Home.jsp after successful login
//                response.reset();
                request.getRequestDispatcher("GetExpenseIncomeServlet").forward(request, response);
            } else {
                response.sendRedirect("Login.jsp");  // Incorrect password
                System.out.println("Incorrect password");
            }
        }
    }
}
