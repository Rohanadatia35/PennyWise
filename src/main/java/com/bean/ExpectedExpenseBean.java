package com.bean;

import java.sql.Date; // Use java.sql.Date for DB compatibility

public class ExpectedExpenseBean {
    private int userId;
    private Date monthYear; 
    private double monthlyExpense;
    private double expectedExpense; 

    // Default Constructor
    public ExpectedExpenseBean() {
    	super();
    }

    // Parameterized Constructor
    public ExpectedExpenseBean(int userId, Date monthYear, double monthlyExpense, double expectedExpense) {
        this.userId = userId;
        this.monthYear = monthYear;
        this.monthlyExpense = monthlyExpense;
        this.expectedExpense = expectedExpense;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(Date monthYear) {
        this.monthYear = monthYear;
    }

    public double getMonthlyExpense() {
        return monthlyExpense;
    }

    public void setMonthlyExpense(double monthlyExpense) {
        this.monthlyExpense = monthlyExpense;
    }

    public double getExpectedExpense() {
        return expectedExpense;
    }

    public void setExpectedExpense(double expectedExpense) {
        this.expectedExpense = expectedExpense;
    }

    // Debugging Purpose
    @Override
    public String toString() {
        return "ExpectedExpense{" +
                "userId=" + userId +
                ", monthYear=" + monthYear +
                ", monthlyExpense=" + monthlyExpense +
                ", expectedExpense=" + expectedExpense +
                '}';
    }
    
}
