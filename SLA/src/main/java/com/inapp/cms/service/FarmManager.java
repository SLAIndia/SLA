package com.inapp.cms.service;

import java.util.HashMap;
import java.util.List;

import com.inapp.cms.entity.FarmEntity;

public interface FarmManager {

	HashMap<String, Object> saveFarm(FarmEntity objFarmEntity);

	List<HashMap<String, Object>> getFarmDetails(int usr_id);

	FarmEntity getFarm(FarmEntity objFarmEntity);

	int deleteFarm(FarmEntity objFarmEntity);

	List<HashMap<String, Object>> getFarmDetailsByUser(int user_id, String roleName);
	
	List<HashMap<String, Object>> getAllFarmDetailsByUser(int user_id, String roleName);

	List<HashMap<String, Object>> getFarmDetailsByCodeorName(String codeName);
}
