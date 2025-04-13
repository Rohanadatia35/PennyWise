package com.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.ExpenseBean;
import com.bean.ExpenseDetailBean;
import com.dao.CategoryDao;
import com.dao.ExpenseDetailDao;

@WebServlet("/DownloadCSVServlet")
public class DownloadCSVServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=expenses.csv");
		System.out.println("download servlet");
		HttpSession session = request.getSession(false); // Get session if it exists
		if (session == null) {
			response.sendRedirect("Login.jsp"); // Redirect to login if no session exists
			return;
		}
		int userId = Integer.parseInt(session.getAttribute("userId").toString());

		try (PrintWriter out = response.getWriter()) {
			ExpenseDetailDao expenseDetailDao = new ExpenseDetailDao();
			List<ExpenseDetailBean> expenses = expenseDetailDao.getAllExpensesIncomesWithId(userId);
			
//			for(ExpenseDetailBean expense : expenses) {
//				System.out.println(expense.toString());
//			}
			// Write CSV Header
			out.println("Sr. No,Date,Type,Category Name, description,Amount");

			int srNo = 1; // Initialize serial number
			for (ExpenseDetailBean expense : expenses) {
				System.out.println(expense.getType());
				String typeText = (expense.getType().equals("i")) ? "Income":"Expense" ; // Convert 'i' -> "Income", 'e' ->													// "Expense"
				System.out.println(typeText);
//				CategoryDao categoryDao=new CategoryDao();
				
				String categoryName = expense.getCname(); // Get category name from categoryId
				String description=expense.getDescription();
				out.println(srNo++ + "," + expense.geteDate() + "," + typeText + "," + categoryName + "," + description+","
						+ expense.getAmount() );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
