package com.inapp.cms.dao;

import java.util.HashMap;
import java.util.List;

import com.inapp.cms.entity.BreedKidsEntity;
import com.inapp.cms.entity.HealthCareEntity;
import com.inapp.cms.entity.HealthcareVaccLinkEntity;

public interface HealthCareDAO {

	public HealthCareEntity saveHealthCare(HealthCareEntity objHealthCareEntity);
	public BreedKidsEntity saveBreedKids(BreedKidsEntity objBreedKidsEntity);
	public List<HealthCareEntity> getHealthCareDetails(HealthCareEntity objHealthCareEntity);
	public int deleteHealthCare(HealthCareEntity objHealthCareEntity);
	public List<HealthcareVaccLinkEntity> getHealthCareTypeDetails(
			HealthCareEntity objHealthCareEntity);
	public HealthcareVaccLinkEntity saveHealthCareTypeDetails(
			HealthcareVaccLinkEntity objHealthcareVaccLinkEntity);
	public List<HealthcareVaccLinkEntity> getHealthCareTypeDetailsById(int vaccId);
	public List<HashMap<String, Object>> getHealthCareDetailsByRole(HealthCareEntity objHealthCareEntity, String rolename, int userId);
}
