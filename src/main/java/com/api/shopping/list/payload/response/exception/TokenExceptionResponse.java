package com.api.shopping.list.payload.response.exception;

import java.time.Instant;

public class TokenExceptionResponse {
	private Instant timestamp;
	private int status;
	private String error;
	private String message;
	
	public TokenExceptionResponse() {
		
	}
	
	public TokenExceptionResponse(int status, String error, String message) {
		this.status = status;
		this.error = error;
		this.timestamp = Instant.now();
		this.message = message;
	}
	public Instant getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
