package com.app.usermanagement.service;

import java.util.List;

import com.app.usermanagement.entity.RoleEntity;



public interface RoleService {
	public List<RoleEntity> getRoles() throws Exception;
}
