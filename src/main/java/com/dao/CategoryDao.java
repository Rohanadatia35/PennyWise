package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DbConnection;

public class CategoryDao {
	Connection con = DbConnection.getConnection();
    public String getCategoryName(int categoryId) {
    	
        String categoryName = "";
        try (
             PreparedStatement ps = con.prepareStatement("select cname from Categories where categoryid = ?")) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    categoryName = rs.getString("cname");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryName;
    }
    
    public int getCategoryIdByName(String category) throws SQLException {
    	int categoryid=0;
        String query = "SELECT categoryid FROM categories WHERE cname = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, category);
            System.out.println(category);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	categoryid=rs.getInt("categoryid");
            	System.out.println(categoryid);
            }
        }
        return categoryid;
    }
}
