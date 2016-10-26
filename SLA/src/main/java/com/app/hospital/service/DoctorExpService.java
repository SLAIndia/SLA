package com.app.hospital.service;

import java.util.HashMap;
import java.util.List;

import com.app.hospital.entity.DoctorExpEntity;

public interface DoctorExpService {
	DoctorExpEntity saveDoctorExp(DoctorExpEntity objDoctorExp) throws Exception;
	List<HashMap<String, Object>> getDoctorExp(long pki_doctor_id,String uvc_qualif_name);
	int deleteDoctorExp(long pki_doctor_qualif_master_id);
	DoctorExpEntity getDoctorExpByIds(Long pki_doctor_qualif_master_id, Integer id);

}
