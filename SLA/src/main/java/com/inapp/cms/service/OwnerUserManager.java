package com.inapp.cms.service;

import java.util.List;

import com.inapp.cms.entity.OwnerEntity;

public interface OwnerUserManager {

	public OwnerEntity saveOwnerDetails(OwnerEntity objOwnerEntity);
	public List<Object> getUserDetails(int userId, String role);
	public List<Object> getFarmsAssignedByUserId(int userId, String role);
	public int saveOwnerFarmMapping(OwnerEntity objOwnerEntity);
}
