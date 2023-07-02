package ru.todolist.rest.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
	
}
