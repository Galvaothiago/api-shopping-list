package com.api.shopping.list.payload.response.exception;

import java.util.ArrayList;
import java.util.List;

public class InvalidPasswordPolicyResponse {
	private int status;
	private String message;
	private int quantityRulesBroken;
	private List<String> rulesBroken = new ArrayList<>();
	
	public InvalidPasswordPolicyResponse() {
		
	}
	
	public InvalidPasswordPolicyResponse(int status, String message, int quantityRulesBroken,
			List<String> rulesBroken) {
		this.status = status;
		this.message = message;
		this.quantityRulesBroken = quantityRulesBroken;
		this.rulesBroken = rulesBroken;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getQuantityRulesBroken() {
		return quantityRulesBroken;
	}

	public void setQuantityRulesBroken(int quantityRulesBroken) {
		this.quantityRulesBroken = quantityRulesBroken;
	}

	public List<String> getRulesBroken() {
		return rulesBroken;
	}

	public void setRulesBroken(List<String> rulesBroken) {
		this.rulesBroken = rulesBroken;
	}
}
