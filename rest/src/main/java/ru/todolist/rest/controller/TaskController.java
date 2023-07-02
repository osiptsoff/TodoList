package ru.todolist.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
import ru.todolist.rest.service.TaskService;

class NotFoundException extends RuntimeException { private static final long serialVersionUID = 1L; }

@RestController
@RequestMapping(path = "rest/users/tasks")
public class TaskController {	
	@Autowired
	private TaskService taskService;
	
	@GetMapping()
	public List<Task> getTasks(@RequestParam(required = false) Boolean actual,
			@RequestParam(required = false) String date) {
		List<Task> tasks = taskService.getTasks(getCurrentUserId(), actual, date);
		
		if(tasks.isEmpty())
			throw new NotFoundException();
		
		return tasks;
	}
	
	@PostMapping()
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void addTask(@RequestBody Task task) {
		taskService.addTask(getCurrentUserId(), task);
	}
	
	@DeleteMapping("/{idtask}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removeTask(@PathVariable int idtask) {
		int rowsAffected = taskService.removeTask(getCurrentUserId(), idtask);
		if(rowsAffected == 0)
			throw new NotFoundException();
	}
	
	@PutMapping("/{idtask}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void modifyTask(@PathVariable int idtask, @RequestBody Task task) {
		task.setIdtask(idtask);
		int rowsAffected = taskService.modifyTask(getCurrentUserId(), idtask, task);
		if(rowsAffected == 0)
			throw new NotFoundException();
	}
	
	private Integer getCurrentUserId() {
		return (Integer)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
