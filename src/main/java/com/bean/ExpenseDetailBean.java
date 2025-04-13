package com.bean;

import java.util.Date;

public class ExpenseDetailBean {
	private int expenseid;
	private Date eDate;
	private double amount;
	private String cname;
	private String description;
	private String type;
	public ExpenseDetailBean() {
		super();
	}
	
	public int getExpenseId() {
		return expenseid;
	}
	public void setExpenseId(int eid) {
		this.expenseid = eid;
	}
	public Date geteDate() {
		return eDate;
	}
	public void seteDate(Date eDate) {
		this.eDate = eDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ExpenseDetailBean(Date eDate, double amount, String cname) {
		super();
		this.eDate = eDate;
		this.amount = amount;
		this.cname = cname;
	}
	@Override
	public String toString() {
		return "ExpenseDetailBean [expenseid=" + expenseid + ", eDate=" + eDate + ", amount=" + amount + ", cname="
				+ cname + ", description=" + description + ", type=" + type + "]";
	}
	
	
}
