package com.app.usermanagement.dao;

import java.util.List;

import com.app.usermanagement.entity.UserTypeEntity;

public interface UserTypeDao {
	public List<UserTypeEntity> getUserTypes() throws Exception;
	public UserTypeEntity getUserType(int userTypeId) throws Exception;
	public UserTypeEntity saveUserType(UserTypeEntity userTypeEntity) throws Exception;
	public void updateUserType(UserTypeEntity userTypeEntity) throws Exception;	
	public boolean deleteUserType(int userTypeId) throws Exception;
}
