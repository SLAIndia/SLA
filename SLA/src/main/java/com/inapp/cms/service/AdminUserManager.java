package com.inapp.cms.service;

import com.inapp.cms.entity.RoleEntity;
import com.inapp.cms.entity.UserEntity;

public interface AdminUserManager {

	public UserEntity saveUserDetails(UserEntity objUserEntity);
	public RoleEntity getRole(UserEntity objUserEntity);
}
