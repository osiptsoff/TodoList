package ru.todolist.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ru.todolist.rest.model.User;
import ru.todolist.rest.repository.DAO;

@RestController
@RequestMapping(path = "rest/users")
public class UserController {
	@Autowired
	private DAO<User> userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping()
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void addUser(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDao.insert(user);
	}
	
	/*
	 * @ExceptionHandler(DuplicateKeyException.class) private ResponseEntity<String>
	 * duplicateKeyExceptionHandler() { return new
	 * ResponseEntity<>("Login is taken.", HttpStatus.BAD_REQUEST); }
	 */
	
	/*
	 * @ExceptionHandler(DataAccessException.class) private ResponseEntity<String>
	 * dbExceptionHandler() { return new
	 * ResponseEntity<>("Database exception happened",
	 * HttpStatus.INTERNAL_SERVER_ERROR); }
	 */
}
