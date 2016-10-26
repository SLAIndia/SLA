package com.app.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EmailTemplate {
	private final String USER_MAIL_HEADER = "email-templates/Header.html";
	private final String USER_MAIL_FOOTER = "email-templates/Footer.html";
	private String header;

	private String footer;



	public EmailTemplate() {
		try {
			this.header = new String(Files.readAllBytes(
					Paths.get(EmailTemplate.class.getClassLoader().getResource(USER_MAIL_HEADER).getPath())));
			this.footer = new String(Files.readAllBytes(
					Paths.get(EmailTemplate.class.getClassLoader().getResource(USER_MAIL_FOOTER).getPath())));

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
