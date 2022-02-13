package com.api.shopping.list.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PurchaseUnmatchedUser extends ResponseStatusException{
	private static final long serialVersionUID = 1L;

	public PurchaseUnmatchedUser(HttpStatus status, String reason) {
		super(status, reason);
	}
	
	public PurchaseUnmatchedUser(String reason) {
		this(HttpStatus.UNAUTHORIZED, reason);
	}

}
