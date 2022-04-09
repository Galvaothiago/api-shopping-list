package com.api.shopping.list.security.exceptions;

public class TokenException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public TokenException(String message) {
		super(message);
	}

}
