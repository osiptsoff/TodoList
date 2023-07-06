package ru.todolist.rest.security.jwt;

import io.swagger.v3.oas.annotations.media.Schema;

public class AuthRequest {
	@Schema(description = "Login of user", example = "Yuriy123", required = true)
	private String login;
	@Schema(description = "Password of user", example = "etertgdfgdfgrte", required = true)
	private String password;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
