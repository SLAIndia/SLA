package com.inapp.cms.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.inapp.cms.entity.CattleEntity;
import com.inapp.cms.entity.MilkProductionEntity;
import com.inapp.cms.service.CattleManager;
import com.inapp.cms.service.MilkProductionManager;
import com.inapp.cms.service.UserManager;
import com.inapp.cms.utils.Common;
import com.inapp.cms.utils.DateDeserializer;
import com.inapp.cms.utils.DateSerializer;
import com.inapp.cms.utils.MessageConstants;

@Controller
@RequestMapping(value = "/milkproduction")
public class MilkProductionController {
	private static final Logger logger = Logger.getLogger(MilkProductionController.class);
	@Autowired
	private MilkProductionManager milkproductionManager;
	
	@Autowired
	private UserManager userManager;
	@Autowired
	private CattleManager cattleManager;

	@RequestMapping(value = "/saveMilkProduction", method = RequestMethod.POST)
	public @ResponseBody
	String saveMilkProduction(@RequestBody String milkproductionDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
		Gson milkproductionGson = gsonBuilder.create();
		try {
			map = userManager.getReqDetails(milkproductionDetails,MessageConstants.ALL_PERMISSION);
			if(map.get("status").equals("1")){
			MilkProductionEntity objMilkProductionEntity = milkproductionGson.fromJson(map.get("reqData"), MilkProductionEntity.class);
			
			CattleEntity objCattleEntity = new CattleEntity();
			objCattleEntity.setCattleid(objMilkProductionEntity.getObjdoe().getCattleid());
			objCattleEntity = cattleManager.getCattleDetails(objCattleEntity);
			System.out.println("before checking   "+objMilkProductionEntity);
			List<MilkProductionEntity> listMilkProduction = milkproductionManager.getMilkProductionDetails(objMilkProductionEntity);
			System.out.println("after  checking   "+listMilkProduction.size());
			System.out.println("after  checking 22   "+new Gson().toJson(listMilkProduction));
			if(listMilkProduction !=null && listMilkProduction.size() >0){
				objMilkProductionEntity.setMilkprodid(listMilkProduction.get(0).getMilkprodid()) ;
				objMilkProductionEntity.setUpdateddt(Common.getCurrentGMTTimestamp());
				objMilkProductionEntity.setUniquesynckey(objCattleEntity.getCattleeartagid()+"/"+new Date(objMilkProductionEntity.getMilkproddt().getTime()));
			}
			else{
				objMilkProductionEntity.setCreateddt(Common.getCurrentGMTTimestamp());
				objMilkProductionEntity.setUniquesynckey(objCattleEntity.getCattleeartagid()+"/"+new Date(objMilkProductionEntity.getMilkproddt().getTime()));
			}
			
			objMilkProductionEntity = milkproductionManager.saveMilkProduction(objMilkProductionEntity);
			if(objMilkProductionEntity!=null && objMilkProductionEntity.getMilkprodid()>0){
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",milkproductionGson.toJson(objMilkProductionEntity));
			}else{
				map.put("msg", MessageConstants.msg_3);
				map.put("status", "3");
			}
			map.remove("reqData");
			map.remove("u_id");
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in saveMilkProduction :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = milkproductionGson.toJson(map);
		return res.toString();
	}
	
	@RequestMapping(value = "/milkproductionDtls", method = RequestMethod.POST)
	public @ResponseBody
	String milkproductionDetails(@RequestBody String milkproductionDetails) {

		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
		Gson milkproductionGson = gsonBuilder.create();
		try {
			map = userManager.getReqDetails(milkproductionDetails,MessageConstants.ALL_PERMISSION);
			if(map.get("status").equals("1")){
			MilkProductionEntity objMilkProductionEntity = milkproductionGson.fromJson(map.get("reqData"), MilkProductionEntity.class);
			List<MilkProductionEntity> listMilkProduction = milkproductionManager.getMilkProductionDetails(objMilkProductionEntity);
			if(listMilkProduction !=null && listMilkProduction.size() >0){
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",milkproductionGson.toJson(listMilkProduction));
			}else{
				map.put("msg", MessageConstants.msg_2);
				map.put("status", "2");
			}
			map.remove("reqData");
			map.remove("u_id");
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in milkproductionDetails :"+e.getMessage());
			map.put("msg",MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = milkproductionGson.toJson(map);
		return res.toString();
	}
	
	@RequestMapping(value = "/getAllMilkProduction", method = RequestMethod.POST)
	public @ResponseBody
	String getAllMilkProduction(@RequestBody String milkproductionDetails) {

		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson resGson = new Gson();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
		Gson milkproductionGson = gsonBuilder.create();
		try {
			map = userManager.getReqDetails(milkproductionDetails,MessageConstants.ALL_PERMISSION);
			if(map.get("status").equals("1")){
			MilkProductionEntity objMilkProductionEntity = milkproductionGson.fromJson(map.get("reqData"), MilkProductionEntity.class);
			List<MilkProductionEntity> listMilkProduction = milkproductionManager.getMilkProductionDetails(objMilkProductionEntity);
			if(listMilkProduction !=null && listMilkProduction.size() >0){
				
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",resGson.toJson(listMilkProduction));
			}else{
				map.put("msg", MessageConstants.msg_2);
				map.put("status", "2");
			}
			map.remove("reqData");
			map.remove("u_id");
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in milkproductionDetails :"+e.getMessage());
			map.put("msg",MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = resGson.toJson(map);
		return res.toString();
	}


	@RequestMapping(value = "/deleteMilkProduction", method = RequestMethod.POST)
	public @ResponseBody
	String deleteMilkProduction(@RequestBody String milkproductionDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson milkproductionGson = new Gson();
		try {
			map = userManager.getReqDetails(milkproductionDetails,MessageConstants.ALL_PERMISSION);
			if (map.get("status").equals("1")) {
				MilkProductionEntity objMilkProductionEntity = milkproductionGson.fromJson(map.get("reqData"), MilkProductionEntity.class);
				
				int status = milkproductionManager.deleteMilkProduction(objMilkProductionEntity);
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
			logger.error("error in deleteMilkProduction :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = milkproductionGson.toJson(map);
		return res.toString();
	}
	@RequestMapping(value = "/getAllMilkProductionByOwner", method = RequestMethod.POST)
	public @ResponseBody
	String getAllMilkProductionByOwner(@RequestBody String milkproductionDetails) {

		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson resGson = new Gson();
		try {
			map = userManager.getReqDetails(milkproductionDetails,MessageConstants.ALL_PERMISSION);
			if(map.get("status").equals("1")){
			List<MilkProductionEntity> listMilkProduction = milkproductionManager.getMilkProductionDetailsByOwner(Integer.parseInt(map.get("u_id")));
			if(listMilkProduction !=null && listMilkProduction.size() >0){
				
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",resGson.toJson(listMilkProduction));
			}else{
				map.put("msg", MessageConstants.msg_2);
				map.put("status", "2");
			}
			map.remove("reqData");
			map.remove("u_id");
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in milkproductionDetails :"+e.getMessage());
			map.put("msg",MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = resGson.toJson(map);
		return res.toString();
	}
	
	@RequestMapping(value = "/getMilkProductionById", method = RequestMethod.POST)
	public @ResponseBody
	String getMilkProductionById(@RequestBody String milkproductionDetails) {

		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson resGson = new Gson();
		try {
			map = userManager.getReqDetails(milkproductionDetails,MessageConstants.ALL_PERMISSION);
			if(map.get("status").equals("1")){
			MilkProductionEntity objMilkProductionEntity = resGson.fromJson(map.get("reqData"), MilkProductionEntity.class);
			List<MilkProductionEntity> listMilkProduction = milkproductionManager.getMilkProductionById(objMilkProductionEntity);
			if(listMilkProduction !=null && listMilkProduction.size() >0){
				
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",resGson.toJson(listMilkProduction));
			}else{
				map.put("msg", MessageConstants.msg_2);
				map.put("status", "2");
			}
			map.remove("reqData");
			map.remove("u_id");
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in milkproductionDetails :"+e.getMessage());
			map.put("msg",MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = resGson.toJson(map);
		return res.toString();
	}

}
