package com.app.hospital.service;

import java.util.List;

import com.app.hospital.entity.SpecializationsEntity;

public interface SpecializationsService {
	SpecializationsEntity saveSpecializations(SpecializationsEntity objSpecializations) throws Exception;

	List<SpecializationsEntity> getSpecializations();

	SpecializationsEntity getSpecialization(long pki_hos_dept_type_id);

	int deleteSpecialization(long pki_hos_dept_type_id);

	SpecializationsEntity getSpecializationByName(String vc_hos_dept_type_name);

}
