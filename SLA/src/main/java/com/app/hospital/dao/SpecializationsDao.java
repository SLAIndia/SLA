package com.app.hospital.dao;

import java.util.List;

import com.app.hospital.entity.SpecializationsEntity;

public interface SpecializationsDao {
	SpecializationsEntity saveSpecializations(SpecializationsEntity objSpecializations) throws Exception;

	List<SpecializationsEntity> getSpecializations();

	SpecializationsEntity getSpecialization(long pki_hos_dept_type_id);

	int deleteSpecialization(long pki_hos_dept_type_id);
}
