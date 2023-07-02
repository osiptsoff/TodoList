package ru.todolist.rest.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.security.auth.message.AuthException;
import ru.todolist.rest.security.jwt.AuthRequest;
import ru.todolist.rest.security.jwt.AuthResponse;
import ru.todolist.rest.security.jwt.RefreshRequest;
import ru.todolist.rest.service.AuthService;

@RestController
@RequestMapping(path = "rest/auth")
public class AuthController {
	@Autowired
	private AuthService authService;
	
	@PostMapping()
	public AuthResponse authenticate(@RequestBody AuthRequest authRequest) {
		return authService.authenticate(authRequest);
	}
	
	@PostMapping("/refresh")
	public Map<String, String> refresh(@RequestBody RefreshRequest refreshRequest) throws AuthException {
		return Collections.singletonMap("accessToken", authService.refresh(refreshRequest));
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	private ResponseEntity<String> usernameNotFoundExceptionHandler() {
		return new ResponseEntity<>("Username not found.", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadCredentialsException.class)
	private ResponseEntity<String> badCredentialsExceptionHandler() {
		return new ResponseEntity<>("Password does not match.", HttpStatus.BAD_REQUEST);
	}
		
	@ExceptionHandler(DataAccessException.class)
	private ResponseEntity<String> dbExceptionHandler() {
		return new ResponseEntity<>("Database exception happened", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(AuthException.class)
	private ResponseEntity<String> authExceptionHandler() {
		return new ResponseEntity<>("Invalid refrersh token.", HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(NumberFormatException.class)
	private ResponseEntity<String> numberFormatExceptionHandler() {
		return new ResponseEntity<>("Failed to parse user id from token.", HttpStatus.BAD_REQUEST);
	}
}
