package com.rajen.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rajen.dtos.apiresponse;

@RestControllerAdvice
public class globalexception {

	
	@ExceptionHandler(resourcenotfoundexception.class)
	public ResponseEntity<apiresponse> resourcnotfoundexcption(resourcenotfoundexception rs){
		
		apiresponse apiresponse= new apiresponse();
		apiresponse.setMessage(rs.getMessage());
		apiresponse.setStatus(HttpStatus.NOT_FOUND);
		apiresponse.setSucess(true);
		return new ResponseEntity<apiresponse>(apiresponse,HttpStatus.OK);
	}
	
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
	        Map<String, String> validationErrors = new HashMap<>();

	        List<ObjectError> errors = ex.getBindingResult().getAllErrors();

	        for (ObjectError error : errors) {
	            if (error instanceof FieldError) {
	                FieldError fieldError = (FieldError) error;
	                validationErrors.put(fieldError.getField(), error.getDefaultMessage());
	            }
	        }

	        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
	    }
	 
	 
	 
	 @ExceptionHandler(badapirequest.class)
		public ResponseEntity<apiresponse> handlebadapirequest(badapirequest rs){
			
			apiresponse apiresponse= new apiresponse();
			apiresponse.setMessage(rs.getMessage());
			apiresponse.setStatus(HttpStatus.BAD_REQUEST);
			apiresponse.setSucess(false);
			return new ResponseEntity<apiresponse>(apiresponse,HttpStatus.BAD_REQUEST);
		}
		 @ExceptionHandler(BadCredentialsExceptionn.class)
			public ResponseEntity<apiresponse> BadCredentialsExceptionn(BadCredentialsExceptionn rs){
				
				apiresponse apiresponse= new apiresponse();
				apiresponse.setMessage(rs.getMessage());
				apiresponse.setStatus(HttpStatus.BAD_REQUEST);
				apiresponse.setSucess(false);
				return new ResponseEntity<apiresponse>(apiresponse,HttpStatus.BAD_REQUEST);
			}
	
}
