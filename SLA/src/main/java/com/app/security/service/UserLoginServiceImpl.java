package com.app.security.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.security.AppJwtTokenUtil;
import com.app.security.AppJwtUser;
import com.app.security.dao.UserLoginDao;
import com.app.usermanagement.entity.UserDetailsEntity;
import com.app.utils.ServiceConstants;

@Service(ServiceConstants.USER_LOGIN_SERVICE)
public class UserLoginServiceImpl implements UserLoginService {

	@Autowired
	private UserLoginDao userLoginDao;

	@Autowired
	private AppJwtTokenUtil appJwtTokenUtil;

	@Override
	public UserDetailsEntity login(String username, String password, HttpServletResponse httpServletResponse) throws Exception {

		UserDetailsEntity userDetailsEntity = userLoginDao.login(username, password);

		AppJwtUser appJwtUser = new AppJwtUser(userDetailsEntity);

		final String token = appJwtTokenUtil.generateToken(appJwtUser);

		httpServletResponse.setHeader("Token", token);

		return userDetailsEntity;
	}

}
