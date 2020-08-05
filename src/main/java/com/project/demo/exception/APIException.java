package com.project.demo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class APIException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private ExceptionResponse response = new ExceptionResponse();
	
	public APIException(String message) {
		response.setTimeStamp(LocalDateTime.now());
		if(message.contains("not found") || message.contains("empty"))
			response.setError(HttpStatus.NOT_FOUND);
		else
			response.setError(HttpStatus.BAD_REQUEST);
		response.setMessage(message);
		response.setStatus(response.getError().value());
	}
	public ExceptionResponse getResponse() {
		return response;
	}
	public HttpStatus getStatus() {
		return response.getError();
	}
}