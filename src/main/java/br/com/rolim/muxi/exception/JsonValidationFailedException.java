package br.com.rolim.muxi.exception;

import java.util.Set;

import com.networknt.schema.ValidationMessage;

import lombok.Getter;

@Getter
public class JsonValidationFailedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private Set<ValidationMessage> validationResult;

	public JsonValidationFailedException(Set<ValidationMessage> validationResult) {
		this.validationResult = validationResult;
	}

}
