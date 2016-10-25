package com.app.security.dao;

import com.app.usermanagement.entity.UserDetailsEntity;

public interface UserLoginDao {

	public UserDetailsEntity login(String username, String password) throws Exception;



}
