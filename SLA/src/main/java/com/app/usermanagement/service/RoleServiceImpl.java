package com.app.usermanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.usermanagement.dao.RoleDao;
import com.app.usermanagement.entity.RoleEntity;
import com.app.utils.ServiceConstants;


@Service(ServiceConstants.ROLE_SERVICE)
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleDao roleDao;
	
	@Override
	public List<RoleEntity> getRoles()  throws Exception{
		return roleDao.getRoles();
	}

}
	