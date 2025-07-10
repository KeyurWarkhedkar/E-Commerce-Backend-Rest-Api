package com.keyur.E_Commerce.DTOs;


public class SessionDTO {
	//fields
	private String token;
	private String message;

	//no args constructor
	public SessionDTO() {
	}


	//getters and setters
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
