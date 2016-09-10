package com.app.usermanagement.dao;

import java.util.List;

import com.app.usermanagement.entity.UserDetailsEntity;
import com.app.usermanagement.entity.UserEntity;

public interface UserDao {
	public List<UserEntity> getUsers() throws Exception;

	public UserEntity getUser(int userTypeId) throws Exception;

	public UserEntity saveUser(UserEntity userEntity) throws Exception;

	public UserDetailsEntity saveUserDetails(UserDetailsEntity userDetailsEntity) throws Exception;

	public void updateUser(UserEntity userEntity) throws Exception;

	public boolean isUsernameAlreadyInUse(String username, int userId);

	public boolean deleteUser(int userTypeId) throws Exception;
}
