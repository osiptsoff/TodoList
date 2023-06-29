package ru.todolist.rest.repository;

import java.util.List;

public interface DAO<T> {
	List<T> getAll();
	T getById(int id);
	int insert(T obj);
	int update(T obj);
	int removeById(int id);
	int remove(T obj);
}
