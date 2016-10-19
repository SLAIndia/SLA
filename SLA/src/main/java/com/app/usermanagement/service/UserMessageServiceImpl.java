package com.app.usermanagement.service;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import com.app.usermanagement.entity.UserDetailsEntity;
import com.app.utils.EmailTemplate;

import com.app.utils.ServiceConstants;

@Service(ServiceConstants.USER_MESSAGE_SERVICE)
public class UserMessageServiceImpl implements UserMessageService {

	@Override
	public void sendRegistrationMail(UserDetailsEntity userDetails) throws Exception {
		String body = new String(Files.readAllBytes(Paths.get(
				UserMessageServiceImpl.class.getClassLoader().getResource("email-templates/UserReg.html").getPath())));

		String emailContents = new EmailTemplate().getContents(body);

		System.out.println(" emailContents ---> \n\n " + emailContents);
	}

	@Override
	public void sendRegistrationSMS(UserDetailsEntity userDetails) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendForgotPasswordMail(UserDetailsEntity userDetails) throws Exception {

	}

}
