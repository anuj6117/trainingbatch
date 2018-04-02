package com.example.demo.model.userModel;

import org.springframework.http.HttpStatus;

public class AppResponse {

	private String result;
	private HttpStatus status;
	private String message;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus badRequest) {
		this.status = badRequest;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}
