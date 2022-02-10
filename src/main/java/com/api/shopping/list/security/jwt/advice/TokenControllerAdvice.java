package com.api.shopping.list.security.jwt.advice;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.api.shopping.list.security.exceptions.ErrorMessage;
import com.api.shopping.list.security.exceptions.TokenRefreshException;

public class TokenControllerAdvice {
	@ExceptionHandler(value = TokenRefreshException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ErrorMessage handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
		return new ErrorMessage(
	        HttpStatus.FORBIDDEN.value(),
	        new Date(),
	        ex.getMessage(),
	        request.getDescription(false));
	  }
}
