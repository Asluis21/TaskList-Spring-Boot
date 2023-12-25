package com.aluis.springboot.task.list.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
    // EXECPCIÓN PARA EL FORMATO DE LA FECHA EN LA URL : pendingTasks?date=20-03-2023
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
	 	 
	    Map<String, Object> errorMessage =  new HashMap<>();
	    errorMessage.put("error", "Invalid date. Expected date format dd-MM-yyyy");
	        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	}
	 
	
	// EXCEPCIÓN PARA EL FORMATO DE LA FECHA EN EL JSON
	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<?> handleInvalidFormatException(InvalidFormatException ex) {
		
	    Map<String, Object> errorMessage =  new HashMap<>();
	 	errorMessage.put("error", "Format date is incorrect");
	 	 
	    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	
	
	// EXECPCIÓN PARA EL ARGUMENTO EN LA URL TIPO NUMERICO
	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<?> handleNumberFormatException(NumberFormatException ex){
		Map<String, Object> errorMessage =  new HashMap<>();
		errorMessage.put("error", "Enter a numeric value");
		 
	    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
}
