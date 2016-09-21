package com.app.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hospital.dao.QualificationsDao;
import com.app.hospital.entity.QualificationsEntity;
import com.app.utils.ServiceConstants;

@Service(ServiceConstants.QUALIFICATIONS_SERVICE)
public class QualificationsServiceImpl implements QualificationsService {
	@Autowired
    private QualificationsDao qualificationsDao;
	
	@Override
	public QualificationsEntity saveQualifications(QualificationsEntity objQualifications) throws Exception {
		return qualificationsDao.saveQualifications(objQualifications);
	}

	@Override
	public List<QualificationsEntity> getQualifications() {
		return qualificationsDao.getQualifications();
	}
	@Override
	public QualificationsEntity getQualification(long pki_doctor_qualif_master_id) {
		return qualificationsDao.getQualification(pki_doctor_qualif_master_id);
	}
	@Override
	public int deleteQualification(long pki_doctor_qualif_master_id) {
		return qualificationsDao.deleteQualification(pki_doctor_qualif_master_id);
	}

	@Override
	public QualificationsEntity getQualificationByName(String uvc_qualif_name) {
		return qualificationsDao.getQualificationByName(uvc_qualif_name);
	}
}
