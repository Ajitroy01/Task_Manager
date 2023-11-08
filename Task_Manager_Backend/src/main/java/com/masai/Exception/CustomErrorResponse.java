package com.masai.Exception;

import java.time.LocalDateTime;

public class CustomErrorResponse {
	private String message;
	private String description;
	private LocalDateTime timestamp = LocalDateTime.now();
	
	public CustomErrorResponse() {
		
	}

	public CustomErrorResponse(String message, String description, LocalDateTime timestamp) {
		super();
		this.message = message;
		this.description = description;
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
