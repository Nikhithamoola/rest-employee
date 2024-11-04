package com.sathya.rest.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Globalexceptionhandler {
@ExceptionHandler
public ResponseEntity<Map<String,String>>
exceptionalHandler2(MethodArgumentNotValidException exception)
{
	Map<String,String> errorMap = new HashMap<>();
	exception.getBindingResult()
	         .getFieldErrors()
	         .forEach(error -> {
	        	 String name=error.getField();
	        	 String msg=error.getDefaultMessage();
	        	 errorMap.put(name,msg);
	         });
	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(errorMap);
}
}
