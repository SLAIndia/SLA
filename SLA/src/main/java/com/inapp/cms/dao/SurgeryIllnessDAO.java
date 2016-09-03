package com.inapp.cms.dao;

import java.util.HashMap;
import java.util.List;

import com.inapp.cms.entity.SurgeryIllnessDetailEntity;
import com.inapp.cms.entity.SurgeryIllnessEntity;

public interface SurgeryIllnessDAO {

	List<HashMap<String, Object>> getSurgeryMaster();

	List<SurgeryIllnessEntity> getSurgeryIllness(
			SurgeryIllnessEntity objSurgeryIllnessEntity,int uId);

	List<SurgeryIllnessDetailEntity> getSurgeryIllnessDetail(
			SurgeryIllnessEntity objSurgeryIllnessEntity);
	SurgeryIllnessEntity saveSurgeryIllness(
			SurgeryIllnessEntity objSurgeryIllnessEntity);

	boolean saveSurgeryIllnessDetail(List<SurgeryIllnessDetailEntity> listDetail,SurgeryIllnessEntity objSurgeryIllnessEntity);

	List<SurgeryIllnessEntity> getSurgeryIllnessData(int uId);

}
