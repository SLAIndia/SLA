package com.inapp.cms.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inapp.cms.dao.HealthCareDAO;
import com.inapp.cms.entity.BreedKidsEntity;
import com.inapp.cms.entity.HealthCareEntity;
import com.inapp.cms.entity.HealthcareVaccLinkEntity;
import com.inapp.cms.utils.ServiceConstants;

@Service(ServiceConstants.HEALTHCARE_MANAGER)
public class HealthCareManagerImpl implements HealthCareManager{

	@Autowired
    private HealthCareDAO healthcareDAO;
	
	@Override
	public HealthCareEntity saveHealthCare(HealthCareEntity objHealthCareEntity) {
		return healthcareDAO.saveHealthCare(objHealthCareEntity);
	}
	
	@Override
	public HealthcareVaccLinkEntity saveHealthCareTypeDetails(HealthcareVaccLinkEntity objHealthVaccLinkEntity) {
		return healthcareDAO.saveHealthCareTypeDetails(objHealthVaccLinkEntity);
	}


	@Override
	public BreedKidsEntity saveBreedKids(BreedKidsEntity objBreedKidsEntity) {
		return healthcareDAO.saveBreedKids(objBreedKidsEntity);
	}
	
	@Override
	public List<HealthCareEntity> getHealthCareDetails(HealthCareEntity objHealthCareEntity) {
		return healthcareDAO.getHealthCareDetails(objHealthCareEntity);
	}
	
	@Override
	public List<HealthcareVaccLinkEntity> getHealthCareTypeDetails(HealthCareEntity objHealthCareEntity) {
		return healthcareDAO.getHealthCareTypeDetails(objHealthCareEntity);
	}

	@Override
	public int deleteHealthCare(HealthCareEntity objHealthCareEntity) {
		return healthcareDAO.deleteHealthCare(objHealthCareEntity);
	}

	@Override
	public List<HealthcareVaccLinkEntity> getHealthCareTypeDetailsById(int vaccId) {
		return healthcareDAO.getHealthCareTypeDetailsById(vaccId);
	}

	@Override
	public List<HashMap<String, Object>> getHealthCareDetailsByRole(
			HealthCareEntity objHealthCareEntity, String rolename, int userId) {
		return healthcareDAO.getHealthCareDetailsByRole(objHealthCareEntity, rolename, userId);
	}

}
