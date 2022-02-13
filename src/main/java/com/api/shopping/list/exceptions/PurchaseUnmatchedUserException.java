package com.api.shopping.list.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PurchaseUnmatchedUserException extends ResponseStatusException{
	private static final long serialVersionUID = 1L;

	public PurchaseUnmatchedUserException(HttpStatus status, String reason) {
		super(status, reason);
	}
	
	public PurchaseUnmatchedUserException(String reason) {
		this(HttpStatus.UNAUTHORIZED, reason);
	}

}
