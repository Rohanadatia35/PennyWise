package com.controller;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.ExpenseDetailBean;
import com.dao.ExpenseDetailDao;

@WebServlet("/GetExpenseDetailsServlet")
public class GetExpenseDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();

        int expenseId = Integer.parseInt(request.getParameter("expenseId"));
        System.out.println(expenseId);
        ExpenseDetailDao expenseDetailDao = new ExpenseDetailDao();
        ExpenseDetailBean expense = expenseDetailDao.getExpenseById(expenseId);

        if (expense != null) {
            out.print("{ \"status\": \"success\", \"expenseId\": " + expense.getExpenseId() +
                    ", \"cname\": " + expense.getCname() +
                    ", \"description\": \"" + expense.getDescription() + "\"" +
                    ", \"amount\": " + expense.getAmount() +
                    ", \"eDate\": \"" + expense.geteDate() + "\" }");
        } else {
            out.print("{ \"status\": \"error\", \"message\": \"Expense not found\" }");
        }
        out.flush();
    }
}
