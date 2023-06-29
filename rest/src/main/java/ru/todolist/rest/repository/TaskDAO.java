package ru.todolist.rest.repository;

import java.util.List;

import ru.todolist.rest.model.Task;

public interface TaskDAO extends DAO<Task>{
	List<Task> getTasksOfUser(int iduser);
	List<Task> getTasksOfUserByActuality(int iduser, boolean actual);
	List<Task> getTasksOfUserByDate(int iduser, String date);
	int removeTaskOfUserById(int iduser, int idtask);
	int updateTaskOfUser(int iduser, Task task);
}
