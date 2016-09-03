package com.inapp.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inapp.cms.dao.SurgeryMasterDAO;
import com.inapp.cms.entity.SurgeryMasterEntity;
import com.inapp.cms.utils.ServiceConstants;

@Service(ServiceConstants.SUEGERYTYPE_MANAGER)
public class SurgeryMasterManagerImpl implements SurgeryMasterManager{

	@Autowired SurgeryMasterDAO surgeryTypeDAO;

	@Override
	public SurgeryMasterEntity saveSurgeryType(
			SurgeryMasterEntity surgerymasterEntity) {
		return surgeryTypeDAO.saveSurgeryType(surgerymasterEntity);
	}

	@Override
	public List<SurgeryMasterEntity> getSurgeryList(
			SurgeryMasterEntity surgerymasterEntity) {
		return surgeryTypeDAO.getSurgeryList(surgerymasterEntity);
	}

	@Override
	public boolean isSurgeryNameExist(SurgeryMasterEntity surgerymasterEntity) {
		return surgeryTypeDAO.isSurgeryNameExist(surgerymasterEntity);
	}
	
	
}
