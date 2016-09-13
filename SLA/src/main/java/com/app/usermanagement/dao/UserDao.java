package com.app.usermanagement.dao;

import java.util.List;

import com.app.usermanagement.entity.UserDetailsEntity;
import com.app.usermanagement.entity.UserEntity;

public interface UserDao {
	public List<UserDetailsEntity> getUsers() throws Exception;

	public UserDetailsEntity getUser(int userTypeId) throws Exception;

	public UserEntity saveUser(UserEntity userEntity) throws Exception;

	public UserDetailsEntity saveUserDetails(UserDetailsEntity userDetailsEntity) throws Exception;

	public void updateUser(UserEntity userEntity) throws Exception;
	public void updateUserDetails(UserDetailsEntity userDetailsEntity) throws Exception;

	public boolean isUsernameAlreadyInUse(String username, Integer userId) throws Exception;

	public boolean isPhoneAlreadyInUse(String phone1, Integer userId) throws Exception;

	public boolean deleteUser(int userTypeId) throws Exception;
}
