package com.app.usermanagement.service;

import com.app.usermanagement.entity.UserDetailsEntity;

public interface UserMessageService {

	public void sendRegistrationMail(UserDetailsEntity userDetails, String password) throws Exception;

	public void sendRegistrationSMS(UserDetailsEntity userDetails, String password) throws Exception;

	public void sendForgotPasswordMail(UserDetailsEntity userDetails) throws Exception;

}
