package com.api.shopping.list.security.exceptions;

import java.time.Instant;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.api.shopping.list.exceptions.PurchaseItemNotExistsException;
import com.api.shopping.list.exceptions.PurchaseNotFoundException;
import com.api.shopping.list.exceptions.PurchaseUnmatchedUserException;
import com.api.shopping.list.payload.response.exception.PurchaseErrorMessage;

@ControllerAdvice
public class HandlerExceptions {
	
	@ExceptionHandler(TokenRefreshException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ErrorMessage handleTokenRefreshException(TokenRefreshException e, WebRequest request) {
		return new ErrorMessage(
	        HttpStatus.FORBIDDEN.value(),
	        new Date(),
	        e.getMessage(),
	        request.getDescription(false));  
	}
	
	@ExceptionHandler(PurchaseNotFoundException.class)
	public ResponseEntity<?> PurchaseNotFoundException(PurchaseNotFoundException e) {
		PurchaseErrorMessage errorMessage = new PurchaseErrorMessage();
		
		errorMessage.setTimestamp(Instant.now());
		errorMessage.setMessage(e.getReason());
		errorMessage.setStatus(e.getStatus().value());
		errorMessage.setError(e.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}
	
	@ExceptionHandler(PurchaseItemNotExistsException.class)
	public ResponseEntity<?> PurchaseItemNotExistsException(PurchaseItemNotExistsException e) {
		PurchaseErrorMessage errorMessage = new PurchaseErrorMessage();
		
		errorMessage.setTimestamp(Instant.now());
		errorMessage.setMessage(e.getReason());
		errorMessage.setStatus(e.getStatus().value());
		errorMessage.setError(e.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	}
	
	@ExceptionHandler(PurchaseUnmatchedUserException.class)
	public ResponseEntity<?> PurchaseUnmatchedUserException(PurchaseUnmatchedUserException e) {
		PurchaseErrorMessage errorMessage = new PurchaseErrorMessage();
		
		errorMessage.setTimestamp(Instant.now());
		errorMessage.setMessage(e.getReason());
		errorMessage.setStatus(e.getStatus().value());
		errorMessage.setError(e.getMessage());
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
	}

}
