package com.bean;

public class CategoryBean {
    private int categoryId;
    private String cName;

    // Default Constructor
    public CategoryBean() {}

    // Parameterized Constructor
    public CategoryBean(int categoryId, String cName) {
        this.categoryId = categoryId;
        this.cName = cName;
    }

    // Getters and Setters
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    // toString Method for Debugging
    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", cName='" + cName + '\'' +
                '}';
    }
}