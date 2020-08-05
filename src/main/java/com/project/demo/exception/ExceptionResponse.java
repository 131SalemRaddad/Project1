package com.project.demo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ExceptionResponse {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timeStamp;
	private int status;
	private HttpStatus error;
	private String message;
	public HttpStatus getError() {
		return error;
	}
	public void setError(HttpStatus error) {
		this.error = error;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "ExceptionResponse [status=" + error + ", timeStamp=" + timeStamp + ", message=" + message + "]";
	}
}