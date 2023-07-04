package ru.todolist.rest.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler {
	@ExceptionHandler(DuplicateKeyException.class)
	private ResponseEntity<String> duplicateKeyExceptionHandler() {
		return new ResponseEntity<>("Attempt to duplicate unique stored value.", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NotFoundException.class)
	private ResponseEntity<String> resourceNotFoundExceptionHandler() {
		return new ResponseEntity<>("No requested resources found.", HttpStatus.NOT_FOUND); 
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	private ResponseEntity<String> illegalArgumentExceptionHandler() {
		return new ResponseEntity<>("Illegal argument was passed.", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DataAccessException.class)
	private ResponseEntity<String> dbExceptionHandler() {
		return new ResponseEntity<>("Database exception happened.", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
