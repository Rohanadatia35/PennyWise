package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.util.DbConnection;

public class ExpectedExpenseDao {
	// Insert expectedExpense
	public boolean addExpectedExpense(int userId, double expectedExpense) {
		boolean success = false;

		try (Connection con = DbConnection.getConnection()) {
			// Check if the user has an existing expected expense record
			java.sql.Date monthYear = java.sql.Date.valueOf(LocalDate.now().withDayOfMonth(1));
			double existingExpense = getExpectedExpense(userId, monthYear);
			// If record exists, update it
			String updateQuery = "UPDATE expectedexpense SET expectedexpense = ? WHERE userid = ? AND monthyear = ?";
			try (PreparedStatement pstmt = con.prepareStatement(updateQuery)) {
				pstmt.setDouble(1, expectedExpense);
				pstmt.setInt(2, userId);
				pstmt.setDate(3, monthYear);

				int rowsUpdated = pstmt.executeUpdate();
				success = (rowsUpdated > 0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return success;
	}

	// Fetch Expected Expense for User
	public double getExpectedExpense(int userId, java.sql.Date monthYear) {
		double expectedExpense = 0;
		try (Connection con = DbConnection.getConnection()) {

			// Verify the database connection is valid
			if (con == null) {
				System.out.println("Database connection is null!");
				return 0;
			}

			String query = "SELECT expectedexpense FROM expectedexpense WHERE userid = ? AND monthYear = ?";

			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setInt(1, userId);
			pstmt.setDate(2, monthYear);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				expectedExpense = rs.getDouble("expectedexpense");
			} else {
				System.out.println("No record found for userId=" + userId + ", monthYear=" + monthYear);
			}
		} catch (SQLException e) {
			System.out.println("SQL Exception in getExpectedExpense: " + e.getMessage());
			e.printStackTrace();
		}

		return expectedExpense;
	}

	public List<Map<String, Object>> getExpectedExpenseHistory(int userId, int months) {
		List<Map<String, Object>> result = new ArrayList<>();

		try(Connection con = DbConnection.getConnection()) {
			String query = "SELECT TO_CHAR(monthYear, 'Mon') as month, " + "monthlyExpense, expectedExpense "
					+ "FROM expectedExpense " + "WHERE userid = ? "
					+ "AND monthYear >= ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -?) " + "ORDER BY monthYear ASC";

			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, userId);
			ps.setInt(2, months - 1);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Map<String, Object> monthData = new HashMap<>();
				monthData.put("month", rs.getString("month"));
				monthData.put("monthlyExpense", rs.getDouble("monthlyExpense"));
				monthData.put("expectedExpense", rs.getDouble("expectedExpense"));
				result.add(monthData);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error getting expense history: " + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}
	public boolean isExpectedExpenseMore(int userId, String edate) {
		boolean isMore=false;
		double expectedExpense= 0;
		double monthlyExpense=0;
		try (Connection con = DbConnection.getConnection()) {
			String query="Select expectedexpense, monthlyexpense from expectedexpense where userid=? and monthyear=TRUNC(TO_DATE(? ,'YYYY-MM-DD'),'MM')";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, userId);
			ps.setString(2, edate);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				expectedExpense=rs.getDouble("expectedexpense");
				monthlyExpense=rs.getDouble("monthlyexpense");
				if((expectedExpense*0.75) <= monthlyExpense) {
					isMore=true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isMore;
	};
}

