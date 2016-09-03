package com.inapp.cms.utils;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

public class DateDeserializer implements JsonDeserializer<Timestamp> {
	private static final Logger logger = Logger
			.getLogger(DateDeserializer.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd-MM-yyyy HH:mm:ss");

	public Timestamp deserialize(JsonElement dateStr, Type typeOfSrc,
			JsonDeserializationContext context) {
		try {
			
			Date parsedDate = dateFormat.parse(dateStr.getAsString());
		
			Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

			return timestamp;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in deserialize method"+dateStr+"---" + e.getMessage());
		}
		return null;
	}
}