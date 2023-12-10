package com.sparta.stairs.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<String> customExHandle(CustomException e) {
		return new ResponseEntity<>(e.getMessage(), e.getStatus());
	}

}
