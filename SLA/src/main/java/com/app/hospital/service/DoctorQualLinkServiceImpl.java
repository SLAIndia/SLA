package com.app.hospital.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hospital.dao.DoctorQualLinkDao;
import com.app.hospital.entity.DoctorQualLinkEntity;
import com.app.utils.ServiceConstants;

@Service(ServiceConstants.DOCTOR_QUAL_LINK_SERVICE)
public class DoctorQualLinkServiceImpl implements DoctorQualLinkService {
	@Autowired
    private DoctorQualLinkDao doctorQualLinkDao;

	@Override
	public DoctorQualLinkEntity saveDoctorQualLink(DoctorQualLinkEntity objDoctorQualLink) throws Exception {
		return doctorQualLinkDao.saveDoctorQualLink(objDoctorQualLink);
	}
	@Override
	public List<HashMap<String, Object>> getDoctorQualLink(long pki_doctor_id,String uvc_qualif_name) {
		return doctorQualLinkDao.getDoctorQualLink(pki_doctor_id,uvc_qualif_name);
	}
	@Override
	public int deleteDoctorQualLink(long pki_doctor_qualif_link_id) {
		return doctorQualLinkDao.deleteDoctorQualLink(pki_doctor_qualif_link_id);
	}
	@Override
	public DoctorQualLinkEntity getDoctorQualLinkByIds(Long qulaifId, Integer doctorId){
			return doctorQualLinkDao.getDoctorQualLinkByIds(qulaifId,doctorId);
			}
}
