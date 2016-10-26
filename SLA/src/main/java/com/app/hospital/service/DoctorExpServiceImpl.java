package com.app.hospital.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hospital.dao.DoctorExpDao;
import com.app.hospital.entity.DoctorExpEntity;
import com.app.utils.ServiceConstants;

@Service(ServiceConstants.DOCTOR_EXP_LINK_SERVICE)
public class DoctorExpServiceImpl implements DoctorExpService {
	@Autowired
    private DoctorExpDao doctorExpDao;

	@Override
	public DoctorExpEntity saveDoctorExp(DoctorExpEntity objDoctorExp) throws Exception {
		return doctorExpDao.saveDoctorExp(objDoctorExp);
	}
	@Override
	public List<HashMap<String, Object>> getDoctorExp(long pki_doctor_id,String uvc_qualif_name) {
		return doctorExpDao.getDoctorExp(pki_doctor_id,uvc_qualif_name);
	}
	@Override
	public int deleteDoctorExp(long pki_doctor_qualif_link_id) {
		return doctorExpDao.deleteDoctorExp(pki_doctor_qualif_link_id);
	}
	@Override
	public DoctorExpEntity getDoctorExpByIds(Long qulaifId, Integer doctorId){
			return doctorExpDao.getDoctorExpByIds(qulaifId,doctorId);
			}
}
