package com.api.shopping.list.exceptions;

public class TokenException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public TokenException(String reason) {
		super(reason);
	}
}
