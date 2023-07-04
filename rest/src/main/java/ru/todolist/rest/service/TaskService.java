package ru.todolist.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.todolist.rest.model.Task;
import ru.todolist.rest.repository.TaskDAO;

@Service
public class TaskService {
	@Autowired
	private TaskDAO taskDao;
	
	public List<Task> getTasks(int iduser, Boolean actual, String date) {
		List<Task> result;
		
		if (date != null)
			result = taskDao.getTasksOfUserByDate(iduser, date);
		else
			result = taskDao.getTasksOfUser(iduser);
		
		if(actual != null)
			result = result.stream().filter(task -> task.getActual() == actual).toList();
		
		return result;
	}
	
	public void addTask(int iduser, Task task) {
		task.setIduser(iduser);
		taskDao.insert(task);
	}
	
	public int removeTask(int iduser, int idtask) {
		return taskDao.removeTaskOfUserById(iduser, idtask);
	}
	
	public int modifyTask(int iduser, int idtask, Task task)  {
		task.setIdtask(idtask);
		return taskDao.updateTaskOfUser(iduser, task);
	}
}
