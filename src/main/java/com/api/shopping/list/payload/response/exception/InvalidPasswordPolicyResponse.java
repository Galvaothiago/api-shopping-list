package com.api.shopping.list.payload.response.exception;

import java.util.ArrayList;
import java.util.List;

public class InvalidPasswordPolicyResponse {
	private int status;
	private String message;
	private int quantityRulesViolated;
	private List<String> rulesViolated = new ArrayList<>();
	
	public InvalidPasswordPolicyResponse() {
		
	}
	
	public InvalidPasswordPolicyResponse(int status, String message, int quantityRulesViolated,
			List<String> rulesBroken) {
		this.status = status;
		this.message = message;
		this.quantityRulesViolated = quantityRulesViolated;
		this.rulesViolated = rulesBroken;
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
		return quantityRulesViolated;
	}

	public void setQuantityRulesBroken(int quantityRulesViolated) {
		this.quantityRulesViolated = quantityRulesViolated;
	}

	public List<String> getRulesBroken() {
		return rulesViolated;
	}

	public void setRulesBroken(List<String> rulesViolated) {
		this.rulesViolated = rulesViolated;
	}
}
