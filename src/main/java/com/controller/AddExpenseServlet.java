package com.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.ExpenseDao;

@WebServlet("/AddExpenseServlet")
public class AddExpenseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get user ID from session
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        String category = request.getParameter("category");
        double amount = Double.parseDouble(request.getParameter("amount"));
        String inputDate = request.getParameter("date");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String description=request.getParameter("description");
		try {
			date = sdf.parse(inputDate);
		} catch (Exception e) {
			e.printStackTrace();
		}

        // Convert Date back to String (matching DB format)
        String formattedDate = sdf.format(date); // "YYYY-MM-DD"

        String modelName = request.getParameter("modelname"); // 'e' for expense, 'i' for income
        String type = modelName.equals("e") ? "e" : "i";

        // Call DAO method to insert expense
        ExpenseDao expenseDao = new ExpenseDao();
        boolean success = expenseDao.addExpense(userId, category, amount, formattedDate, type, description);

        if (success) {
        	response.sendRedirect("GetExpenseIncomeServlet");
//            response.sendRedirect("Home.jsp");
        } else {
            response.getWriter().println("Failed to add expense/income.");
        }
    }
}
