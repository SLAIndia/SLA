package com.app.utils;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum AppMessage {
	NOT_FOUND,
	INVALID_REQUEST,
	SUCCESS,
	INVALID_USER,
	INTERNAL_SERVER_ERROR,
	USERNAME_EXISTS,
	RECORD_NOT_FOUND;
	private static final String PATH = "/appconstants.properties";

	private static final Logger logger = LoggerFactory.getLogger(AppMessage.class);

	private static Properties properties;

	private int statusCode = 0;
	private String value;

	private void init() {
		if (properties == null) {
			properties = new Properties();
			try {
				properties.load(AppMessage.class.getResourceAsStream(PATH));
			} catch (Exception e) {
				logger.error("Unable to load " + PATH + " file from classpath.", e);
				System.exit(1);
			}
		}

		String property[] = ((String) properties.getProperty(this.toString())).split(",", 2);
		statusCode = Integer.parseInt(property[0]);
		value = property[1];
	}

	public String getValue() {
		if (value == null) {
			init();
		}
		return value;
	}

	public int getStatusCode() {
		if (statusCode == 0) {
			init();
		}
		return statusCode;
	}
}
