package ru.todolist.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ru.todolist.rest.model.User;
import ru.todolist.rest.repository.DAO;

@RestController
@RequestMapping(path = "users")
public class UserController {
	@Autowired
	private DAO<User> userDao;
	
	@PostMapping()
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void addTask(@RequestBody User user) {
		userDao.insert(user);
	}
	
	@ExceptionHandler(DataAccessException.class)
	private ResponseEntity<String> dbExceptionHandler() {
		return new ResponseEntity<>("Database exception happened", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
