package com.app.handlers;

import java.util.HashMap;
public class AppResponse extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	public static final String DATA_FIELD = "data";
	public static final String CODE_FIELD = "code";
	public static final String MESSAGE_FIELD = "message";
	public static final String RESULT_FIELD = "result";
	public static final String STATUS_CODE_400 = "400";
	public static final String STATUS_CODE_200 = "200";
	public static final String FAIL_MESSAGE = "Request was invalid.";
	public static final String SUCCESS_MESSAGE = "Successful";
	public static final String FAILURE_MESSAGE = "Failed";
	public static final String STATUS = "status";
	public static final String NO_RECORDS = "No Records Found";
	
	public AppResponse()	{
		this.put(STATUS, true);
		this.put(MESSAGE_FIELD, SUCCESS_MESSAGE);
	}
}