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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inapp.cms.entity.CattleEntity;
import com.inapp.cms.entity.HealthCareEntity;
import com.inapp.cms.entity.HealthcareVaccLinkEntity;
import com.inapp.cms.entity.UserEntity;
import com.inapp.cms.entity.VaccinationEntity;
import com.inapp.cms.service.CattleManager;
import com.inapp.cms.service.HealthCareManager;
import com.inapp.cms.service.UserManager;
import com.inapp.cms.service.VaccinationManager;
import com.inapp.cms.utils.Common;
import com.inapp.cms.utils.DateDeserializer;
import com.inapp.cms.utils.DateSerializer;
import com.inapp.cms.utils.MessageConstants;

@Controller
@RequestMapping(value = "/healthcare")
public class HealthCareController {
	private static final Logger logger = Logger.getLogger(HealthCareController.class);
	@Autowired
	private HealthCareManager healthcareManager;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private CattleManager cattleManager;
	
	@Autowired
	private VaccinationManager vaccinationManager;
	

	@RequestMapping(value = "/saveHealthCare", method = RequestMethod.POST)
	public @ResponseBody
	String saveHealthCare(@RequestBody String healthcareDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();String earTag = "";
		String res = "";
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
		Gson healthcareGson = gsonBuilder.create();
		try {
			map = userManager.getReqDetails(healthcareDetails,MessageConstants.VET_PERMISSION);
			if(map.get("status").equals("1")){
			HealthCareEntity objHealthCareEntity = healthcareGson.fromJson(map.get("reqData"), HealthCareEntity.class);
			HealthcareVaccLinkEntity objHealthcareVaccLinkEntity = healthcareGson.fromJson(map.get("reqData"), HealthcareVaccLinkEntity.class);
			
			if(objHealthcareVaccLinkEntity.getVacchctype()==0){
				VaccinationEntity objVaccinationEntity = new VaccinationEntity();
				objVaccinationEntity.setVaccinationid(objHealthcareVaccLinkEntity.getObjVaccinationEntity().getVaccinationid());
				objVaccinationEntity = vaccinationManager.getVaccinationDetails(objVaccinationEntity).get(0);
				if(objVaccinationEntity!=null){
					objHealthcareVaccLinkEntity.setObjVaccinationEntity(objVaccinationEntity);
				}
			}else if(objHealthcareVaccLinkEntity.getVacchctype()==1)
			{
				objHealthcareVaccLinkEntity.setObjVaccinationEntity(null);
				objHealthcareVaccLinkEntity.setVaccroutine(null);
			}
			if(objHealthCareEntity.getHealthcareid()!=null && objHealthCareEntity.getHealthcareid()>0)
			{
				objHealthCareEntity.setUpdateddt(Common.getCurrentGMTTimestamp());// for sync
				
			}
			else{
				objHealthCareEntity.setCreateddt(Common.getCurrentGMTTimestamp());// for sync
				
			}
			
			CattleEntity objCattleEntity = new CattleEntity();
			objCattleEntity.setCattleid(objHealthCareEntity.getObjCattleEntity().getCattleid());
			earTag = cattleManager.getCattleDetails(objCattleEntity).getCattleeartagid();
			objHealthCareEntity.setUniquesynckey(earTag+"/"+objHealthCareEntity.getServicedate());// for sync
			
			objHealthCareEntity = healthcareManager.saveHealthCare(objHealthCareEntity);
			objHealthcareVaccLinkEntity.setObjHealthCareEntity(objHealthCareEntity);
			if(objHealthCareEntity.getHealthcareid()!=null && objHealthCareEntity.getHealthcareid()>0){
				if(objHealthcareVaccLinkEntity.getVaccid()!=null && objHealthcareVaccLinkEntity.getVaccid()>0){
					objHealthcareVaccLinkEntity.setUpdateddt(Common.getCurrentGMTTimestamp())	;
				}
				else{
					objHealthcareVaccLinkEntity.setCreateddt(Common.getCurrentGMTTimestamp());// for sync
				}
				objHealthcareVaccLinkEntity.setVacchcvetid(objHealthCareEntity.getVetid());
				objHealthcareVaccLinkEntity = healthcareManager.saveHealthCareTypeDetails(objHealthcareVaccLinkEntity);
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",healthcareGson.toJson(objHealthcareVaccLinkEntity));
			}else{
				map.put("msg", MessageConstants.msg_3);
				map.put("status", "3");
			}
			map.remove("reqData");
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in saveHealthCare :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = healthcareGson.toJson(map);
		return res.toString();
	}
	

	
	@RequestMapping(value = "/healthcareDetails", method = RequestMethod.POST)
	public @ResponseBody
	String healthcareDetails(@RequestBody String healthcareDetails) {

		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
		Gson healthcareGson = gsonBuilder.create();
		Gson resGson = new Gson();
		try {
			map = userManager.getReqDetails(healthcareDetails,MessageConstants.ALL_PERMISSION);
			if(map.get("status").equals("1")){
			HealthCareEntity objHealthCareEntity = healthcareGson.fromJson(map.get("reqData"), HealthCareEntity.class);
			List<HealthCareEntity> listHealthCare = healthcareManager.getHealthCareDetails(objHealthCareEntity);
			
			if(listHealthCare!=null && listHealthCare.size() >0){
				List<HealthcareVaccLinkEntity> listHealthCareTypeDetails = healthcareManager.getHealthCareTypeDetails(listHealthCare.get(0));
				HashMap<String, Object> finalMap = new HashMap<String, Object>();
				finalMap.put("masterData", listHealthCare);
				if(listHealthCareTypeDetails!=null&& listHealthCareTypeDetails.size()>0)
				{
					finalMap.put("detailData", listHealthCareTypeDetails);
				}
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",resGson.toJson(finalMap));
			}else{
				map.put("msg", MessageConstants.msg_2);
				map.put("status", "2");
			}
			map.remove("reqData");
			map.remove("u_id");
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in healthcareDetails :"+e.getMessage());
			map.put("msg",MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = healthcareGson.toJson(map);
		return res.toString();
	}
	
	@RequestMapping(value = "/getHealthCareList", method = RequestMethod.POST)
	public @ResponseBody
	String getHealthCareList(@RequestBody String healthcareDetails) {

		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
		Gson healthcareGson = gsonBuilder.create();List<HealthCareEntity> listHealthCare = null;
		Gson resGson = new Gson();String rolename = "ADMIN";List<HashMap<String, Object>> listHealthCareByRole = null;
		try {
			map = userManager.getReqDetails(healthcareDetails,MessageConstants.ALL_PERMISSION);
			if(map.get("status").equals("1")){
			UserEntity objUserEntity = new UserEntity();
			objUserEntity.setId(Integer.parseInt(map.get("u_id").toString()));
			List<UserEntity> listUserEntity  = userManager.getUser(objUserEntity);
			if(listUserEntity!=null && listUserEntity.size()>0)
			{
				rolename = 	listUserEntity.get(0).getObjRoleEntity().getRolename();
			}
			
			HealthCareEntity objHealthCareEntity = healthcareGson.fromJson(map.get("reqData"), HealthCareEntity.class);
			if(rolename.toUpperCase().equals("ADMIN")){
			listHealthCare = healthcareManager.getHealthCareDetails(objHealthCareEntity);
			}
			else{
				listHealthCareByRole = healthcareManager.getHealthCareDetailsByRole(objHealthCareEntity, rolename.toUpperCase(), listUserEntity.get(0).getId());
			}
			if(listHealthCare!=null && listHealthCare.size() >0){
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",resGson.toJson(listHealthCare));
			}
			else if(listHealthCareByRole!=null && listHealthCareByRole.size()>0)
			{
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",resGson.toJson(listHealthCareByRole));
			}
			else{
				map.put("msg", MessageConstants.msg_2);
				map.put("status", "2");
			}
			map.remove("reqData");
			map.remove("u_id");
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in healthcareDetails :"+e.getMessage());
			map.put("msg",MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = healthcareGson.toJson(map);
		return res.toString();
	}
	
	@RequestMapping(value = "/healthcareTypeDetailsById", method = RequestMethod.POST)
	public @ResponseBody
	String healthcareTypeDetailsById(@RequestBody String healthcareDetails) {

		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
		Gson healthcareGson = gsonBuilder.create();
		try {
			map = userManager.getReqDetails(healthcareDetails,MessageConstants.ALL_PERMISSION);
			if(map.get("status").equals("1")){
				HealthcareVaccLinkEntity objHealthVaccLinkEntity = healthcareGson.fromJson(map.get("reqData"), HealthcareVaccLinkEntity.class);
			List<HealthcareVaccLinkEntity> listHealthCare = healthcareManager.getHealthCareTypeDetailsById(objHealthVaccLinkEntity.getVaccid());
			
			if(listHealthCare!=null && listHealthCare.size() >0){
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",healthcareGson.toJson(listHealthCare));
			}else{
				map.put("msg", MessageConstants.msg_2);
				map.put("status", "2");
			}
			map.remove("reqData");
			map.remove("u_id");
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in healthcareDetails :"+e.getMessage());
			map.put("msg",MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = healthcareGson.toJson(map);
		return res.toString();
	}

	@RequestMapping(value = "/deleteHealthCare", method = RequestMethod.POST)
	public @ResponseBody
	String deleteHealthCare(@RequestBody String healthcareDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson healthcareGson = new Gson();
		try {
			map = userManager.getReqDetails(healthcareDetails,MessageConstants.ALL_PERMISSION);
			if (map.get("status").equals("1")) {
				HealthCareEntity objHealthCareEntity = healthcareGson.fromJson(map.get("reqData"), HealthCareEntity.class);
				
				int status = healthcareManager.deleteHealthCare(objHealthCareEntity);
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
			logger.error("error in deleteHealthCare :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = healthcareGson.toJson(map);
		return res.toString();
	}
		
}
