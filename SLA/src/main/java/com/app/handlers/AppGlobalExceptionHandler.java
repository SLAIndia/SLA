package com.app.handlers;

import org.springframework.http.HttpStatus;

import java.util.Optional;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.app.utils.AppException;

@ControllerAdvice
public class AppGlobalExceptionHandler {
	Logger log = Logger.getLogger(AppGlobalExceptionHandler.class);

	@ExceptionHandler(Throwable.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public AppResponse handleException(HttpServletRequest req, Throwable e) {
		log.error(e.getMessage(), e);
		AppResponse response = new AppResponse();
		response.put(response.CODE_FIELD, response.STATUS_CODE_400);
		response.put(response.MESSAGE_FIELD, response.FAIL_MESSAGE);
		response.put(response.STATUS, false);
		return response;
	}

	@ExceptionHandler(AppException.class)
	@ResponseBody
	public AppResponse handleAppException(HttpServletResponse res, AppException e) {
		log.error(e.getMessage(), e);
		res.setStatus(e.getStatusCode());
		AppResponse response = new AppResponse();
		response.put(response.MESSAGE_FIELD, Optional.ofNullable(e.getMsg()).orElse(response.FAIL_MESSAGE));
		response.put(response.STATUS, false);
		return response;
	}

	@ExceptionHandler(AuthenticationException.class)
	@ResponseBody
	public AppResponse handleBadCredentialsException(HttpServletResponse res, AuthenticationException e) {
		log.error(e.getMessage(), e);
		// res.setStatus(e.getStatusCode());
		AppResponse response = new AppResponse();
		response.put(response.CODE_FIELD, "401");
		response.put(response.MESSAGE_FIELD, " ");
		response.put("login_status", "BadCredentials");
		response.put(response.STATUS, false);
		return response;
	}
}
