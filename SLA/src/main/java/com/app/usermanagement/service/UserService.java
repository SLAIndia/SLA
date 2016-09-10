package com.app.usermanagement.service;

import java.util.List;

import com.app.usermanagement.entity.UserDetailsEntity;
import com.app.usermanagement.entity.UserEntity;

public interface UserService {

	public List<UserEntity> getUsers() throws Exception;

	public UserEntity getUser(int userTypeId) throws Exception;

	public void registerUser(UserDetailsEntity userDetails) throws Exception;

	public void updateUser(UserDetailsEntity userDetails) throws Exception;

	public boolean isUsernameAlreadyInUse(String username, int userId);

	public boolean deleteUser(int userTypeId) throws Exception;
}
