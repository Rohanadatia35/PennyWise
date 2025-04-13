package com.dao;

public interface IDao<T> {
	void addUser(T t);
	T authenticate(String email);
	boolean updateUser(T updatedt);
	T findById(int id );
	T findByEmail(String email);
}
