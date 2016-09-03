package com.inapp.cms.service;

import java.util.List;

import com.inapp.cms.entity.SurgeryMasterEntity;

public interface SurgeryMasterManager {

	SurgeryMasterEntity saveSurgeryType(SurgeryMasterEntity surgerymasterEntity);

	List<SurgeryMasterEntity> getSurgeryList(
			SurgeryMasterEntity surgerymasterEntity);

	boolean isSurgeryNameExist(SurgeryMasterEntity surgerymasterEntity);

}
