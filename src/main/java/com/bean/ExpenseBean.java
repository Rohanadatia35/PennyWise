package com.bean;

import java.util.Date;

public class ExpenseBean{
    private int expenseId;
    private Integer userId;
    private Integer categoryId;
    private Date eDate;
    private double amount;
    private String type;
    private String cname;
    private String description;
    
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// Default Constructor
    public ExpenseBean() {}

    // Parameterized Constructor
    public ExpenseBean(int expenseId, Integer userId, Integer categoryId, Date eDate, double amount, String type) {
        this.expenseId = expenseId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.eDate = eDate;
        this.amount = amount;
        this.type = type;
    }

    // Getters and Setters
    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Date getEDate() {
        return eDate;
    }

    public void setEDate(Date eDate) {
        this.eDate = eDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategoryName() {
        return cname;
    }
    
    public void setCategoryName(String cname) {
        this.cname = cname;
    }

	@Override
	public String toString() {
		return "ExpenseBean [expenseId=" + expenseId + ", userId=" + userId + ", categoryId=" + categoryId + ", eDate="
				+ eDate + ", amount=" + amount + ", type=" + type + ", categoryName=" + cname + "]";
	}
    
    

}
