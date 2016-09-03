package com.inapp.cms.service;

import java.util.HashMap;
import java.util.List;

import com.inapp.cms.entity.BreedKidsEntity;
import com.inapp.cms.entity.HealthCareEntity;
import com.inapp.cms.entity.HealthcareVaccLinkEntity;

public interface HealthCareManager {

	HealthCareEntity saveHealthCare(HealthCareEntity objHealthCareEntity);
	BreedKidsEntity saveBreedKids(BreedKidsEntity objBreedKidsEntity);
	List<HealthCareEntity> getHealthCareDetails(HealthCareEntity objHealthCareEntity);
	int deleteHealthCare(HealthCareEntity objHealthCareEntity);
	List<HealthcareVaccLinkEntity> getHealthCareTypeDetails(
			HealthCareEntity objHealthCareEntity);
	HealthcareVaccLinkEntity saveHealthCareTypeDetails(HealthcareVaccLinkEntity objVaccLinkEntity);
	List<HealthcareVaccLinkEntity> getHealthCareTypeDetailsById(int vaccId);
	public List<HashMap<String, Object>> getHealthCareDetailsByRole(HealthCareEntity objHealthCareEntity, String rolename, int userId);
}
