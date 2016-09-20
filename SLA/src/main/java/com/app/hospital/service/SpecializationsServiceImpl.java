package com.app.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hospital.dao.SpecializationsDao;
import com.app.hospital.entity.SpecializationsEntity;
import com.app.utils.ServiceConstants;

@Service(ServiceConstants.SPECIALIZATIONS_SERVICE)
public class SpecializationsServiceImpl implements SpecializationsService {
	@Autowired
    private SpecializationsDao specializationsDao;
	
	@Override
	public SpecializationsEntity saveSpecializations(SpecializationsEntity objSpecializations) throws Exception {
		return specializationsDao.saveSpecializations(objSpecializations);
	}

	@Override
	public List<SpecializationsEntity> getSpecializations() {
		return specializationsDao.getSpecializations();
	}
	@Override
	public SpecializationsEntity getSpecialization(long pki_hos_dept_type_id) {
		return specializationsDao.getSpecialization(pki_hos_dept_type_id);
	}
	@Override
	public int deleteSpecialization(long pki_hos_dept_type_id) {
		return specializationsDao.deleteSpecialization(pki_hos_dept_type_id);
	}
}
