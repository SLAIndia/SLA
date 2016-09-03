package com.inapp.cms.service;

import java.util.List;

import com.inapp.cms.entity.VaccinationEntity;

public interface VaccinationManager {

	VaccinationEntity saveVaccination(VaccinationEntity objVaccinationEntity);
	List<VaccinationEntity> getVaccinationDetails(VaccinationEntity objVaccinationEntity);
	int deleteVaccination(VaccinationEntity objVaccinationEntity);
}
