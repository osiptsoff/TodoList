package ru.todolist.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.security.auth.message.AuthException;

@ControllerAdvice
public class AuthExceptionHandler {
	@ExceptionHandler(UsernameNotFoundException.class)
	private ResponseEntity<String> usernameNotFoundExceptionHandler() {
		return new ResponseEntity<>("Username not found.", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadCredentialsException.class)
	private ResponseEntity<String> badCredentialsExceptionHandler() {
		return new ResponseEntity<>("Password does not match.", HttpStatus.BAD_REQUEST);
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
