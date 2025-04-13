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

/**
 * Servlet implementation class GetExpenseIncomeServlet
 */
@WebServlet("/GetExpenseIncomeServlet")
public class GetExpenseIncomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetExpenseIncomeServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
    	HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        Integer userId = (Integer) session.getAttribute("userId");
        List<ExpenseDetailBean>	incomeList;
        List<ExpenseDetailBean> expenseList;
        double expenseSum;
        double incomeSum;
        double totalSum;
        Object filterObj = request.getAttribute("isFilter"); 
        
        boolean isFiltered = (filterObj != null) && (boolean) filterObj;
        System.out.println("isFiltered: " + isFiltered); 
        ExpenseDetailDao expenseDetailDao = new ExpenseDetailDao();
        
        if(isFiltered) {
        	expenseList=(List<ExpenseDetailBean>)request.getAttribute("filteredExpenses");
        	incomeList=(List<ExpenseDetailBean>)request.getAttribute("filteredIncomes");
        	expenseSum=(double)request.getAttribute("totalFilteredExpense");
            incomeSum=(double)request.getAttribute("totalFilteredIncome");
            totalSum=incomeSum-expenseSum;
        }
        	else {
        incomeList=expenseDetailDao.getAllExpensesIncomes(userId , "i");
        expenseList=expenseDetailDao.getAllExpensesIncomes(userId, "e");
        expenseSum=expenseDetailDao.totalExpenseIncome(userId, "e");
        incomeSum=expenseDetailDao.totalExpenseIncome(userId, "i");
        totalSum=incomeSum-expenseSum;
        
//        Gson gson = new Gson();
//        String expenseListJson = gson.toJson(expenseList);
        System.out.println(expenseList.toString());
        System.out.println("GetExpenseIncomeServlet "+expenseSum);
        }
 
        request.setAttribute("incomeList", incomeList);
        request.setAttribute("expenseList",expenseList);
        request.setAttribute("incomeSum", incomeSum);
        request.setAttribute("expenseSum", expenseSum);
        request.setAttribute("totalSum", totalSum);
//        request.setAttribute("expenseListJson", expenseListJson);
        
        // Forward to JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("Home.jsp");
        dispatcher.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	doGet(req, resp);
    	
    }
	

}
