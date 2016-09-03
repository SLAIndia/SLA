package com.inapp.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inapp.cms.dao.OwnerUserDAO;
import com.inapp.cms.entity.OwnerEntity;
import com.inapp.cms.utils.ServiceConstants;

@Service(ServiceConstants.OWNER_MANAGER)
public class OwnerUserManagerImpl implements OwnerUserManager {
	
	@Autowired
	private OwnerUserDAO ownerUserDAO;

	@Override
	public OwnerEntity saveOwnerDetails(OwnerEntity objOwnerEntity) {
		
		return ownerUserDAO.saveOwnerDetails(objOwnerEntity);
	}
	@Override
	public List<Object> getUserDetails(int userId, String role) {
		return ownerUserDAO.getUserDetails(userId, role);
	}
	@Override
	public int saveOwnerFarmMapping(OwnerEntity objOwnerEntity) {
		return ownerUserDAO.saveOwnerFarmMapping(objOwnerEntity);
	}
	@Override
	public List<Object> getFarmsAssignedByUserId(int userId, String role) {
		return ownerUserDAO.getFarmsAssignedByUserId(userId, role);
	}
}
