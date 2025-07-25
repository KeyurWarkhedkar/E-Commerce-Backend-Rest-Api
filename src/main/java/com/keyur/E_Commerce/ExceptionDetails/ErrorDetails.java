package com.keyur.E_Commerce.ExceptionDetails;

import java.time.LocalDateTime;

public class ErrorDetails {
	//fields
	private LocalDateTime timestamp;
	private String message;

	//constructor
	public ErrorDetails(LocalDateTime timestamp, String message) {
		this.timestamp = timestamp;
		this.message = message;
	}

	//getters and setters
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
