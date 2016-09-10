package com.app.usermanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.usermanagement.dao.UserTypeDao;
import com.app.usermanagement.entity.UserTypeEntity;
import com.app.utils.ServiceConstants;

@Service(ServiceConstants.USER_TYPE_SERVICE)
public class UserTypeServiceImpl implements UserTypeService {
	
	@Autowired
	private UserTypeDao userTypeDao;

	@Override
	public List<UserTypeEntity> getUserTypes() throws Exception {
		return userTypeDao.getUserTypes();
	}

	@Override
	public UserTypeEntity getUserType(int userTypeId) throws Exception {
		return userTypeDao.getUserType(userTypeId);
	}

	@Override
	public UserTypeEntity saveUserType(UserTypeEntity userTypeEntity) throws Exception {
		return userTypeDao.saveUserType(userTypeEntity);
	}

	@Override
	public void updateUserType(UserTypeEntity userTypeEntity) throws Exception {
		userTypeDao.updateUserType(userTypeEntity);
	}

	@Override
	public boolean deleteUserType(int userTypeId) throws Exception {
		return userTypeDao.deleteUserType(userTypeId);
	}

}
