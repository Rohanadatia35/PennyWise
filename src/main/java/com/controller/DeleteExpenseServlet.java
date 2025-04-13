package com.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.ExpenseDao;

/**
 * Servlet implementation class DeleteExpenseServlet
 */
@WebServlet("/DeleteExpenseServlet")
public class DeleteExpenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("DeleteExpenseServlet called");
		System.out.println(request.getParameter("expenseId"));
		try {
			
			int expenseId = Integer.parseInt(request.getParameter("expenseId"));
			System.out.println("DeleteExpenseServlet expenseid = " + expenseId);
			ExpenseDao expenseDao = new ExpenseDao();

			boolean success = expenseDao.deleteExpense(expenseId);
			if (success) {
	        	response.sendRedirect("GetExpenseIncomeServlet");
//	            response.sendRedirect("Home.jsp");
	        } else {
	            response.getWriter().println("Failed to edit expense/income.");
	        }
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
}