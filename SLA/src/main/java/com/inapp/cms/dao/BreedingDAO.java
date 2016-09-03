package com.inapp.cms.dao;

import java.util.HashMap;
import java.util.List;

import com.inapp.cms.entity.BreedKidsEntity;
import com.inapp.cms.entity.BreedingEntity;

public interface BreedingDAO {

	public BreedingEntity saveBreeding(BreedingEntity objBreedingEntity);
	public BreedKidsEntity saveBreedKids(BreedKidsEntity objBreedKidsEntity);
	public List<BreedingEntity> getBreedingDetails(BreedingEntity objBreedingEntity);
	public List<HashMap<String, Object>> getBreedingDetailsByAssignedFarmUser(BreedingEntity objBreedingEntity, int userId, int farmId);
	public List<HashMap<String, Object>> getBreedKidDetails(BreedingEntity objBreedingEntity);
	public int deleteBreeding(BreedingEntity objBreedingEntity);
	public BreedingEntity updateParents(BreedingEntity objBreedingEntity);
	public List<HashMap<String, Object>> getBreedingDetailsofDate(
			BreedingEntity objBreedingEntity, int u_id, int farm_id);
}
