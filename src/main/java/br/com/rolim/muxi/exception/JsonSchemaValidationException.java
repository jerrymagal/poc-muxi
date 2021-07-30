package br.com.rolim.muxi.exception;

public class JsonSchemaValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JsonSchemaValidationException(String msg) {
		super(msg);
	}
	
	public JsonSchemaValidationException(String msg, Exception e) {
		super(msg, e);
	}
}
