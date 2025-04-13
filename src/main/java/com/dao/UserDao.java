package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.bean.UserBean;
import com.util.Bcrypt;
import com.util.DbConnection;

public class UserDao implements IDao<UserBean> {

	
	public String findEmailById(int id) {
		String email="";
		try(Connection con = DbConnection.getConnection()) {
			String select = "select email from users where userId=?";
			PreparedStatement pstmt = con.prepareStatement(select);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				email=rs.getString("email");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return email;
	}
	@Override
	public UserBean findById(int id) {
		UserBean userBean = null;
		try(Connection con = DbConnection.getConnection()) {
			String select = "select userId , uname , email,password from users where userId=?";
			PreparedStatement pstmt = con.prepareStatement(select);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				userBean = new UserBean();
				userBean.setUserId(rs.getInt("userId"));
				userBean.setUserName(rs.getString("uname"));
				userBean.setEmail(rs.getString("email"));
				userBean.setPassword(rs.getString("password"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userBean;
	}

	@Override
	public UserBean findByEmail(String email) {
		UserBean userBean = null;
		Connection con = DbConnection.getConnection();
		String query = "SELECT userId, uname, email FROM users WHERE email = ?";
		try (PreparedStatement pstmt = con.prepareStatement(query)) {

			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				userBean = new UserBean();
				userBean.setUserId(rs.getInt("userId"));
				userBean.setUserName(rs.getString("uname"));
				userBean.setEmail(rs.getString("email"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userBean;
	}

	// db insert query
	public void addUser(UserBean userBean) {
		Connection con = DbConnection.getConnection();
		String query = "insert into users (uname,email,password) values (?,?,?)";
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, userBean.getUserName());
			pstmt.setString(2, userBean.getEmail());
			pstmt.setString(3, userBean.getPassword());

			pstmt.executeUpdate();// insert
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// if user's credentials is correct -> then return all user detail
	// else return null
	public UserBean authenticate(String email) {
		Connection con = DbConnection.getConnection();
		UserBean userBean = null;
		String query = "SELECT userId, uname, email, password FROM users WHERE email = ?";
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				userBean = new UserBean();
				userBean.setUserId(rs.getInt("userId"));
				userBean.setUserName(rs.getString("uname"));
				userBean.setEmail(rs.getString("email"));
				userBean.setPassword(rs.getString("password")); // Needed for bcrypt verification
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userBean;
	}

	@Override
	public boolean updateUser(UserBean userBean) {
		Connection con = DbConnection.getConnection();
		boolean isUpdated = false;
		String query = "UPDATE users SET uname = ? WHERE userId = ?"; // Removed unnecessary email update

		try (PreparedStatement pstmt = con.prepareStatement(query)) {

			pstmt.setString(1, userBean.getUserName());
			pstmt.setInt(2, userBean.getUserId()); // Fixed incorrect index

			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
				isUpdated = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isUpdated;
	}

	public String findPasswordByEmail(String email) {
		Connection con = DbConnection.getConnection();
		String query = "SELECT password FROM users WHERE email = ?";
		String userPassword = null;

		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				userPassword = rs.getString("password");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userPassword;
	}

	public String generateTempPassword(String email) {
		String tempPassword = "";
		boolean isUpdated = false;
		Connection con = DbConnection.getConnection();

		// Generate a random temporary password (8 characters)
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
		StringBuilder sb = new StringBuilder(8);
		Random random = new Random();

		for (int i = 0; i < 8; i++) {
			int index = random.nextInt(characters.length());
			sb.append(characters.charAt(index));
		}

		tempPassword = sb.toString();
		Bcrypt bcrypt = new Bcrypt();
		// Hash the temporary password with BCrypt
		String hashedPassword = bcrypt.hashPassword(tempPassword);

		// Update the password in the database
		String query = "update users set password = ? where email = ?";
		try (PreparedStatement pstmt = con.prepareStatement(query)) {

			pstmt.setString(1, hashedPassword);
			pstmt.setString(2, email);
			int rowsAffected = pstmt.executeUpdate();
			isUpdated = (rowsAffected > 0);
			if (!isUpdated) {
				// If update failed, don't return the generated password
				tempPassword = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			tempPassword = ""; // Reset if there's an error
		}
		return tempPassword; // Return the plain text password
	}

	public boolean updatePassword(String hashPassword, int userId) {

		boolean isUpdated = false;
		String query = "UPDATE users SET password = ? WHERE userId = ?"; // ✅ Removed unnecessary email update

		try (Connection con = new DbConnection().getConnection();
				PreparedStatement pstmt = con.prepareStatement(query)) {

			pstmt.setString(1, hashPassword);
			pstmt.setInt(2, userId); // ✅ Fixed incorrect index

			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
				isUpdated = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isUpdated;
	}

	public boolean editUser(int userId, String userName, String email) {
		String query = "UPDATE users SET uname = ?, email = ? WHERE userId = ?";
		boolean isUpdated = false;
		try (Connection con = new DbConnection().getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, userName);
			ps.setString(2, email);
			ps.setInt(3, userId);
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				isUpdated = true;
			} // Returns true if update is successful
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isUpdated;
	}
}
