	package com.controller;
	
	import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.UserDao;
import com.util.Bcrypt;
	
	
	@WebServlet("/ChangePasswordServlet")
	public class ChangePasswordServlet extends HttpServlet {
	
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			String newPassword=request.getParameter("newPassword");
			String confirmNewPassword = request.getParameter("confirmNewPassword");
			
			if(newPassword.equals(confirmNewPassword)) {
				Bcrypt bcrypt=new Bcrypt();
				String hashPassword= bcrypt.hashPassword(newPassword);
				System.out.println(hashPassword);
			UserDao userDao = new UserDao();
			System.out.println("password matched");
			
			HttpSession session=request.getSession(false);
			int userId = (int) session.getAttribute("userId");

			if(userDao.updatePassword(hashPassword,userId)) {
			
			response.sendRedirect("Login.jsp");}
			}else {
				PrintWriter out=response.getWriter();
				out.println("password doesnot match");
				
				System.out.println("password doesnot matched");
				response.sendRedirect("ChangePassword.jsp");
			}
		}
	
	}
