package com.api.shopping.list.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TokenException extends ResponseStatusException {
	private static final long serialVersionUID = 1L;
	
	public TokenException(HttpStatus status, String reason) {
		super(status, reason);
	
	}
	
	public TokenException(String reason) {
		this(HttpStatus.UNAUTHORIZED, reason);
	}
}
