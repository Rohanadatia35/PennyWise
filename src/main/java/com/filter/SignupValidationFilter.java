package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/SignupValidationFilter")
public class SignupValidationFilter implements Filter {

	public void init(FilterConfig arg0) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		System.out.println("doFilter::SignupValdiationFilter");
		
		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		boolean isError = false;

		if (userName == null || userName.trim().length() == 0) {
			isError = true;
			request.setAttribute("userNameError", "Please Enter userName");
		}
		if (email == null || email.trim().length() == 0) {
			isError = true;
			request.setAttribute("emailError", "Please Enter Email");
		}
		if (password == null || password.trim().length() == 0) {
			isError = true;
			request.setAttribute("passwordError", "Please Enter Password");
		}
		
		String alphaRegEx = "[a-zA-Z]+";
		String emailRegEx="^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		String passwordRegEx="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%?&])[A-Za-z\\d@$!%?&]{8,}$";

		
		if (userName.matches(alphaRegEx) == false) {
			isError = true;
			request.setAttribute("userNameError", "Please Enter valid FirstName.Only Alphabets Can be used!");
		}
		
		if (email.matches(emailRegEx) == false) {
			isError = true;
			request.setAttribute("emailError", "Please Enter valid email!");
		}
		
		System.out.println(password);
		System.out.println(password.matches(passwordRegEx));
		if (password.matches(passwordRegEx) == false) {
			System.out.println(password);
			isError = true;
			request.setAttribute("passwordError", "Please Enter a Strong password!");
		}
	
		if (isError == true) {
			// go back Signup.jsp
			RequestDispatcher rd = request.getRequestDispatcher("Signup.jsp");
			rd.forward(request, response);
		} else {
			RequestDispatcher rd=request.getRequestDispatcher("SignupServlet");
			rd.forward(request, response);
		}
	           

	}

	public void destroy() {

	}

	
}