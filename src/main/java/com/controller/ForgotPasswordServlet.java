package com.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.UserBean;
import com.dao.UserDao;
import com.util.DbConnection;
import com.util.SendEmailUtil;

@WebServlet("/ForgetPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Connection con = DbConnection.getConnection();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		UserDao userDao=new UserDao();
		String email = request.getParameter("email");
		
		UserBean userBean=userDao.findByEmail(email);
		if(userBean == null) {
			response.sendRedirect("Signup.jsp");
			return;
		}
//		userDao.findPasswordByEmail(email);
		String tempPassword=userDao.generateTempPassword(email);
		
		String emailBody = "<p>Hello " + userBean.getUserName() + ",</p>" 
                + "<p>this is your temporary password</p>"
                + tempPassword
				+"<h4> It is highly recommended once you login using this password you change your password <h4>";	
		
		try {
			SendEmailUtil.sendHtmlEmail(userBean.getEmail(), "Password Change request: " +userBean.getUserName()  , emailBody);
			response.sendRedirect("Login.jsp");
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}
	}
}