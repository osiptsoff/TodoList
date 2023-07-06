package ru.todolist.rest.security.jwt;

import io.swagger.v3.oas.annotations.media.Schema;

public class RefreshRequest {
	@Schema(description = "Refresh token, used to get new access token", example = "header.claims.signature", required = true)

	private String refreshToken;

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
