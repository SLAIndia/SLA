package com.inapp.cms.service;

import java.util.HashMap;
import java.util.List;

import com.inapp.cms.entity.BreedKidsEntity;
import com.inapp.cms.entity.BreedingEntity;

public interface BreedingManager {

	BreedingEntity saveBreeding(BreedingEntity objBreedingEntity);
	BreedKidsEntity saveBreedKids(BreedKidsEntity objBreedKidsEntity);
	List<BreedingEntity> getBreedingDetails(BreedingEntity objBreedingEntity);
	public List<HashMap<String, Object>>  getBreedingDetailsByAssignedFarmUser(BreedingEntity objBreedingEntity, int userId, int farmId);
	int deleteBreeding(BreedingEntity objBreedingEntity);
	public List<HashMap<String, Object>> getBreedKidDetails(BreedingEntity objBreedingEntity);
	BreedingEntity updateParents(BreedingEntity objBreedingEntity);
	List<HashMap<String, Object>> getBreedingDetailsofDate(
			BreedingEntity objBreedingEntity, int u_id, int farm_id);
}
