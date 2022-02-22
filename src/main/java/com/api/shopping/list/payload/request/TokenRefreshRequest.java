package com.api.shopping.list.payload.request;

public class TokenRefreshRequest {
	private String accessToken;
	private String refreshToken;
	private String tokenType = "Bearer";
	
	public TokenRefreshRequest() {
		
	}
	
	public TokenRefreshRequest(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
	    this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
}
