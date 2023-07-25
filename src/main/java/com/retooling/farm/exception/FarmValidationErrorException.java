package com.retooling.farm.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FarmValidationErrorException extends Exception {

	public FarmValidationErrorException() {
		super();
	}
	
	public FarmValidationErrorException(String message) {
		super(message);
	}
	
}
