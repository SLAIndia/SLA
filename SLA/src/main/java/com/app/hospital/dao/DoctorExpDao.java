package com.app.hospital.dao;

import java.util.HashMap;
import java.util.List;

import com.app.hospital.entity.DoctorQualLinkEntity;

public interface DoctorExpDao {
	DoctorQualLinkEntity saveDoctorQualLink(DoctorQualLinkEntity objDoctorQualLink) throws Exception;
	List<HashMap<String, Object>>  getDoctorQualLink(long pki_doctor_id,String uvc_qualif_name);
	int deleteDoctorQualLink(long pki_doctor_qualif_link_id);
	DoctorQualLinkEntity getDoctorQualLinkByIds(Long qulaifId, Integer doctorId);
}
