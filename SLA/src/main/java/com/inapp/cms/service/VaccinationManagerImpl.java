package com.inapp.cms.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inapp.cms.dao.VaccinationDAO;
import com.inapp.cms.entity.VaccinationEntity;
import com.inapp.cms.utils.ServiceConstants;

@Service(ServiceConstants.VACCINATION_MANAGER)
public class VaccinationManagerImpl implements VaccinationManager{

	@Autowired
    private VaccinationDAO vaccinationDAO;
	
	@Override
	public VaccinationEntity saveVaccination(VaccinationEntity objVaccinationEntity) {
		return vaccinationDAO.saveVaccination(objVaccinationEntity);
	}

	@Override
	public List<VaccinationEntity> getVaccinationDetails(VaccinationEntity objVaccinationEntity) {
		return vaccinationDAO.getVaccinationDetails(objVaccinationEntity);
	}

	@Override
	public int deleteVaccination(VaccinationEntity objVaccinationEntity) {
		return vaccinationDAO.deleteVaccination(objVaccinationEntity);
	}

}
