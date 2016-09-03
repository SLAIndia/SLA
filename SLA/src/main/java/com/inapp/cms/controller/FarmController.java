package com.inapp.cms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.utils.Common;
import com.app.utils.MessageConstants;
import com.google.gson.Gson;
import com.inapp.cms.entity.FarmEntity;
import com.inapp.cms.entity.UserEntity;
import com.inapp.cms.service.FarmManager;
import com.inapp.cms.service.UserManager;

@Controller
@RequestMapping(value = "/farm")
public class FarmController {
	private static final Logger logger = Logger.getLogger(FarmController.class);
	
	@Autowired
	private FarmManager farmManager;
	
	@Autowired
	private UserManager userManager;
	

	@RequestMapping(value = "/saveFarm", method = RequestMethod.POST)
	public @ResponseBody
	String saveFarm(@RequestBody String farmDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson farmGson = new Gson();
		try {
			map = userManager.getReqDetails(farmDetails,MessageConstants.ADMIN_PERMISSION);
			if(map.get("status").equals("1")){
			FarmEntity objFarmEntity = farmGson.fromJson(map.get("reqData"), FarmEntity.class);
			objFarmEntity.setCreateddt(Common.getCurrentGMTTimestamp()); // for sync
			HashMap<String, Object> farmDetMap = farmManager.saveFarm(objFarmEntity);
			if(farmDetMap != null && farmDetMap.size() >0){
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",farmGson.toJson(farmDetMap));
				
				
			}else{
				map.put("msg", MessageConstants.msg_3);
				map.put("status", "3");
			}
			map.remove("reqData");
			}
		
			
		} catch (Exception e) {
			logger.error("error in saveFarm :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = farmGson.toJson(map);
		return res.toString();
	}
	
	@RequestMapping(value = "/farmDetails", method = RequestMethod.POST)
	public @ResponseBody
	String farmDetails(@RequestParam("signature") String signature,
			@RequestParam("u_id") String u_id,
			@RequestParam("reqData") String reqData,
			@RequestBody String farmDetails) {

		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson farmGson = new Gson();
		try {
			map = userManager.getReqDetails(signature, u_id, reqData,MessageConstants.ALL_PERMISSION);
			if(map.get("status").equals("1")){
				int usr_id = Integer.parseInt(map.get("u_id"));
			List<HashMap<String, Object>> farmDetMap = farmManager.getFarmDetails(usr_id);

			if(farmDetMap != null && farmDetMap.size() >0){
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",farmGson.toJson(farmDetMap));
				
				
			}else{
				map.put("msg", MessageConstants.msg_2);
				map.put("status", "2");
			}
			map.remove("reqData");
			map.remove("u_id");
			}
		
			
		} catch (Exception e) {
			logger.error("error in farmDetails :"+e.getMessage());
			map.put("msg",MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = farmGson.toJson(map);
		return res.toString();
	}
	@RequestMapping(value = "/farmData", method = RequestMethod.POST)
	public @ResponseBody
	String farmData(@RequestBody String farmDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson farmGson = new Gson();
		try {
			map = userManager.getReqDetails(farmDetails,MessageConstants.ALL_PERMISSION);
			if (map.get("status").equals("1")) {
				FarmEntity objFarmEntity = farmGson.fromJson(map.get("reqData"), FarmEntity.class);
				
				objFarmEntity = farmManager.getFarm(objFarmEntity);
				if (objFarmEntity != null ) {
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData", farmGson.toJson(objFarmEntity));

				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
				map.remove("reqData");
				map.remove("u_id");
			}
			
		} catch (Exception e) {
			logger.error("error in getFarmData :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = farmGson.toJson(map);
		return res.toString();
	}
	
	//not using
	@RequestMapping(value = "/deleteFarm", method = RequestMethod.POST)
	public @ResponseBody
	String deleteFarm(@RequestBody String farmDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson farmGson = new Gson();
		try {
			map = userManager.getReqDetails(farmDetails,MessageConstants.ADMIN_PERMISSION);
			if (map.get("status").equals("1")) {
				FarmEntity objFarmEntity = farmGson.fromJson(map.get("reqData"), FarmEntity.class);
				
				int status = farmManager.deleteFarm(objFarmEntity);
				if (status > 0) {
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");

				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
				map.remove("reqData");
				map.remove("u_id");
			}
			
		} catch (Exception e) {
			logger.error("error in getFarmData :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = farmGson.toJson(map);
		return res.toString();
	}
	
	@RequestMapping(value = "/getFarmDetailsByUser", method = RequestMethod.POST)
	public @ResponseBody
	String getFarmDetailsByUser(@RequestBody String farmDetails) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson farmGson = new Gson();String roleName = "";
		try {
			map = userManager.getReqDetails(farmDetails, MessageConstants.ALL_PERMISSION);
			if (map.get("status").equals("1")) {
				int user_id = Integer.parseInt(map.get("u_id")+"");
				UserEntity objEntity = new UserEntity();
				objEntity.setId(user_id);
				List<UserEntity> objUserList = userManager.getUser(objEntity);
				roleName = objUserList.get(0).getObjRoleEntity().getRolename();
				List<HashMap<String, Object>> farmDetMap  = farmManager.getFarmDetailsByUser(user_id, roleName);
				if(farmDetMap != null && farmDetMap.size() >0){
					HashMap<String, Object> objmap = new HashMap<String, Object>();
					objmap.put("farm_dtls", (farmDetMap));
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData", farmGson.toJson(objmap));

				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
				map.remove("reqData");
				map.remove("u_id");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getFarmDetailsByUser :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}
		res = farmGson.toJson(map);
		return res.toString();
	}
	
	@RequestMapping(value = "/getallFarmDetailsByUser", method = RequestMethod.POST)
	public @ResponseBody
	String getallFarmDetailsByUser(@RequestBody String farmDetails) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson farmGson = new Gson();String roleName = "";
		try {
			map = userManager.getReqDetails(farmDetails,MessageConstants.ALL_PERMISSION);
			if (map.get("status").equals("1")) {
				int user_id = Integer.parseInt(map.get("u_id"));
				UserEntity objEntity = new UserEntity();
				objEntity.setId(user_id);
				List<UserEntity> objUserList = userManager.getUser(objEntity);
				roleName = objUserList.get(0).getObjRoleEntity().getRolename();
				List<HashMap<String, Object>> farmDetMap  = farmManager.getAllFarmDetailsByUser(user_id, roleName);
				if(farmDetMap != null && farmDetMap.size() >0){
					HashMap<String, Object> objmap = new HashMap<String, Object>();
					objmap.put("farm_dtls", (farmDetMap));
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData", farmGson.toJson(objmap));

				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
				map.remove("reqData");
				map.remove("u_id");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getFarmDetailsByUser :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}
		res = farmGson.toJson(map);
		return res.toString();
	}
	
	@RequestMapping(value = "/getFarmsByCodeAndName", method = RequestMethod.POST)
	public @ResponseBody
	String getFarmsByCodeAndName(@RequestBody String farmDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson farmGson = new Gson();
		System.out.println("Farm details"+farmDetails);
		try {
			map = userManager.getReqDetails(farmDetails,MessageConstants.ALL_PERMISSION);
			if (map.get("status").equals("1")) {
				FarmEntity objFarmEntity = farmGson.fromJson(map.get("reqData"), FarmEntity.class);
				List<HashMap<String, Object>> farmList= farmManager.getFarmDetailsByCodeorName(objFarmEntity.getFarmcode().toString());
				if (farmList.size()>0) {
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData", farmGson.toJson(farmList));
				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
				map.remove("reqData");
				map.remove("u_id");
			}
			
		} catch (Exception e) {
			logger.error("error in getFarmData :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = farmGson.toJson(map);
		return res.toString();
	}
}
