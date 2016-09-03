package com.inapp.cms.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.utils.ServiceConstants;
import com.inapp.cms.dao.FarmDAO;
import com.inapp.cms.entity.FarmEntity;

@Service(ServiceConstants.FARM_MANAGER)
public class FarmManagerImpl implements FarmManager{

	@Autowired
    private FarmDAO farmDAO;
	
	@Override
	public HashMap<String, Object> saveFarm(FarmEntity objFarmEntity) {
		return farmDAO.saveFarm(objFarmEntity);
	}

	@Override
	public List<HashMap<String, Object>> getFarmDetails(int usr_id) {
		return farmDAO.getFarmDetails(usr_id);
	}

	@Override
	public FarmEntity getFarm(FarmEntity objFarmEntity) {
		return farmDAO.getFarmData(objFarmEntity);
	}

	@Override
	public int deleteFarm(FarmEntity objFarmEntity) {
		return farmDAO.deleteFarm(objFarmEntity);
	}

	@Override
	public List<HashMap<String, Object>> getFarmDetailsByUser(int user_id, String roleName) {
		return farmDAO.getFarmDetailsByUser(user_id, roleName);
	}

	@Override
	public List<HashMap<String, Object>> getFarmDetailsByCodeorName(
			String codeName) {
		return farmDAO.getFarmDetailsByCodeorName(codeName);
	}

	@Override
	public List<HashMap<String, Object>> getAllFarmDetailsByUser(int user_id, String roleName) {
		return farmDAO.getAllFarmDetailsByUser(user_id, roleName);
	}
}
