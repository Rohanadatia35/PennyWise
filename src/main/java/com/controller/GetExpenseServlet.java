package com.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.dao.ExpenseDao;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/GetExpenseServlet")
public class GetExpenseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        Integer userId = (Integer) session.getAttribute("userId");
        ExpenseDao expenseDao = new ExpenseDao();
        List<Object[]> rawExpenseList = expenseDao.getExpensesByUser(userId);
        List<JsonObject> formattedExpenseList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");

        for (Object[] expense : rawExpenseList) {
            JsonObject expenseObj = new JsonObject();
            expenseObj.addProperty("date", sdf.format(expense[0]));
            expenseObj.addProperty("category", (String) expense[1]);
            expenseObj.addProperty("amount", ((Character) expense[3] == 'i' ? "+Rs " : "-Rs ") + String.format("%.2f", (Double) expense[2]));
            expenseObj.addProperty("transactionClass", ((Character) expense[3] == 'i') ? "transaction-item income" : "transaction-item expense");

            formattedExpenseList.add(expenseObj);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(formattedExpenseList));
    }
}
