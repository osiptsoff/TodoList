package ru.todolist.rest.security.jwt;

import io.swagger.v3.oas.annotations.media.Schema;

public class RefreshResponse {
	@Schema(description = "Access token, used for authorization", example = "header.claims.signature", required = true)
	private String accessToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
