package com.api.shopping.list.security.services;

import java.util.ArrayList;
import java.util.List;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

public class ValidatePolicyPassword {
	
	private String password;
	
	private RuleResult result;
	
	private List<String> listRulesViolated = new ArrayList<>();
	
	
	public ValidatePolicyPassword(String password) {
		this.password = password;
	}
	
	public boolean validate() {
		result = createValidationPolicy().validate(new PasswordData(password));
		
		if (result.isValid()) {
			return true;		
		}
		return false;
	}
	
	public void brokenRulesMessagens() {
		List<String> violationMessages = createValidationPolicy().getMessages(result);
		
		if(!this.validate()) {
			for(String message : violationMessages) {
				listRulesViolated.add(message);
				System.out.println(message);
			}
		}
	}

	private PasswordValidator createValidationPolicy() {
		
		var validator = new PasswordValidator(
				//length between 8 and 16 characters
				new LengthRule(8, 16),
				
				//at least one upper-case character
				new CharacterRule(EnglishCharacterData.UpperCase, 1),
				
				//at least one lower-case character
				new CharacterRule(EnglishCharacterData.LowerCase, 1),
				
				//at least one digit character
				new CharacterRule(EnglishCharacterData.Digit, 1),
				
				//at least one symbol (special character)
				new CharacterRule(EnglishCharacterData.Special, 1),
				
				//define some illegal sequences that will fail when >= 5 chars long
				//alphabetical is of the form 'abcde', numerical is '34567', qwery is 'asdfg'
				//the false parameter indicates that wrapped sequences are allowed; e.g. 'xyzabc'
				new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 3, false),
				new IllegalSequenceRule(EnglishSequenceData.Numerical, 3, false),
				new IllegalSequenceRule(EnglishSequenceData.USQwerty, 5, false),
				
				//no whitespace
				new WhitespaceRule());
		
		return validator;
	}


	public List<String> getListRulesViolated() {
		return listRulesViolated;
	}
}
