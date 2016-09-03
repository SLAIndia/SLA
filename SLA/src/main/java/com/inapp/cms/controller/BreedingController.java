package com.inapp.cms.controller;

import java.sql.Date;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inapp.cms.entity.BreedKidsEntity;
import com.inapp.cms.entity.BreedingEntity;
import com.inapp.cms.entity.CattleEntity;
import com.inapp.cms.service.BreedingManager;
import com.inapp.cms.service.CattleManager;
import com.inapp.cms.service.UserManager;
import com.inapp.cms.utils.Common;
import com.inapp.cms.utils.DateDeserializer;
import com.inapp.cms.utils.DateSerializer;
import com.inapp.cms.utils.MessageConstants;

@Controller
@RequestMapping(value = "/breeding")
public class BreedingController {
	private static final Logger logger = Logger.getLogger(BreedingController.class);
	@Autowired
	private BreedingManager breedingManager;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private CattleManager cattleManager;
	

	@RequestMapping(value = "/saveBreeding", method = RequestMethod.POST)
	public @ResponseBody
	String saveBreeding(@RequestBody String breedingDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
		Gson breedingGson = gsonBuilder.create();
		try {
			map = userManager.getReqDetails(breedingDetails,MessageConstants.OWNER_PERMISSION);
			if(map.get("status").equals("1")){
			BreedingEntity objBreedingEntity = breedingGson.fromJson(map.get("reqData"), BreedingEntity.class);
			CattleEntity objDoeCattleEntity = new CattleEntity();
			CattleEntity objBuckCattleEntity = new CattleEntity();
			objDoeCattleEntity.setCattleid(objBreedingEntity.getBreedingdoeid());
			objBuckCattleEntity.setCattleid(objBreedingEntity.getBreedingbuckid());
			
			objDoeCattleEntity = cattleManager.getCattleDetails(objDoeCattleEntity);
			objBuckCattleEntity = cattleManager.getCattleDetails(objBuckCattleEntity);
			objBreedingEntity.setBreedingdoeeartag(objDoeCattleEntity.getCattleeartagid());
			objBreedingEntity.setBreedingbuckeartag(objBuckCattleEntity.getCattleeartagid());
			if(objBreedingEntity.getBreedingid()!=null && objBreedingEntity.getBreedingid()>0)
				{
				objBreedingEntity.setUpdateddt(Common.getCurrentGMTTimestamp());//for sync
				objBreedingEntity = breedingManager.updateParents(objBreedingEntity);
				}//for sync
			else
				{
				objBreedingEntity.setCreateddt(Common.getCurrentGMTTimestamp());
				objBreedingEntity.setUniquesynckey(objDoeCattleEntity.getCattleeartagid()+objBuckCattleEntity.getCattleeartagid()+"/"+new Date(objBreedingEntity.getBreedingdate().getTime()));
				}//for sync
			
			objBreedingEntity = breedingManager.saveBreeding(objBreedingEntity);
			if(objBreedingEntity.getBreedingid()!=null && objBreedingEntity.getBreedingid()>0){
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",breedingGson.toJson(objBreedingEntity));
			}else{
				map.put("msg", MessageConstants.msg_3);
				map.put("status", "3");
			}
			map.remove("reqData");
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in saveBreeding :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = breedingGson.toJson(map);
		return res.toString();
	}
	
	@RequestMapping(value = "/saveBreedKid", method = RequestMethod.POST)
	public @ResponseBody
	String saveBreedKid(@RequestBody String breedingDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
		Gson breedingGson = gsonBuilder.create();
		try {
			map = userManager.getReqDetails(breedingDetails,MessageConstants.ALL_PERMISSION);
			if(map.get("status").equals("1")){
			CattleEntity objCattleEntity = breedingGson.fromJson(map.get("reqData"), CattleEntity.class);
			BreedKidsEntity objBredKidsEntity = breedingGson.fromJson(map.get("reqData"), BreedKidsEntity.class);
			BreedingEntity objBreedingEntity = new BreedingEntity();
			objBreedingEntity.setBreedingid(objBredKidsEntity.getObjBreedingEntity().getBreedingid());//set breeding id from breedkid.id
			objBreedingEntity = breedingManager.getBreedingDetails(objBreedingEntity).get(0);
			objCattleEntity.setCreateddt(Common.getCurrentGMTTimestamp());//for sync
			HashMap<String, Object> cattleDetMap = cattleManager.saveCattleDetails(objCattleEntity);
			if(cattleDetMap != null && cattleDetMap.size() >0){
				
				objBredKidsEntity.setBreedkidscattleid(Integer.parseInt(cattleDetMap.get("cattle_id").toString()));
				objBreedingEntity.setUpdateddt(Common.getCurrentGMTTimestamp());//for sync
				if(objBredKidsEntity.getId()==null || objBredKidsEntity.getId()==0){
				objBreedingEntity.setBreedingkidsno(objBreedingEntity.getBreedingkidsno()+1);// Increment kid no by 1
				}
				objBreedingEntity = breedingManager.saveBreeding(objBreedingEntity);
				objBredKidsEntity.setObjBreedingEntity(objBreedingEntity);
				if(objBredKidsEntity.getId()!=null && objBredKidsEntity.getId()>0){
					objBredKidsEntity.setUpdateddt(Common.getCurrentGMTTimestamp());
				}else{
					objBredKidsEntity.setCreateddt(Common.getCurrentGMTTimestamp());//for sync
				}
				objBredKidsEntity.setUniquesynckey(cattleDetMap.get("cattle_ear_tag_id").toString());//for sync
				objBredKidsEntity = breedingManager.saveBreedKids(objBredKidsEntity);
				if(objBredKidsEntity!=null && objBredKidsEntity.getId()>0){
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData",breedingGson.toJson(objBreedingEntity));
				}else{
					map.put("msg", MessageConstants.msg_3);
					map.put("status", "3");
				}
			}
			
			map.remove("reqData");
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in saveBreedKid :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = breedingGson.toJson(map);
		return res.toString();
	}
	
	
	@RequestMapping(value = "/breedingDetails", method = RequestMethod.POST)
	public @ResponseBody
	String breedingDetails(@RequestBody String breedingDetails) {

		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		
		Gson breedingGson = new Gson();
		try {
			map = userManager.getReqDetails(breedingDetails,MessageConstants.ALL_PERMISSION);
			if(map.get("status").equals("1")){
			BreedingEntity objBreedingEntity = breedingGson.fromJson(map.get("reqData"), BreedingEntity.class);
			List<HashMap<String, Object>> listBreeding = breedingManager.getBreedingDetailsByAssignedFarmUser(objBreedingEntity,Integer.parseInt(map.get("u_id")),0);
			if(listBreeding !=null && listBreeding.size() >0){
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",breedingGson.toJson(listBreeding));
			}else{
				map.put("msg", MessageConstants.msg_2);
				map.put("status", "2");
			}
			map.remove("reqData");
			map.remove("u_id");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in breedingDetails :"+e.getMessage());
			map.put("msg",MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = breedingGson.toJson(map);
		return res.toString();
	}

	@RequestMapping(value = "/deleteBreeding", method = RequestMethod.POST)
	public @ResponseBody
	String deleteBreeding(@RequestBody String breedingDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson breedingGson = new Gson();
		try {
			map = userManager.getReqDetails(breedingDetails,MessageConstants.ALL_PERMISSION);
			if (map.get("status").equals("1")) {
				BreedingEntity objBreedingEntity = breedingGson.fromJson(map.get("reqData"), BreedingEntity.class);
				
				int status = breedingManager.deleteBreeding(objBreedingEntity);
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
			logger.error("error in deleteBreeding :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = breedingGson.toJson(map);
		return res.toString();
	}
	

	@RequestMapping(value = "/breedKidDetails", method = RequestMethod.POST)
	public @ResponseBody
	String breedKidDetails(@RequestBody String breedingDetails) {

		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		
		Gson breedingGson = new Gson();
		try {
			map = userManager.getReqDetails(breedingDetails,MessageConstants.ALL_PERMISSION);
			if(map.get("status").equals("1")){
			BreedingEntity objBreedingEntity = breedingGson.fromJson(map.get("reqData"), BreedingEntity.class);
			List<HashMap<String, Object>> listBreeding = breedingManager.getBreedKidDetails(objBreedingEntity);
			if(listBreeding != null && listBreeding.size() >0){
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",breedingGson.toJson(listBreeding));
			}else{
				map.put("msg", MessageConstants.msg_2);
				map.put("status", "2");
			}
			map.remove("reqData");
			map.remove("u_id");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in breedKidDetails :"+e.getMessage());
			map.put("msg",MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = breedingGson.toJson(map);
		return res.toString();
	}
	@RequestMapping(value = "/getBreedingDetails", method = RequestMethod.POST)
	public @ResponseBody
	String getBreedingDetails(@RequestBody String breedingDetails) {

		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		
		Gson breedingGsons = new Gson();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
		Gson breedingGson = gsonBuilder.create();
		try {
			map = userManager.getReqDetails(breedingDetails,MessageConstants.OWNER_PERMISSION);
			if(map.get("status").equals("1")){
			BreedingEntity objBreedingEntity = breedingGson.fromJson(map.get("reqData"), BreedingEntity.class);
			//List<HashMap<String, Object>> listBreeding = breedingManager.getBreedingDetailsByAssignedFarmUser(objBreedingEntity,Integer.parseInt(map.get("u_id")),0);
			List<HashMap<String, Object>> listBreeding = breedingManager.getBreedingDetailsofDate(objBreedingEntity,Integer.parseInt(map.get("u_id")),0);
			
			
			if(listBreeding != null && listBreeding.size() > 0){
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",breedingGsons.toJson(listBreeding));
			}else{
				map.put("msg", MessageConstants.msg_2);
				map.put("status", "2");
			}
			map.remove("reqData");
			map.remove("u_id");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in breedKidDetails :"+e.getMessage());
			map.put("msg",MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = breedingGson.toJson(map);
		return res.toString();
	}
}
