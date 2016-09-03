package com.inapp.cms.dao;

import java.util.List;

import com.inapp.cms.entity.SurgeryMasterEntity;

public interface SurgeryMasterDAO {

	SurgeryMasterEntity saveSurgeryType(SurgeryMasterEntity surgerymasterEntity);

	List<SurgeryMasterEntity> getSurgeryList(
			SurgeryMasterEntity surgerymasterEntity);

	boolean isSurgeryNameExist(SurgeryMasterEntity surgerymasterEntity);

}
