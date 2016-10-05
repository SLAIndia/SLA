package com.app.hospital.service;

import java.util.HashMap;
import java.util.List;

import com.app.hospital.entity.DoctorQualLinkEntity;

public interface DoctorQualLinkService {
	DoctorQualLinkEntity saveDoctorQualLink(DoctorQualLinkEntity objDoctorQualLink) throws Exception;

	 List<HashMap<String, Object>> getDoctorQualLink(long pki_doctor_id,String uvc_qualif_name);

	//DoctorQualLinkEntity getDoctorQualLink(long pki_doctor_qualif_master_id);

	int deleteDoctorQualLink(long pki_doctor_qualif_master_id);

	DoctorQualLinkEntity getDoctorQualLinkByName(String uvc_qualif_name);

}
