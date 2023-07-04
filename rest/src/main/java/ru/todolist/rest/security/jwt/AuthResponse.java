package ru.todolist.rest.security.jwt;

public class AuthResponse {
	private final String type = "Bearer";
	private String refreshToken;
	private String accessToken;
	
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String getType() {
		return type;
	}
}
