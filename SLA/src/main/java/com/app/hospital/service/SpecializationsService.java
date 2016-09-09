package com.app.hospital.service;

import java.util.List;

import com.app.hospital.entity.SpecializationsEntity;

public interface SpecializationsService {
	SpecializationsEntity saveSpecializations(SpecializationsEntity objSpecializations) throws Exception;

	List<SpecializationsEntity> getSpecializations();

	SpecializationsEntity getSpecialization(SpecializationsEntity objSpecializationsEntity);

	int deleteSpecialization(SpecializationsEntity objSpecializationsEntity);
}
