package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.ExpectedExpenseDao;
import com.dao.UserDao;
import com.util.SendEmailUtil;

/**
 * Servlet implementation class GetExpectedExpenseServlet
 */
@WebServlet("/GetExpectedExpenseServlet")
public class GetExpectedExpenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetExpectedExpenseServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ExpectedExpenseDao expectedExpenseDao = new ExpectedExpenseDao();
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            out.print("{\"error\": \"Not logged in\"}");
            System.out.println("User not logged in");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        Integer counter = (Integer) session.getAttribute("emailCounter");
        if (counter == null) {
            counter = 0; // Initialize counter
        }
        
        LocalDate currentMonthYear = LocalDate.now().withDayOfMonth(1);
        Date sqlDate = Date.valueOf(currentMonthYear);
        String DateString =sqlDate.toString();
//        System.out.println(DateString);
        boolean isMore=expectedExpenseDao.isExpectedExpenseMore(userId, DateString);
        
        try {
            double expectedExpense = expectedExpenseDao.getExpectedExpense(userId, sqlDate);
            UserDao userDao=new UserDao();
            String email=userDao.findEmailById(userId);
            if (expectedExpense == 0 && counter == 0) {
            	String emailBody="<p>You have spent more than <strong>75%</strong> of your "
            			+ "expected expense for this month.</p>\r\n"
            			+ "<p>Please review your expenses to stay within your budget.</p>";
            	
            	SendEmailUtil.sendHtmlEmail(email, "SPENDING TOOOO MUCCHHHH!!!!", emailBody);
    			
            } 
            out.print("{\"status\": \"success\", \"expectedExpense\": " + expectedExpense + "}");
        } catch (Exception e) {
            System.out.println("Exception in doGet: " + e.getMessage());
            e.printStackTrace();
            out.print("{\"status\": \"error\", \"message\": \"Database error: " + e.getMessage() + "\"}");
        }
        
        out.flush();
	}

}
