package com.inapp.cms.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.utils.Common;
import com.app.utils.MessageConstants;
import com.google.gson.Gson;
import com.inapp.cms.entity.SyncEntity;
import com.inapp.cms.entity.UserEntity;
import com.inapp.cms.service.OwnerUserManager;
import com.inapp.cms.service.SyncManager;
import com.inapp.cms.service.UserManager;

@Controller
@RequestMapping(value = "/sync")
public class SyncController {
	private static final Logger logger = Logger.getLogger(SyncController.class);
	@Autowired
	private SyncManager syncManager;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private OwnerUserManager ownerUserManager;
	
	
	@RequestMapping(value = "/getDetails", method = RequestMethod.POST)
	public @ResponseBody
	String syncDetails(@RequestBody String syncDetails) {

		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();List<Integer> newFarmIdList = null;
		String res = "";
		Gson syncGson = new Gson();
		syncDetails = syncDetails.replace("\\", "");
		
		try {
			map = userManager.getReqDetails(syncDetails,MessageConstants.ALL_PERMISSION);
			if(map.get("status").equals("1")){
			SyncEntity objSyncEntity = syncGson.fromJson(map.get("reqData"), SyncEntity.class);
			//List<Integer> farmIdList = syncManager.getFarmIdsAssignedByUserId(Integer.parseInt(map.get("u_id")), "OWNER");
			List<Integer> farmIdList = syncManager.getfarmsByUserId(Integer.parseInt(map.get("u_id")), "OWNER", 1, objSyncEntity.getFarms());
			if(objSyncEntity.getFarms()!=null && objSyncEntity.getFarms().size()>0){
				newFarmIdList = syncManager.getfarmsByUserId(Integer.parseInt(map.get("u_id")), "OWNER", 0, objSyncEntity.getFarms());
			}
			objSyncEntity.setUserId(Integer.parseInt(map.get("u_id")));
			UserEntity objUserEntity = new UserEntity();
			objUserEntity.setId(Integer.parseInt(map.get("u_id")));
			List<UserEntity> objList = userManager.getUser(objUserEntity);
			objSyncEntity.setUsername(objList.get(0).getUsername());
			List<HashMap<String, Object>> listSync = syncManager.getSyncDetails(objSyncEntity,farmIdList,newFarmIdList);
			if(listSync!=null && listSync.size() >0){
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",syncGson.toJson(listSync.get(0)));
			}else{
				map.put("msg", MessageConstants.msg_2);
				map.put("status", "2");
			}
			map.remove("reqData");
			map.remove("u_id");
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in syncDetails :"+e.getMessage());
			map.put("msg",MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = syncGson.toJson(map);
		return res.toString();
	}
	
	@RequestMapping(value = "/saveClientData", method = RequestMethod.POST)
	public @ResponseBody
	String saveClientData(@RequestBody String syncDetails) {

		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		Gson syncGson = new Gson();org.json.JSONObject objJson = null;
		//syncDetails = syncDetails.replace("\\", "");
		boolean savedStatus = false;
		try {
			map = userManager.getReqDetails(syncDetails,MessageConstants.ALL_PERMISSION);
			if(map.get("status").equals("1")){
				
				try {
				    objJson = new JSONObject(map.get("reqData"));
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			savedStatus = syncManager.saveClientDetails(objJson);
			Timestamp syncDt = Common.getCurrentGMTTimestamp();
			if(savedStatus==true){
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",syncDt+"".trim());
			}else{
				map.put("msg", MessageConstants.msg_2);
				map.put("status", "2");
				map.put("resData",syncDt+"".trim());
			}
			map.remove("reqData");
			map.remove("u_id");
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in syncDetails :"+e.getMessage());
			map.put("msg",MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = syncGson.toJson(map);
		return res.toString();
	}

		
	@RequestMapping(value = "/generateDesktopId", method = RequestMethod.POST)
	public @ResponseBody String generateEarTag(@RequestBody String cattleDetails) {
		
		logger.info("====generateDesktopId=====");
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		Gson farmGson = new Gson();
		try {
			
			map = userManager.getReqDetails(cattleDetails,MessageConstants.OWNER_PERMISSION);
			if (map.get("status").equals("1")) {
				String reqData = map.get("reqData");
				JSONObject obj = new JSONObject(reqData);
				String status = obj.getString("status");
				String earTag = syncManager.generateDesktopId(status,Integer.parseInt(map.get("u_id")));
				if(earTag != null && !earTag.equals("")){
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData", earTag);
				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
				map.remove("reqData");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in generateEarTag :" + e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}

		res = farmGson.toJson(map);
		return res.toString();
	}
	
	@RequestMapping(value = "/getImageDetails", method = RequestMethod.POST)
	public @ResponseBody
	String getImageDetails(@RequestBody String syncDetails) {

		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();List<Integer> newFarmIdList = null;
		String res = "";
		Gson syncGson = new Gson();
		syncDetails = syncDetails.replace("\\", "");
		
		try {
			map = userManager.getReqDetails(syncDetails,MessageConstants.ALL_PERMISSION);
			if(map.get("status").equals("1")){
			SyncEntity objSyncEntity = syncGson.fromJson(map.get("reqData"), SyncEntity.class);
			//List<Integer> farmIdList = syncManager.getFarmIdsAssignedByUserId(Integer.parseInt(map.get("u_id")), "OWNER");
			
			List<Integer> farmIdList = syncManager.getfarmsByUserId(Integer.parseInt(map.get("u_id")), "OWNER", 1, objSyncEntity.getFarms());
			if(objSyncEntity.getFarms()!=null && objSyncEntity.getFarms().size()>0){
				newFarmIdList = syncManager.getfarmsByUserId(Integer.parseInt(map.get("u_id")), "OWNER", 0, objSyncEntity.getFarms());
			}
			
			
			objSyncEntity.setUserId(Integer.parseInt(map.get("u_id")));
			List<HashMap<String, Object>> listSync = syncManager.getImageDetails(objSyncEntity,farmIdList,newFarmIdList);
			if(listSync!=null && listSync.size() >0){
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",syncGson.toJson(listSync));
			}else{
				map.put("msg", MessageConstants.msg_2);
				map.put("status", "2");
			}
			map.remove("reqData");
			map.remove("u_id");
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getImageDetails :"+e.getMessage());
			map.put("msg",MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = syncGson.toJson(map);
		return res.toString();
	}
	@RequestMapping(value = "/getServerGMT", method = RequestMethod.POST)
	public @ResponseBody
	String getServerGMT(@RequestBody String details) {

		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		Gson syncGson = new Gson();
		try {
			
			Timestamp syncDt = Common.getCurrentGMTTimestamp();
			logger.info("syncDt=getServerGMT =="+syncDt+"".trim());
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",syncDt+"".trim());
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getServerGMT :"+e.getMessage());
			map.put("msg",MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = syncGson.toJson(map);
		return res.toString();
	}

	
}