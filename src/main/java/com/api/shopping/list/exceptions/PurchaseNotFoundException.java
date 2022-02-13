package com.api.shopping.list.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PurchaseNotFoundException extends ResponseStatusException{

	private static final long serialVersionUID = 1L;
	
	public PurchaseNotFoundException(HttpStatus status, String reason) {
		super(status, reason);
	}

	public PurchaseNotFoundException(String reason) {
		this(HttpStatus.NOT_FOUND, reason);
	}

}
