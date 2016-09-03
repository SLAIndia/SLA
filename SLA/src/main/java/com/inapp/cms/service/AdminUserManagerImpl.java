package com.inapp.cms.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inapp.cms.dao.AdminUserDAO;
import com.inapp.cms.entity.RoleEntity;
import com.inapp.cms.entity.UserEntity;
import com.inapp.cms.utils.ServiceConstants;

@Service(ServiceConstants.ADMIN_MANAGER)
public class AdminUserManagerImpl implements AdminUserManager {

	
	@Autowired
	private AdminUserDAO adminUserDAO;
	
	private static final Logger logger = Logger
			.getLogger(AdminUserManagerImpl.class);

	@Override
	public UserEntity saveUserDetails(UserEntity objUserEntity) {
		return adminUserDAO.saveUserDetails(objUserEntity);
	}

	@Override
	public RoleEntity getRole(UserEntity objUserEntity) {
		return adminUserDAO.getRole(objUserEntity);
	}
}
