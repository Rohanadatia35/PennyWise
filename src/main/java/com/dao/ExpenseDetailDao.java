package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bean.ExpenseDetailBean;
import com.util.DbConnection;

public class ExpenseDetailDao {

	public ExpenseDetailBean getExpenseById(int expenseId) {
		ExpenseDetailBean expense = new ExpenseDetailBean();
		String query = "SELECT e.edate, c.cname, e.amount, e.type , e.description"
				+ "FROM expenses e JOIN categories c ON e.categoryid = c.categoryid "
				+ "WHERE e.expenseid = ?";

		try (Connection conn = DbConnection.getConnection(); 
				PreparedStatement ps = conn.prepareStatement(query)) {
			
			ps.setInt(1, expenseId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				expense.setAmount(rs.getDouble("amount"));
				expense.seteDate(rs.getDate("edate"));
				expense.setCname(rs.getString("cname"));
				expense.setDescription(rs.getString("description"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return expense;
	}
	public List<ExpenseDetailBean> getAllExpensesIncomes(int userId, String type) {
		List<ExpenseDetailBean> expenses = new ArrayList<>();
		String sql = "SELECT e.expenseid, e.edate, e.amount, c.cname , e.description " + "FROM Expenses e "
				+ "JOIN Categories c ON e.categoryid = c.categoryid " + "WHERE e.userid= ? and e.type=?"
				+ "ORDER BY e.edate DESC"; // Changed to DESC to show newest first

		try (Connection conn = DbConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, userId);
			pstmt.setString(2, type);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				ExpenseDetailBean expense = new ExpenseDetailBean();
				expense.setExpenseId(rs.getInt("expenseid"));
				expense.seteDate(rs.getDate("edate"));
				expense.setAmount(rs.getDouble("amount"));
				expense.setCname(rs.getString("cname")); 
				expense.setDescription(rs.getString("description"));
				expenses.add(expense);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(expenses.toString());
		return expenses;
	}

	
	
	
	public double totalExpenseIncome(int userId, String type) {
		double expenseSum = 0;
		try (Connection con = DbConnection.getConnection();) {

			String query = "Select sum(amount) as expensesum from expenses where userid=? and type =?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setInt(1, userId);
			pstmt.setString(2, type);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				expenseSum = rs.getDouble("expensesum");
			}
			System.out.println("expenseSum = "+expenseSum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return expenseSum;
	}

	
///////// description added /////////
	public List<ExpenseDetailBean> getExpensesIncomesByFilter(int userId, String filterType, String filterDate,
			String type) {
		List<ExpenseDetailBean> expenses = new ArrayList<>();
		String query = "";

		switch (filterType) {
		case "week":
			query = "SELECT e.expenseid, c.cname,e.amount,e.edate,e.description FROM expenses e join categories c on e.categoryid = c.categoryid WHERE userid = ? AND eDate >= (TO_DATE(?, 'YYYY-MM-DD') - 7) and type=?";
			break;
		case "month":
			query = "SELECT e.expenseid, c.cname,e.amount,e.edate,e.description FROM expenses e join categories c on e.categoryid = c.categoryid WHERE userid = ? AND EXTRACT(MONTH FROM eDate) = EXTRACT(MONTH FROM TO_DATE(?, 'YYYY-MM-DD'))  "
					+ "AND EXTRACT(YEAR FROM eDate) = EXTRACT(YEAR FROM TO_DATE(?, 'YYYY-MM-DD')) and type=?";
			break;
		case "year":
			query = "SELECT e.expenseid, c.cname,e.amount,e.edate,e.description FROM expenses e join categories c on e.categoryid = c.categoryid WHERE userid = ? AND EXTRACT(YEAR FROM eDate) = EXTRACT(YEAR FROM TO_DATE(?, 'YYYY-MM-DD'))and type=?";
			break;
		}

		try (Connection conn = DbConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			if (filterType.equals("month")) {
				pstmt.setInt(1, userId);
				pstmt.setString(2, filterDate);
				pstmt.setString(3, filterDate);
				pstmt.setString(4, type);
			} else {
				pstmt.setInt(1, userId);
				pstmt.setString(2, filterDate);
				pstmt.setString(3, type);
			}

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				ExpenseDetailBean expense = new ExpenseDetailBean();
				expense.setExpenseId(rs.getInt("expenseid"));
				expense.seteDate(rs.getDate("eDate"));
				expense.setAmount(rs.getDouble("amount"));
				expense.setCname(rs.getString("cname"));
				expense.setDescription(rs.getString("description"));
				expenses.add(expense);
			}
			// System.out.println(expenses.toString());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return expenses;
	}

	public double getTotalExpensesIncomesByFilter(int userId, String filterType, String filterDate, String type) {
		double totalExpense = 0;
		String query = "";

		switch (filterType) {
		case "week":
			query = "SELECT sum(amount) as totalExpense FROM expenses e join categories c on e.categoryid = c.categoryid WHERE userid = ? AND eDate >= (TO_DATE(?, 'YYYY-MM-DD') - 7) and type=?";
			break;
		case "month":
			query = "SELECT sum(amount) as totalExpense FROM expenses e join categories c on e.categoryid = c.categoryid WHERE userid = ? AND EXTRACT(MONTH FROM eDate) = EXTRACT(MONTH FROM TO_DATE(?, 'YYYY-MM-DD'))  "
					+ "AND EXTRACT(YEAR FROM eDate) = EXTRACT(YEAR FROM TO_DATE(?, 'YYYY-MM-DD')) and type=?";
			break;
		case "year":
			query = "SELECT sum(amount) as totalExpense FROM expenses e join categories c on e.categoryid = c.categoryid WHERE userid = ? AND EXTRACT(YEAR FROM eDate) = EXTRACT(YEAR FROM TO_DATE(?, 'YYYY-MM-DD'))and type=?";
			break;
		}

		try (Connection conn = DbConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			if (filterType.equals("month")) {
				pstmt.setInt(1, userId);
				pstmt.setString(2, filterDate);
				pstmt.setString(3, filterDate);
				pstmt.setString(4, type);
			} else {
				pstmt.setInt(1, userId);
				pstmt.setString(2, filterDate);
				pstmt.setString(3, type);
			}

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				totalExpense = rs.getDouble("totalExpense");
			}
			// System.out.println(expenses.toString());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return totalExpense;
	}
	
	public List<ExpenseDetailBean> getExpensesIncomesByFilterAndCategory(int userId, String filterType, String filterDate,
			String type,String category) {
		List<ExpenseDetailBean> expenses = new ArrayList<>();
		String query = "";

		switch (filterType) {
		case "week":
			query = "SELECT e.expenseid, c.cname,e.amount,e.edate,e.description FROM expenses e join categories c on e.categoryid = c.categoryid WHERE userid = ? AND eDate >= (TO_DATE(?, 'YYYY-MM-DD') - 7) and type=? and c.cname=?";
			break;
		case "month":
			query = "SELECT e.expenseid, c.cname,e.amount,e.edate,e.description FROM expenses e join categories c on e.categoryid = c.categoryid WHERE userid = ? AND EXTRACT(MONTH FROM eDate) = EXTRACT(MONTH FROM TO_DATE(?, 'YYYY-MM-DD'))  "
					+ "AND EXTRACT(YEAR FROM eDate) = EXTRACT(YEAR FROM TO_DATE(?, 'YYYY-MM-DD')) and type=? and c.cname=?";
			break;
		case "year":
			query = "SELECT e.expenseid, c.cname,e.amount,e.edate,e.description FROM expenses e join categories c on e.categoryid = c.categoryid WHERE userid = ? AND EXTRACT(YEAR FROM eDate) = EXTRACT(YEAR FROM TO_DATE(?, 'YYYY-MM-DD'))and type=? and c.cname=?";
			break;
		}

		try (Connection conn = DbConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			if (filterType.equals("month")) {
				pstmt.setInt(1, userId);
				pstmt.setString(2, filterDate);
				pstmt.setString(3, filterDate);
				pstmt.setString(4, type);
				pstmt.setString(5,category);
			} else {
				pstmt.setInt(1, userId);
				pstmt.setString(2, filterDate);
				pstmt.setString(3, type);
				pstmt.setString(4, category);
			}

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				ExpenseDetailBean expense = new ExpenseDetailBean();
				expense.setExpenseId(rs.getInt("expenseid"));
				expense.seteDate(rs.getDate("eDate"));
				expense.setAmount(rs.getDouble("amount"));
				expense.setCname(rs.getString("cname"));
				expense.setDescription(rs.getString("description"));
				expenses.add(expense);
			}
			// System.out.println(expenses.toString());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return expenses;
	}

	public double getTotalExpensesIncomesByFilterAndCategory(int userId, String filterType, String filterDate, String type,String category) {
		double totalExpense = 0;
		String query = "";

		switch (filterType) {
		case "week":
			query = "SELECT sum(amount) as totalExpense FROM expenses e join categories c on e.categoryid = c.categoryid WHERE userid = ? AND eDate >= (TO_DATE(?, 'YYYY-MM-DD') - 7) and type=? and c.cname=?";
			break;
		case "month":
			query = "SELECT sum(amount) as totalExpense FROM expenses e join categories c on e.categoryid = c.categoryid WHERE userid = ? AND EXTRACT(MONTH FROM eDate) = EXTRACT(MONTH FROM TO_DATE(?, 'YYYY-MM-DD'))  "
					+ "AND EXTRACT(YEAR FROM eDate) = EXTRACT(YEAR FROM TO_DATE(?, 'YYYY-MM-DD')) and type=? and c.cname=?";
			break;
		case "year":
			query = "SELECT sum(amount) as totalExpense FROM expenses e join categories c on e.categoryid = c.categoryid WHERE userid = ? AND EXTRACT(YEAR FROM eDate) = EXTRACT(YEAR FROM TO_DATE(?, 'YYYY-MM-DD'))and type=? and c.cname=?";
			break;
		}

		try (Connection conn = DbConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			if (filterType.equals("month")) {
				pstmt.setInt(1, userId);
				pstmt.setString(2, filterDate);
				pstmt.setString(3, filterDate);
				pstmt.setString(4, type);
				pstmt.setString(5, category);
			} else {
				pstmt.setInt(1, userId);
				pstmt.setString(2, filterDate);
				pstmt.setString(3, type);
				pstmt.setString(4, category);
			}

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				totalExpense = rs.getDouble("totalExpense");
			}
			// System.out.println(expenses.toString());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return totalExpense;
	}

	//////// description added ////////
	public List<ExpenseDetailBean> getAllExpensesIncomesWithId(int userId) {
		List<ExpenseDetailBean> expenses = new ArrayList<>();

		String sql = "SELECT e.expenseid,e.type, e.edate, e.amount, c.cname, e.description " + "FROM Expenses e "
				+ "JOIN Categories c ON e.categoryid = c.categoryid " + "WHERE e.userid= ? "
				+ "ORDER BY e.edate DESC"; // Changed to DESC to show newest first

		try (Connection conn = DbConnection.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, userId);
			
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				ExpenseDetailBean expense = new ExpenseDetailBean();
				expense.setExpenseId(rs.getInt("expenseid"));
				expense.seteDate(rs.getDate("edate"));
				expense.setType(rs.getString("type"));
				expense.setAmount(rs.getDouble("amount"));
				expense.setCname(rs.getString("cname"));
				expense.setDescription(rs.getString("description"));

				expenses.add(expense);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(expenses.toString());
		return expenses;
	}
}
