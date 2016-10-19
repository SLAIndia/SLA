package com.app.usermanagement.service;

import java.util.List;

import com.app.usermanagement.entity.UserDetailsEntity;
import com.app.usermanagement.entity.UserEntity;

public interface UserService {

	public List<UserDetailsEntity> getUsers() throws Exception;

	public UserDetailsEntity getUser(int userTypeId) throws Exception;

	public void registerUser(UserDetailsEntity userDetails) throws Exception;

	public void updateUser(UserDetailsEntity userDetails) throws Exception;

	public boolean isUsernameAlreadyInUse(String username, Integer userId) throws Exception;

	public boolean isPhoneAlreadyInUse(String phone1, Integer userId) throws Exception;

	public boolean deleteUser(int userTypeId) throws Exception;

	public boolean approveUser(int status, Integer[] userIds) throws Exception;
}
