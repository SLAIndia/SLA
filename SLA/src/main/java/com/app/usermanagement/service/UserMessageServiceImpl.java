package com.app.usermanagement.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.usermanagement.entity.UserDetailsEntity;
import com.app.utils.EmailTemplate;
import com.app.utils.EmailUser;
import com.app.utils.ServiceConstants;

@Service(ServiceConstants.USER_MESSAGE_SERVICE)
public class UserMessageServiceImpl implements UserMessageService {

	private final String USER_LOGIN_MAIL_BODY = "email-templates/User-Registration.html";

	public String getHTMLContents(String file) throws IOException {
		return new String(Files
				.readAllBytes(Paths.get(UserMessageServiceImpl.class.getClassLoader().getResource(file).getPath())));
	}

	@Override
	public void sendRegistrationMail(UserDetailsEntity userDetails, String password) throws Exception {

		String body = getHTMLContents(USER_LOGIN_MAIL_BODY);

		String dearUser = Optional.ofNullable(userDetails.getFname()).orElse("user");
		body = body.replace("@#@dearuser", dearUser)
				.replace("@#@Username", userDetails.getUser().getUsername())
				.replace("@#@Password", password);

		String emailContents = new EmailTemplate().getContents(body);

		EmailUser.emailUser("cibye221b@gmail.com" , "SLA account creation Details ", emailContents);
		//EmailUser.emailUser(  userDetails.getUser().getUsername(), "SLA account creation Details ", emailContents);
	}

	@Override
	public void sendRegistrationSMS(UserDetailsEntity userDetails) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendForgotPasswordMail(UserDetailsEntity userDetails) throws Exception {

	}

}
