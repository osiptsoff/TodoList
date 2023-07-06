package ru.todolist.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.security.auth.message.AuthException;
import ru.todolist.rest.security.jwt.AuthRequest;
import ru.todolist.rest.security.jwt.AuthResponse;
import ru.todolist.rest.security.jwt.RefreshRequest;
import ru.todolist.rest.security.jwt.RefreshResponse;
import ru.todolist.rest.service.AuthService;

@RestController
@RequestMapping(path = "rest/auth")
public class AuthController {
	@Autowired
	private AuthService authService;
	
	@Operation(summary = "Authenticates given user: checks credentials and provides with refresh and access tokens if all is correct.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Returns type (Bearer always), refresh and access tokens.",
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = AuthResponse.class)) }),
		@ApiResponse(responseCode = "400", description = "Authentication failed - 'Username not found.' or 'Password does not match.'",
				 content = @Content(schema = @Schema(implementation = String.class))),
		@ApiResponse(responseCode = "500", description = "Exception while database querying.",
				content = @Content(schema = @Schema(implementation = Void.class)))
	})
	@PostMapping()
	public AuthResponse authenticate(@RequestBody AuthRequest authRequest) {
		return authService.authenticate(authRequest);
	}
	
	@Operation(summary = "Generates access token by given refresh token.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Returns valid access token.",
			content = { @Content(mediaType = "application/json", 
			schema = @Schema(implementation = RefreshResponse.class)) }),
		@ApiResponse(responseCode = "403", description = "Invalid refresh token passed.",
				 content = @Content(schema = @Schema(implementation = String.class))) 
	})
	@PostMapping("/refresh")
	public RefreshResponse refresh(@RequestBody RefreshRequest refreshRequest) throws AuthException {
		RefreshResponse refreshResponse = new RefreshResponse();
		refreshResponse.setAccessToken(authService.refresh(refreshRequest));
		
		return refreshResponse;
	}
	
}
