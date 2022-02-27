package com.api.shopping.list.security.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ValidatePolicyPasswordException extends ResponseStatusException{
	private static final long serialVersionUID = 1L;
	
	private List<String> violatedRules = new ArrayList<>();

	public ValidatePolicyPasswordException(HttpStatus status, String reason) {
		super(status, reason);
	}

	public ValidatePolicyPasswordException(String reason, List<String> rules) {
		this(HttpStatus.BAD_REQUEST, reason);
		this.violatedRules = rules;
	}

	public List<String> getViolatedRules() {
		return violatedRules;
	}

	public void setViolatedRules(List<String> violatedRules) {
		this.violatedRules = violatedRules;
	}

	
}
