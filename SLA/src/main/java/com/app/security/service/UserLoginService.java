package com.app.security.service;

import javax.servlet.http.HttpServletResponse;

import com.app.usermanagement.entity.UserDetailsEntity;

public interface UserLoginService {

	public UserDetailsEntity login(String username, String password,HttpServletResponse httpServletResponse) throws Exception;
}
