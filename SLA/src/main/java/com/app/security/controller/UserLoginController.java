package com.app.security.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.handlers.AppResponse;
import com.app.security.service.UserLoginService;
import com.app.utils.AppException;
import com.app.utils.AppMessage;

@RestController
public class UserLoginController {
	@Autowired
	private UserLoginService userLoginService;

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AppResponse login(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password, HttpServletResponse httpServletResponse)
			throws Exception {

		AppResponse response = new AppResponse();

		response.put(AppResponse.DATA_FIELD, Optional.ofNullable(userLoginService.login(username, password, httpServletResponse))
				.orElseThrow(() -> new AppException(AppMessage.RECORD_NOT_FOUND)));



		/*
		 * loginService.login(credentials) .map(minimalProfile -> { try {
		 * 
		 * } catch (Exception e) { throw new RuntimeException(e); } return
		 * minimalProfile; }) .orElseThrow(() -> new
		 * FailedToLoginException(credentials.getUsername()));
		 */

		return response;
	}
}
