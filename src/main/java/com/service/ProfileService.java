package com.service;

import com.dao.UserDao;
import com.util.DbConnection;

import java.sql.Connection;

import com.bean.UserBean;

public class ProfileService {
    private UserDao userDao;

    public ProfileService() {
        this.userDao = new UserDao(); 
    }

    public boolean updateUserProfile(UserBean userBean) {
        return userDao.updateUser(userBean);
    }
}
