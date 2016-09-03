package com.inapp.cms.dao;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.inapp.cms.entity.SyncEntity;

public interface SyncDAO {

	public List<HashMap<String, Object>> getSyncDetails(SyncEntity objSyncEntity,List<Integer> farmIdList, List<Integer> newFarmIdList);
	public List<Integer> getFarmIdsAssignedByUserId(int userId, String role);
	public boolean saveClientDetails(JSONObject objJson);
	public String generateDesktopId(String status, int u_id);
	public List<HashMap<String, Object>> getImageDetails(
			SyncEntity objSyncEntity, List<Integer> farmIdList, List<Integer> newFarmIdList);
	List<Integer> getfarmsByUserId(int userId, String role,
			int existOrNotExist, List<String> farms);
}