package com.inapp.cms.controller;

import java.sql.Timestamp;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.utils.Common;
import com.app.utils.DateDeserializer;
import com.app.utils.DateSerializer;
import com.app.utils.MessageConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inapp.cms.entity.CattleEntity;
import com.inapp.cms.entity.SurgeryIllnessDetailEntity;
import com.inapp.cms.entity.SurgeryIllnessEntity;
import com.inapp.cms.entity.VetEntity;
import com.inapp.cms.service.CattleManager;
import com.inapp.cms.service.OwnerUserManager;
import com.inapp.cms.service.SurgeryIllnessManager;
import com.inapp.cms.service.UserManager;

@Controller
@RequestMapping(value = "/surgery")
public class SurgeryIllnessController {
	
	private static final Logger logger = Logger.getLogger(SurgeryIllnessController.class);
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private SurgeryIllnessManager surgeryManager;
	
	@Autowired
	private OwnerUserManager ownerUserManager;
	
	@Autowired
	private CattleManager cattleManager;

	@RequestMapping(value = "/saveSurgeryIllness", method = RequestMethod.POST)
	public @ResponseBody
	String saveSurgeryIllness(@RequestBody String breedingDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
		Gson surgeryGson = gsonBuilder.create();
		Gson resGson = new Gson();
		try {
			map = userManager.getReqDetails(breedingDetails,MessageConstants.VET_PERMISSION);
			if(map.get("status").equals("1")){
			SurgeryIllnessEntity objSurgeryIllnessEntity = surgeryGson.fromJson(map.get("reqData"), SurgeryIllnessEntity.class);
			
			if(objSurgeryIllnessEntity.getVet().getVetid()==null || objSurgeryIllnessEntity.getVet().getVetid()==0)
			{
				List<Object> list= ownerUserManager.getUserDetails(Integer.parseInt(map.get("u_id").toString()), "VET");
				if(list.size()>0){
					objSurgeryIllnessEntity.setVet((VetEntity)list.get(0));
				}
			}
			
			CattleEntity objCattleEntity = new CattleEntity();
			objCattleEntity.setCattleid(objSurgeryIllnessEntity.getCattle().getCattleid());
			objCattleEntity = cattleManager.getCattleDetails(objCattleEntity);
			
			List<SurgeryIllnessDetailEntity> listDet = objSurgeryIllnessEntity.getListDetails();
			if(objSurgeryIllnessEntity.getSurgillid() != null && objSurgeryIllnessEntity.getSurgillid() >0){
				objSurgeryIllnessEntity.setUpdateddt(Common.getCurrentGMTTimestamp()); //for sync
				objSurgeryIllnessEntity.setUniquesynckey(objCattleEntity.getCattleeartagid()+"/"+objSurgeryIllnessEntity.getSurgillprocdt()); // for sync
			}else{
				objSurgeryIllnessEntity.setCreateddt(Common.getCurrentGMTTimestamp());
				objSurgeryIllnessEntity.setUniquesynckey(objCattleEntity.getCattleeartagid()+"/"+objSurgeryIllnessEntity.getSurgillprocdt()); // for sync
			}
			
			objSurgeryIllnessEntity = surgeryManager.saveSurgeryIllness(objSurgeryIllnessEntity);
			if(objSurgeryIllnessEntity.getSurgillid() != null && objSurgeryIllnessEntity.getSurgillid() >0){
				boolean resultFlag = false;
				if(listDet!=null){
					//for sync	//save data to be deleted to a temporary table for syncing purpose
					resultFlag = surgeryManager.saveSurgeryIllnessDetail(objSurgeryIllnessEntity.getListDetails(),objSurgeryIllnessEntity);
				
				if(resultFlag==true && objSurgeryIllnessEntity.getSurgillid()>0){
					HashMap<String, Object> retMap = new HashMap<String, Object>();
					retMap.put("dtls", objSurgeryIllnessEntity);
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData",resGson.toJson(objSurgeryIllnessEntity.getSurgillid()));
				}else{
					map.put("msg", MessageConstants.msg_3);
					map.put("status", "3");
				}
			}else{
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",resGson.toJson(objSurgeryIllnessEntity.getSurgillid()));
			}
			}
			map.remove("reqData");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in saveSurgeryIllness :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = resGson.toJson(map);
		return res.toString();
	}
	
	@RequestMapping(value = "/getSurgeryMaster", method = RequestMethod.POST)
	public @ResponseBody
	String getSurgeryMaster(@RequestBody String surgeryDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		Gson surgeryGson = new Gson();
		
		try {
			map = userManager.getReqDetails(surgeryDetails,MessageConstants.VET_PERMISSION);
			if(map.get("status").equals("1")){
				List<HashMap<String, Object>> surgeryMaster = surgeryManager.getSurgeryMaster();
			if(null != surgeryMaster && surgeryMaster.size() >0){
				
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData",surgeryGson.toJson(surgeryMaster));
				
			}else{
				map.put("msg", MessageConstants.msg_3);
				map.put("status", "3");
			}
			map.remove("reqData");
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getSurgeryMaster :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = surgeryGson.toJson(map);
		return res.toString();
	}
	@RequestMapping(value = "/getSurgeryIllness", method = RequestMethod.POST)
	public @ResponseBody
	String getSurgeryIllness(@RequestBody String surgeryDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
		Gson surgeryGson = gsonBuilder.create();
		Gson resGson = new Gson();
		try {
			map = userManager.getReqDetails(surgeryDetails,MessageConstants.VET_PERMISSION);
			if(map.get("status").equals("1")){
				SurgeryIllnessEntity objSurgeryIllnessEntity = surgeryGson.fromJson(map.get("reqData"), SurgeryIllnessEntity.class);
				List<SurgeryIllnessEntity> surgeryMasterList= surgeryManager.getSurgeryIllness(objSurgeryIllnessEntity,Integer.parseInt(map.get("u_id").toString()));
			if(null != surgeryMasterList && surgeryMasterList.size() >0){
				List<SurgeryIllnessDetailEntity> surgeryDetailList= surgeryManager.getSurgeryIllnessDetail(surgeryMasterList.get(0));
				    HashMap<String, Object> resultMap = new HashMap<String, Object>();
				    resultMap.put("surgeryIllness", surgeryMasterList);
				    if(surgeryDetailList!=null && surgeryDetailList.size()>0){
				    resultMap.put("surgeryTreatment", surgeryDetailList);
				    }
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData",resGson.toJson(resultMap));
				
			}else{
				map.put("msg", MessageConstants.msg_2);
				map.put("status", "2");
			}
			map.remove("reqData");
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getSurgeryIllness :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = surgeryGson.toJson(map);
		return res.toString();
	}
	
	
	@RequestMapping(value = "/getSurgeryIllnessMaster", method = RequestMethod.POST)
	public @ResponseBody
	String getSurgeryIllnessMaster(@RequestBody String surgeryDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";SurgeryIllnessEntity objSurgeryIllnessEntity = null;
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
		Gson surgeryGson = gsonBuilder.create();
		Gson resGson = new Gson();
		try {
			map = userManager.getReqDetails(surgeryDetails,MessageConstants.VET_PERMISSION);
			if(map.get("status").equals("1")){
				List<SurgeryIllnessEntity> surgeryMasterList= surgeryManager.getSurgeryIllness(objSurgeryIllnessEntity,Integer.parseInt(map.get("u_id").toString()));
			if(null != surgeryMasterList && surgeryMasterList.size() >0){
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData",resGson.toJson(surgeryMasterList));
				
			}else{
				map.put("msg", MessageConstants.msg_2);
				map.put("status", "2");
			}
			map.remove("reqData");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getSurgeryIllness :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = surgeryGson.toJson(map);
		return res.toString();
	}
	@RequestMapping(value = "/getSurgeryIllnessData", method = RequestMethod.POST)
	public @ResponseBody
	String getSurgeryIllnessData(@RequestBody String surgeryDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		Gson resGson = new Gson();
		try {
			map = userManager.getReqDetails(surgeryDetails,MessageConstants.OWNER_PERMISSION);
			if(map.get("status").equals("1")){
				List<SurgeryIllnessEntity> surgeryMasterList= surgeryManager.getSurgeryIllnessData(Integer.parseInt(map.get("u_id").toString()));
				if(null != surgeryMasterList && surgeryMasterList.size() >0){
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData",resGson.toJson(surgeryMasterList));
				}else{
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
			map.remove("reqData");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getSurgeryIllnessData :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = resGson.toJson(map);
		return res.toString();
	}

}
