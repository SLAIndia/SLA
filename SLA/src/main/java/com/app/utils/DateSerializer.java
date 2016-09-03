package com.app.utils;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateSerializer implements JsonSerializer<Timestamp> {
	private static final Logger logger = Logger.getLogger(DateSerializer.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd-MM-yyyy HH:mm:ss");

	public JsonElement serialize(Timestamp date, Type typeOfSrc,
			JsonSerializationContext context) {
	
		return new JsonPrimitive(dateFormat.format(date));
	}
}
