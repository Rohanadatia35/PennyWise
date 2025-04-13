package com.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.bean.ExpenseDetailBean;
import com.dao.ExpenseDetailDao;

@WebServlet("/FilterServlet")
public class FilterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		boolean isFilter = false;

		Integer userId = (Integer) session.getAttribute("userId");
		String filterType = request.getParameter("filterType");
		String filterDate = request.getParameter("filterDate");

		String category = request.getParameter("category");

		ExpenseDetailDao expenseDetailDao = new ExpenseDetailDao();
		List<ExpenseDetailBean> filteredExpenses;
		List<ExpenseDetailBean> filteredIncomes;
		double totalFilteredExpense;
		double totalFilteredIncome;

		System.out.println(filterDate);
		System.out.println("category" + category);
		if (category.equals("all")) {
			filteredExpenses = expenseDetailDao.getExpensesIncomesByFilter(userId, filterType, filterDate, "e");
			filteredIncomes = expenseDetailDao.getExpensesIncomesByFilter(userId, filterType, filterDate, "i");
			totalFilteredExpense = expenseDetailDao.getTotalExpensesIncomesByFilter(userId, filterType, filterDate,
					"e");
			totalFilteredIncome = expenseDetailDao.getTotalExpensesIncomesByFilter(userId, filterType, filterDate, "i");

		} else {
			filteredExpenses = expenseDetailDao.getExpensesIncomesByFilterAndCategory(userId, filterType, filterDate,
					"e", category);
			filteredIncomes = expenseDetailDao.getExpensesIncomesByFilterAndCategory(userId, filterType, filterDate,
					"i", category);
			totalFilteredExpense = expenseDetailDao.getTotalExpensesIncomesByFilterAndCategory(userId, filterType,
					filterDate, "e", category);
			totalFilteredIncome = expenseDetailDao.getTotalExpensesIncomesByFilterAndCategory(userId, filterType,
					filterDate, "i", category);
		}
		System.out.println(filteredExpenses.toString());

		if (filteredExpenses != null || filteredIncomes != null) {
			isFilter = true;
		}
		System.out.println(isFilter);

		request.setAttribute("isFilter", isFilter);
		request.setAttribute("filteredExpenses", filteredExpenses);
		request.setAttribute("filteredIncomes", filteredIncomes);
		request.setAttribute("totalFilteredExpense", totalFilteredExpense);
		request.setAttribute("totalFilteredIncome", totalFilteredIncome);

		RequestDispatcher dispatcher = request.getRequestDispatcher("GetExpenseIncomeServlet");
		dispatcher.forward(request, response);
	}
}