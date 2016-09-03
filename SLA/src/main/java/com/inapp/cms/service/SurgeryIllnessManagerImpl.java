package com.inapp.cms.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.utils.ServiceConstants;
import com.inapp.cms.dao.SurgeryIllnessDAO;
import com.inapp.cms.entity.SurgeryIllnessDetailEntity;
import com.inapp.cms.entity.SurgeryIllnessEntity;

@Service(ServiceConstants.SURGERY_MANAGER)
public class SurgeryIllnessManagerImpl implements SurgeryIllnessManager{
	
	@Autowired SurgeryIllnessDAO surgeryDAO;

	@Override
	public List<HashMap<String, Object>> getSurgeryMaster() {
		return surgeryDAO.getSurgeryMaster();
	}

	@Override
	public List<SurgeryIllnessEntity> getSurgeryIllness(SurgeryIllnessEntity objSurgeryIllnessEntity,int uId) {
		return surgeryDAO.getSurgeryIllness(objSurgeryIllnessEntity,uId);
	}

	@Override
	public List<SurgeryIllnessDetailEntity> getSurgeryIllnessDetail(
			SurgeryIllnessEntity objSurgeryIllnessEntity) {
		return surgeryDAO.getSurgeryIllnessDetail(objSurgeryIllnessEntity);
	}

	@Override
	public SurgeryIllnessEntity saveSurgeryIllness(
			SurgeryIllnessEntity objSurgeryIllnessEntity) {
		return surgeryDAO.saveSurgeryIllness(objSurgeryIllnessEntity);
	}

	@Override
	public boolean saveSurgeryIllnessDetail(
			List<SurgeryIllnessDetailEntity> listDetails, SurgeryIllnessEntity objSurgeryIllnessEntity) {
		return surgeryDAO.saveSurgeryIllnessDetail(listDetails, objSurgeryIllnessEntity);
	}

	@Override
	public List<SurgeryIllnessEntity> getSurgeryIllnessData(int uId) {
		return surgeryDAO.getSurgeryIllnessData(uId);
	}
	

}
