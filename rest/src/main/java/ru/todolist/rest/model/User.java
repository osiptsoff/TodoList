package ru.todolist.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;

public class User {
	@Schema(description = "ID of user", example = "1", required = true)
	private int iduser;
	@Schema(description = "Login of user", example = "Yuriy123", required = true)
	private String login;
	@Schema(description = "Password of user", example = "etertgdfgdfgrte", required = true)
	private String password;
	
	public int getIduser() {
		return iduser;
	}
	public void setIduser(int iduser) {
		this.iduser = iduser;
	}
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
