package com.inapp.cms.dao;

import java.util.List;

import com.inapp.cms.entity.VaccinationEntity;

public interface VaccinationDAO {

	public VaccinationEntity saveVaccination(VaccinationEntity objVaccinationEntity);
	public List<VaccinationEntity> getVaccinationDetails(VaccinationEntity objVaccinationEntity);
	public int deleteVaccination(VaccinationEntity objVaccinationEntity);
}
