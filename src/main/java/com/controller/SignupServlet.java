package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.internal.build.AllowSysOut;

import com.bean.UserBean;
import com.dao.UserDao;
import com.util.Bcrypt;
import com.util.DbConnection;


@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {

	private final Connection con = DbConnection.getConnection();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userName=request.getParameter("userName");
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		System.out.println(userName);
		
		
		if(password.equals(confirmPassword)) {
			Bcrypt bcrypt=new Bcrypt();
			String hashPassword= bcrypt.hashPassword(password);
			System.out.println(hashPassword);
		UserDao userDao = new UserDao();
		System.out.println("password matched");
		
		UserBean userBean = new UserBean();
		userBean.setUserName(userName);
		userBean.setEmail(email);
		userBean.setPassword(hashPassword);
		
		userDao.addUser(userBean);
		response.sendRedirect("Login.jsp");
		}else {
			PrintWriter out=response.getWriter();
			out.println("password doesnot match");
			
			System.out.println("password does not matched");
			response.sendRedirect("Signup.jsp");
		}
	}

}
