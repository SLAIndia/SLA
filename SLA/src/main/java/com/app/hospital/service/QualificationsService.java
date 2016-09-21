package com.app.hospital.service;

import java.util.List;

import com.app.hospital.entity.QualificationsEntity;

public interface QualificationsService {
	QualificationsEntity saveQualifications(QualificationsEntity objQualifications) throws Exception;

	List<QualificationsEntity> getQualifications();

	QualificationsEntity getQualification(long pki_doctor_qualif_master_id);

	int deleteQualification(long pki_doctor_qualif_master_id);

	QualificationsEntity getQualificationByName(String uvc_qualif_name);

}
