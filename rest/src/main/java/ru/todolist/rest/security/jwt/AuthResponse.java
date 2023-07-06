package ru.todolist.rest.security.jwt;

import io.swagger.v3.oas.annotations.media.Schema;

public class AuthResponse {
	@Schema(description = "Type of token", example = "Bearer", required = true)
	private final String type = "Bearer";
	@Schema(description = "Refresh token, used to get new access token", example = "header.claims.signature", required = true)
	private String refreshToken;
	@Schema(description = "Access token, used for authorization", example = "header.claims.signature", required = true)
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
