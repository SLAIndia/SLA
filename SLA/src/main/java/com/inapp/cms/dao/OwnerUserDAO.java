package com.inapp.cms.dao;

import java.util.List;

import com.inapp.cms.entity.OwnerEntity;


public interface OwnerUserDAO {
	
	 public OwnerEntity saveOwnerDetails(OwnerEntity objOwnerEntity);
	 public List<Object> getUserDetails(int userId, String role);
	 public int saveOwnerFarmMapping(OwnerEntity objOwnerEntity);
	 public List<Object> getFarmsAssignedByUserId(int userId, String role);
}
