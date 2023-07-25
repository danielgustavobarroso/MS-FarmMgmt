package com.retooling.farm.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FarmNotFoundException extends Exception {

	public FarmNotFoundException() {
		super();
	}
	
	public FarmNotFoundException(String message) {
		super(message);
	}
	
}
