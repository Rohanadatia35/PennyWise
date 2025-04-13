package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.UserBean;
import com.dao.ExpectedExpenseDao;
import com.dao.UserDao;
import com.util.DbConnection;

@WebServlet("/SetExpectedExpenseServlet")
public class SetExpectedExpenseServlet extends HttpServlet {
	ExpectedExpenseDao expectedExpenseDao = new ExpectedExpenseDao();
	private final Connection con = DbConnection.getConnection();
	private static final long serialVersionUID = 1L;

	public SetExpectedExpenseServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		if (session == null || session.getAttribute("userId") == null) {
			out.print("{\"status\": \"error\", \"message\": \"Session expired. Please log in again.\"}");
			out.flush();
			return;
		}
		String expenseParam = request.getParameter("expectedExpense");
//		System.out.println("Received expected expense: " + request.getParameter("expectedExpense"));
//    	    System.out.println("expense param = "+expenseParam);
		if (expenseParam == null || expenseParam.isEmpty()) {
			out.print("{\"status\": \"error\", \"message\": \"Expense amount is required.\"}");
			out.flush();
			return;
		}

		double expectedExpenseAmount;
		try {
			expectedExpenseAmount = Double.parseDouble(expenseParam);
			System.out.println(expectedExpenseAmount);
		} catch (NumberFormatException e) {
			out.print("{\"status\": \"error\", \"message\": \"Invalid expense amount format.\"}");
			out.flush();
			return;
		}

		if (expectedExpenseAmount < 0) {
			out.print("{\"status\": \"error\", \"message\": \"Invalid expense amount provided.\"}");
			out.flush();
			return;
		}

		int userId = (int) session.getAttribute("userId");
		LocalDate currentMonthYear = LocalDate.now().withDayOfMonth(1);
		Date sqlDate = Date.valueOf(currentMonthYear);

		UserDao userDao = new UserDao();
		UserBean userBean = userDao.findById(userId);
		boolean isSaved = false;

		if (userBean == null) {
			out.print("{\"status\": \"error\", \"message\": \"User not found.\"}");
			out.flush();
			return;
		}
		
		isSaved = expectedExpenseDao.addExpectedExpense(userId, expectedExpenseAmount);

		if (isSaved) {
			double expectedExpense = expectedExpenseDao.getExpectedExpense(userId, sqlDate);
			out.print("{\"status\": \"success\", \"expectedExpense\": " + expectedExpense + "}");
			response.sendRedirect("Home.jsp");	
			}
		out.flush();
	}
}