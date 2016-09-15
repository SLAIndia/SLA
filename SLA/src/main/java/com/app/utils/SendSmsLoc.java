package com.app.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
 
public class SendSmsLoc {
	public static String sendSms() {
		try {
			// Construct data
			String user = "username=" + "jinesh4ever@gmail.com";
			//String apiKey = "&apiKey=" + "nh2UTqFBuAQ-GmM3NePIUViSHGPTBIwN712QOqjHxt";
			String hash = "&hash=" + "Sla123321";
			String message = "&message=" + "This is your message";
			String sender = "&sender=" + "Jinesh George";
			String numbers = "&numbers=" + "919895229827";
			
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("http://api.txtlocal.com/send/?").openConnection();
			String data = user + hash + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
			
			return stringBuffer.toString();
		} catch (Exception e) {
			System.out.println("Error SMS "+e);
			return "Error "+e;
		}
	}
	public static void main(String s[])
	{
		System.out.println(sendSms());
		System.out.println("sent........");
	}
}