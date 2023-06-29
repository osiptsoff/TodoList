package ru.todolist.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ru.todolist.rest.model.Task;
import ru.todolist.rest.repository.TaskDAO;

class NotFoundException extends RuntimeException { private static final long serialVersionUID = 1L; }

@RestController
@RequestMapping(path = "users/{iduser}/tasks")
public class TaskController {	
	@Autowired
	private TaskDAO taskDao;
	
	@GetMapping()
	public List<Task> getTasks(@PathVariable int iduser, @RequestParam(required = false) Boolean actual) {
		List<Task> result;
		if(actual != null)
			result = taskDao.getTasksOfUserByActuality(iduser, actual);
		else
			result =  taskDao.getTasksOfUser(iduser);
		
		if(result.size() == 0) 
			throw new NotFoundException();
		
		return result;
	}
	
	@GetMapping("/{date}")
	public List<Task> getByDate(@PathVariable int iduser, @PathVariable String date,
			@RequestParam(required = false) Boolean actual) {
		var result = taskDao.getTasksOfUserByDate(iduser, date);
		
		if(actual != null)
			result = result.stream().filter(e -> e.getActual() == actual).toList();
		
		if(result.size() == 0)
			throw new NotFoundException();
		
		return result;
	}
	
	@PostMapping()
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void addTask(@PathVariable int iduser, @RequestBody Task task) {
		task.setIduser(iduser);
		taskDao.insert(task);
	}
	
	@DeleteMapping("/{idtask}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removeTask(@PathVariable int iduser, @PathVariable int idtask) {
		int rowsAffected = taskDao.removeTaskOfUserById(iduser, idtask);
		if(rowsAffected == 0)
			throw new NotFoundException();
	}
	
	@PutMapping("/{idtask}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void modifyTask(@PathVariable int iduser, @PathVariable int idtask, @RequestBody Task task) {
		task.setIdtask(idtask);
		int rowsAffected = taskDao.updateTaskOfUser(iduser, task);
		if(rowsAffected == 0)
			throw new NotFoundException();
	}
	
	@ExceptionHandler(DataAccessException.class)
	private ResponseEntity<String> dbExceptionHandler() {
		return new ResponseEntity<>("Database exception happened", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	private ResponseEntity<String> illegalArgumentExceptionHandler() {
		return new ResponseEntity<>("Illegal argument was passed", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NotFoundException.class)
	private ResponseEntity<String> resourceNotFoundExceptionHandler() {
		return new ResponseEntity<>("Task was not found", HttpStatus.NOT_FOUND); 
	}
}
