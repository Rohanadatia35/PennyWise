package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.ExpenseDao;
import com.google.gson.Gson;

/**
 * Servlet implementation class GetMonthComparisonServlet
 */
@WebServlet("/GetMonthComparisonServlet")
public class GetMonthComparisonServlet extends HttpServlet {
    private ExpenseDao expenseDao = new ExpenseDao();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        
        int userId = (int) session.getAttribute("userId");
        
        // Get current and previous month comparison data
        Map<String, Object> comparisonData = expenseDao.getMonthComparisonData(userId);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        // Convert map to JSON
        Gson gson = new Gson();
        out.print(gson.toJson(comparisonData));
        out.flush();
    }
}
