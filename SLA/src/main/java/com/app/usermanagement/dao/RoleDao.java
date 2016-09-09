package com.app.usermanagement.dao;

import java.util.List;

import com.app.usermanagement.entity.RoleEntity;


public interface RoleDao {
	public List<RoleEntity> getRoles() throws Exception;
}
