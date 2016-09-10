package com.app.utils;

public class AppException extends Exception {

	private static final long serialVersionUID = 1L;

	private AppMessage appMessage;

	public AppException(AppMessage appMessage) {
		this.appMessage = appMessage;
	}

	public String getMsg() {
		return this.appMessage.getValue();
	}

	public int getStatusCode() {
		return this.appMessage.getStatusCode();
	}
}
