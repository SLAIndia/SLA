package com.app.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EmailTemplate {

	private String header;

	private String footer;

	public EmailTemplate() {
		try {
			this.header = new String(Files.readAllBytes(Paths.get(EmailTemplate.class.getClassLoader().getResource("email-templates/Header.html").getPath())));
			this.footer = new String(Files.readAllBytes(Paths.get(EmailTemplate.class.getClassLoader().getResource("email-templates/Footer.html").getPath())));
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public String getContents(String body) {
		StringBuilder contentBuilder = new StringBuilder();
		contentBuilder.append(header);
		contentBuilder.append(body);
		contentBuilder.append(footer);
		return contentBuilder.toString();
	}

}
