package ru.todolist.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ru.todolist.rest.model.User;
import ru.todolist.rest.repository.DAO;

@RestController
@RequestMapping(path = "rest/users")
public class UserController {
	@Autowired
	private DAO<User> userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Operation(summary = "Adds new user")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Succsess."),
		@ApiResponse(responseCode = "400", description = "Login is taken."),
		@ApiResponse(responseCode = "500", description = "Exception while database querying.")
	})
	@PostMapping()
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void addUser(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDao.insert(user);
	}
}
