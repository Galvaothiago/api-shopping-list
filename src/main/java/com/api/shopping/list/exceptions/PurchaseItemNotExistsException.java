package com.api.shopping.list.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PurchaseItemNotExistsException extends ResponseStatusException {
	private static final long serialVersionUID = 1L;

	public PurchaseItemNotExistsException(HttpStatus status, String reason) {
		super(status, reason);
	}
	
	public PurchaseItemNotExistsException(String reason) {
		this(HttpStatus.BAD_REQUEST, reason);
	}

	

}
