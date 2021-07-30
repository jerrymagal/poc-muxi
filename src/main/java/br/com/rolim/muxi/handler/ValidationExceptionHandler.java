package br.com.rolim.muxi.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.networknt.schema.ValidationMessage;

import br.com.rolim.muxi.exception.BusinessException;
import br.com.rolim.muxi.exception.JsonValidationFailedException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ControllerAdvice
public class ValidationExceptionHandler {
 
    @ExceptionHandler(JsonValidationFailedException.class)
    public ResponseEntity<Map<String, Object>> onJsonValidationFailedException(JsonValidationFailedException ex) {
        List<String> messages = ex.getValidationResult().stream()
                .map(ValidationMessage::getMessage)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(Map.of(
            "message", "Json validation failed",
            "details", messages
        ));
    }
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
    	ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), Arrays.asList(ex.getMessage()));
    	return ResponseEntity.badRequest().body(errorResponse);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolation(MethodArgumentNotValidException ex) {
		
		List<String> erros = new ArrayList<>();
		
		ex.getBindingResult().getAllErrors().forEach(error -> erros.add(((FieldError) error).getField() + " : " +  error.getDefaultMessage()));
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), erros);
		
		return ResponseEntity.badRequest().body(errorResponse);
	}
    
    @Getter
    @AllArgsConstructor
    class ErrorResponse {
    	private int statusCode;
    	private List<String> erros;
    }
}