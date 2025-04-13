 package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bean.ExpenseBean;
import com.util.DbConnection;

public class ExpenseDao {
	Connection con = DbConnection.getConnection();
	private static List<ExpenseBean> expenses = new ArrayList<>();

	public boolean addExpense(int userId, String category, double amount, String edate, String type , String description) {
		boolean isInserted = false;
		CategoryDao categoryDao = new CategoryDao();
		String query = "INSERT INTO expenses (userid, categoryid, edate, amount, type , description) VALUES (?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ? , ?)";

		try (Connection con = DbConnection.getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {

			int categoryId = categoryDao.getCategoryIdByName(category);
			System.out.println(categoryId);

			pstmt.setInt(1, userId);
			pstmt.setInt(2, categoryId);
			pstmt.setString(3, edate);
			pstmt.setDouble(4, amount);
			pstmt.setString(5, type);
			pstmt.setString(6, description);

			int rows = pstmt.executeUpdate();
			isInserted = rows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isInserted;
	}

	public boolean updateExpense(int expenseId, String category, double amount, String eDate,
			String description) {
		boolean isUpdated = false;
		CategoryDao categoryDao = new CategoryDao();
		
		String query = "Update expenses Set categoryid=?, edate=TO_DATE(?,'YYYY-MM-DD'), amount=?, description=? where expenseid=?";
		System.out.println("expenceid= "+expenseId);
		try (Connection con = DbConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(query)) {

			int categoryId = categoryDao.getCategoryIdByName(category);
			System.out.println(categoryId);

			pstmt.setInt(1, categoryId);
			pstmt.setString(2, eDate);
			pstmt.setDouble(3, amount);
			pstmt.setString(4, description);
			pstmt.setInt(5, expenseId);

			int rows = pstmt.executeUpdate();
			isUpdated = rows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isUpdated;
	}
	
	public boolean deleteExpense(int expenseId) {
		boolean isDeleted= false;
		String query = "DELETE FROM Expenses WHERE expenseid = ?";
		try (Connection con = DbConnection.getConnection();
				PreparedStatement pstmt = con.prepareStatement(query)) {
	        
	        pstmt.setInt(1, expenseId);
	        
	        int rowsAffected = pstmt.executeUpdate();
	        if(rowsAffected > 0) {
	        	isDeleted=true;
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } 
		return isDeleted;
	}	

	//// desc not added /////
	
	public List<Object[]> getExpensesByUser(int userId) {
		List<Object[]> expenses = new ArrayList<>();
		String query = "SELECT e.edate, c.cname, e.amount, e.type  "
				+ "FROM expenses e JOIN categories c ON e.categoryid = c.categoryid "
				+ "WHERE e.userid = ? ORDER BY e.edate DESC";

		try (Connection conn = DbConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Object[] expenseData = new Object[4];
				expenseData[0] = rs.getDate("edate"); // Date
				expenseData[1] = rs.getString("cname"); // Category
				expenseData[2] = rs.getDouble("amount"); // Amount
				expenseData[3] = rs.getString("type").charAt(0); // Type (income/expense)

				expenses.add(expenseData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return expenses;
	}

	

	public List<Map<String, Object>> getCategoryExpensesForCurrentMonth(int userId) {
		List<Map<String, Object>> result = new ArrayList<>();

		try {
			// Query to sum expenses by category for the current month
			String query = "SELECT c.cname, SUM(e.amount) as totalAmount " + "FROM expenses e "
					+ "JOIN categories c ON e.categoryId = c.categoryId " + "WHERE e.userId = ? "
					+ "AND e.edate BETWEEN TRUNC(SYSDATE, 'MM') AND LAST_DAY(TRUNC(SYSDATE, 'MM')) "
					+ "and type='e'"
					+ "GROUP BY c.cname " + "ORDER BY totalAmount DESC";

			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Map<String, Object> categoryData = new HashMap<>();
				categoryData.put("category", rs.getString("cname"));
				categoryData.put("amount", rs.getDouble("totalAmount"));
				result.add(categoryData);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error getting category expenses: " + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	public Map<String, Object> getMonthComparisonData(int userId) {
		Map<String, Object> result = new HashMap<>();

		try {
			// Query to get current month expenses by category
			String currentMonthQuery = "SELECT c.cname, SUM(e.amount) as totalAmount " + "FROM expenses e "
					+ "JOIN categories c ON e.categoryId = c.categoryId " + "WHERE e.userId = ? "
					+ "AND e.edate BETWEEN TRUNC(SYSDATE, 'MM') AND LAST_DAY(TRUNC(SYSDATE, 'MM')) "
					+ "and type='e'"
					+ "GROUP BY c.cname " + "ORDER BY c.cname";

			// Query to get previous month expenses by category
			String previousMonthQuery = "SELECT c.cname, SUM(e.amount) as totalAmount " + "FROM expenses e "
					+ "JOIN categories c ON e.categoryId = c.categoryId " + "WHERE e.userId = ? "
					+ "AND e.edate BETWEEN ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -1) AND LAST_DAY(ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -1)) "
					+ "and type='e'"
					+ "GROUP BY c.cname " + "ORDER BY c.cname";

			// Get current month expenses
			Map<String, Double> currentMonthData = new HashMap<>();
			PreparedStatement ps = con.prepareStatement(currentMonthQuery);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				currentMonthData.put(rs.getString("cname"), rs.getDouble("totalAmount"));
			}
			rs.close();
			ps.close();

			// Get previous month expenses
			Map<String, Double> previousMonthData = new HashMap<>();
			ps = con.prepareStatement(previousMonthQuery);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				previousMonthData.put(rs.getString("cname"), rs.getDouble("totalAmount"));
			}
			rs.close();
			ps.close();

			// Get all unique categories
			Set<String> allCategories = new HashSet<>();
			allCategories.addAll(currentMonthData.keySet());
			allCategories.addAll(previousMonthData.keySet());

			// Create formatted data for response
			List<String> categories = new ArrayList<>(allCategories);
			Collections.sort(categories);

			List<Double> currentMonthAmounts = new ArrayList<>();
			List<Double> previousMonthAmounts = new ArrayList<>();

			for (String category : categories) {
				currentMonthAmounts.add(currentMonthData.getOrDefault(category, 0.0));
				previousMonthAmounts.add(previousMonthData.getOrDefault(category, 0.0));
			}

			// Get month names
			String currentMonthName = new SimpleDateFormat("MMMM yyyy").format(new Date());
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);
			String previousMonthName = new SimpleDateFormat("MMMM yyyy").format(cal.getTime());

			// Build result map
			result.put("categories", categories);
			result.put("currentMonthAmounts", currentMonthAmounts);
			result.put("previousMonthAmounts", previousMonthAmounts);
			result.put("currentMonthName", currentMonthName);
			result.put("previousMonthName", previousMonthName);

		} catch (SQLException e) {
			System.out.println("Error getting month comparison data: " + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	


}
