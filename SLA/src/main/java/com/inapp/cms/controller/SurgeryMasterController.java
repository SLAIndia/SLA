package com.inapp.cms.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.utils.Common;
import com.app.utils.DateDeserializer;
import com.app.utils.DateSerializer;
import com.app.utils.MessageConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inapp.cms.common.Constants;
import com.inapp.cms.common.Response;
import com.inapp.cms.entity.SurgeryMasterEntity;
import com.inapp.cms.service.SurgeryMasterManager;
import com.inapp.cms.service.UserManager;

@Controller
@RequestMapping(value ="/surgeryType")
public class SurgeryMasterController {

private static final Logger logger = Logger.getLogger(SurgeryMasterController.class);
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private SurgeryMasterManager surgeryTypeManager;
	
	@RequestMapping(value = "/saveSurgeryType", method = RequestMethod.POST)
	public @ResponseBody String saveSurgeryType(
			@RequestBody String surgeryDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		Gson surgeryGson = new Gson();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
		Gson surgeryGsonBuilder = gsonBuilder.create();

		try {
			map = userManager.getReqDetails(surgeryDetails,
					MessageConstants.VET_PERMISSION);
			if (map.get("status").equals("1")) {
				SurgeryMasterEntity surgerymasterEntity = surgeryGsonBuilder.fromJson(
						map.get("reqData"), SurgeryMasterEntity.class);
				surgerymasterEntity.setUniquesynckey(surgerymasterEntity.getSurgeryname());
				if(null != surgerymasterEntity.getSurgeryid()){
					surgerymasterEntity.setUpdateddt(Common.getCurrentGMTTimestamp());
				}else{
					surgerymasterEntity.setCreateddt(Common.getCurrentGMTTimestamp());
				}

				surgerymasterEntity = surgeryTypeManager.saveSurgeryType(surgerymasterEntity);
				if (null != surgerymasterEntity) {

					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData", surgeryGson.toJson(surgerymasterEntity));

				} else {
					map.put("msg", MessageConstants.msg_3);
					map.put("status", "3");
				}
				map.remove("reqData");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in saveSurgeryType :" + e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}
		resList.add(map);
		res = surgeryGson.toJson(map);
		return res.toString();
	}
	
	@RequestMapping(value = "/getSurgeryList", method = RequestMethod.POST)
	public @ResponseBody
	String getSurgeryType(@RequestBody String surgeryDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		Gson surgeryGson = new Gson();
		
		try {
			map = userManager.getReqDetails(surgeryDetails,MessageConstants.VET_PERMISSION);
			if (map.get("status").equals("1")) {
				SurgeryMasterEntity surgerymasterEntity = surgeryGson.fromJson(
						map.get("reqData"), SurgeryMasterEntity.class);
				List<SurgeryMasterEntity> surgeryMaster = surgeryTypeManager.getSurgeryList(surgerymasterEntity);
				if (null != surgeryMaster && surgeryMaster.size() > 0) {

					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData", surgeryGson.toJson(surgeryMaster));

				} else {
					map.put("msg", MessageConstants.msg_3);
					map.put("status", "3");
				}
				map.remove("reqData");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getSurgeryList :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = surgeryGson.toJson(map);
		return res.toString();
	}
	
	@RequestMapping(value="/checkSurgeryName", method = RequestMethod.POST)
	public @ResponseBody String checkSurgeryName(@RequestBody String surgeryDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		Gson surgeryGson = new Gson();
		
		try {
			map = userManager.getReqDetails(surgeryDetails,MessageConstants.VET_PERMISSION);
			if (map.get("status").equals("1")) {
				SurgeryMasterEntity surgerymasterEntity = surgeryGson.fromJson(
						map.get("reqData"), SurgeryMasterEntity.class);
				boolean isExist = surgeryTypeManager.isSurgeryNameExist(surgerymasterEntity);
				if (isExist) {
					map.put("msg", "Surgery Name already exist");
					map.put("status", "1");
					map.put("resData", "");

				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
				map.remove("reqData");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getSurgeryList :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = surgeryGson.toJson(map);
		return res.toString();
	}
}
