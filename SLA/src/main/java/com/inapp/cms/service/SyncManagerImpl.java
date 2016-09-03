package com.inapp.cms.service;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inapp.cms.dao.SyncDAO;
import com.inapp.cms.entity.SyncEntity;
import com.inapp.cms.utils.ServiceConstants;

@Service(ServiceConstants.SYNC_MANAGER)
public class SyncManagerImpl implements SyncManager{

	@Autowired
    private SyncDAO syncDAO;
	
	@Override
	public List<HashMap<String, Object>> getSyncDetails(SyncEntity objSyncEntity,List<Integer> farmIdList, List<Integer> newFarmIdList) {
		return syncDAO.getSyncDetails(objSyncEntity,farmIdList,newFarmIdList);
	}
	public List<Integer> getFarmIdsAssignedByUserId(int userId, String role){
		return syncDAO.getFarmIdsAssignedByUserId(userId, role);
	}
	@Override
	public List<Integer>getfarmsByUserId(int userId,String role,int existOrNotExist, List<String> farms){
		return syncDAO.getfarmsByUserId(userId, role, existOrNotExist, farms);
	}
	@Override
	public boolean saveClientDetails(JSONObject objJson) {
		return syncDAO.saveClientDetails(objJson);
	}
	@Override
	public String generateDesktopId(String status, int u_id) {
		return syncDAO.generateDesktopId(status, u_id);
	}
	@Override
	public List<HashMap<String, Object>> getImageDetails(
			SyncEntity objSyncEntity, List<Integer> farmIdList,  List<Integer> newFarmIdList) {
		return syncDAO.getImageDetails(objSyncEntity,farmIdList,newFarmIdList);
	}
}
