package com.inapp.cms.dao;

import com.inapp.cms.entity.RoleEntity;
import com.inapp.cms.entity.UserEntity;


public interface AdminUserDAO {

 public UserEntity saveUserDetails(UserEntity objUserEntity);
 public RoleEntity getRole(UserEntity objUserEntity);
}
