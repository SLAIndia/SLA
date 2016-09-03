package com.inapp.cms.dao;

import java.util.HashMap;
import java.util.List;

import com.inapp.cms.entity.FarmEntity;

public interface FarmDAO {

	public HashMap<String, Object> saveFarm(FarmEntity objFarmEntity);

	public List<HashMap<String, Object>> getFarmDetails(int usr_id);
	
	public FarmEntity getFarmData(FarmEntity objFarmEntity);

	public int deleteFarm(FarmEntity objFarmEntity);

	public List<HashMap<String, Object>> getFarmDetailsByCodeorName(String codeName);

	public List<HashMap<String, Object>> getFarmDetailsByUser(int user_id,
			String roleName);
	public List<HashMap<String, Object>> getAllFarmDetailsByUser(int user_id,
			String roleName);

}
