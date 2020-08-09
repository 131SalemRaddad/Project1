package com.project.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
	@ExceptionHandler(value = APIException.class)
	public ResponseEntity<Object> exception(APIException exception) {
		return new ResponseEntity<>(exception.getResponse(), exception.getStatus());
	}
}