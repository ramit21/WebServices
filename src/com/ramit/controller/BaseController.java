package com.ramit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class BaseController {
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleException(Exception e) {
		System.out.println("Exception handled in controller");
		// Return html in error response
		return new ResponseEntity<String>("<html><body><h1>"+ e.getMessage() +"</h1></body></html>",HttpStatus.BAD_REQUEST); //BAD_REQUEST = 400
	}
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
		System.out.println("Runtime Exception handled in controller");
		return new ResponseEntity<String>("<html><body><h1>"+ e.getMessage() +"</h1></body></html>",HttpStatus.EXPECTATION_FAILED); //BAD_REQUEST = 407
	}
	
}
