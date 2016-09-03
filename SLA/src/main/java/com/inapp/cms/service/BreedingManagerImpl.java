package com.inapp.cms.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.utils.ServiceConstants;
import com.inapp.cms.dao.BreedingDAO;
import com.inapp.cms.entity.BreedKidsEntity;
import com.inapp.cms.entity.BreedingEntity;

@Service(ServiceConstants.BREEDING_MANAGER)
public class BreedingManagerImpl implements BreedingManager{

	@Autowired
    private BreedingDAO breedingDAO;
	
	@Override
	public BreedingEntity saveBreeding(BreedingEntity objBreedingEntity) {
		return breedingDAO.saveBreeding(objBreedingEntity);
	}

	@Override
	public BreedKidsEntity saveBreedKids(BreedKidsEntity objBreedKidsEntity) {
		return breedingDAO.saveBreedKids(objBreedKidsEntity);
	}
	
	@Override
	public List<BreedingEntity> getBreedingDetails(BreedingEntity objBreedingEntity) {
		return breedingDAO.getBreedingDetails(objBreedingEntity);
	}
	
	@Override
	public List<HashMap<String, Object>> getBreedingDetailsByAssignedFarmUser(BreedingEntity objBreedingEntity, int userId, int farmId) {
		return breedingDAO.getBreedingDetailsByAssignedFarmUser(objBreedingEntity,userId,farmId);
	}


	@Override
	public int deleteBreeding(BreedingEntity objBreedingEntity) {
		return breedingDAO.deleteBreeding(objBreedingEntity);
	}

	@Override
	public List<HashMap<String, Object>> getBreedKidDetails(
			BreedingEntity objBreedingEntity) {
		return breedingDAO.getBreedKidDetails(objBreedingEntity);
	}

	@Override
	public BreedingEntity updateParents(BreedingEntity objBreedingEntity) {
		return breedingDAO.updateParents(objBreedingEntity);
	}

	@Override
	public List<HashMap<String, Object>> getBreedingDetailsofDate(
			BreedingEntity objBreedingEntity, int u_id, int farm_id) {
		return breedingDAO.getBreedingDetailsofDate(objBreedingEntity,u_id,farm_id);
	}
	
}
