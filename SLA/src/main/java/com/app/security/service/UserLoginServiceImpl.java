package com.app.security.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.security.AppAuthUser;
import com.app.security.dao.UserLoginDao;
import com.app.usermanagement.entity.UserDetailsEntity;
import com.app.utils.ServiceConstants;

@Service(ServiceConstants.USER_LOGIN_SERVICE)
public class UserLoginServiceImpl implements UserLoginService {

	@Autowired
	private UserLoginDao userLoginDao;

	@Autowired
	private AppSecurityTokenService securityTokenService;

	@Override
	public UserDetailsEntity login(String username, String password, HttpServletResponse httpServletResponse) throws Exception {

		UserDetailsEntity userDetailsEntity = userLoginDao.login(username, password);

		AppAuthUser appJwtUser = new AppAuthUser(userDetailsEntity);

		final String token = securityTokenService.generateToken(appJwtUser);

		httpServletResponse.setHeader("Token", token);

		return userDetailsEntity;
	}

}
