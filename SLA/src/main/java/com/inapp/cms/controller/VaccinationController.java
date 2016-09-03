package com.inapp.cms.controller;

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
import com.inapp.cms.entity.VaccinationEntity;
import com.inapp.cms.service.UserManager;
import com.inapp.cms.service.VaccinationManager;
import com.inapp.cms.utils.Common;
import com.inapp.cms.utils.MessageConstants;

@Controller
@RequestMapping(value = "/vaccination")
public class VaccinationController {
	private static final Logger logger = Logger.getLogger(VaccinationController.class);
	@Autowired
	private VaccinationManager vaccinationManager;
	
	@Autowired
	private UserManager userManager;

	@RequestMapping(value = "/saveVaccination", method = RequestMethod.POST)
	public @ResponseBody
	String saveVaccination(@RequestBody String vaccinationDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		/*GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());*/
		Gson vaccinationGson = new Gson();
		try {
			map = userManager.getReqDetails(vaccinationDetails,MessageConstants.VET_PERMISSION);
			if(map.get("status").equals("1")){
			VaccinationEntity objVaccinationEntity = vaccinationGson.fromJson(map.get("reqData"), VaccinationEntity.class);
			if(objVaccinationEntity.getVaccinationid()!=null && objVaccinationEntity.getVaccinationid()>0)
			{
				objVaccinationEntity.setUpdateddt(Common.getCurrentGMTTimestamp()); // for sync
			}else{
				objVaccinationEntity.setCreateddt(Common.getCurrentGMTTimestamp());// for sync
			}
			objVaccinationEntity = vaccinationManager.saveVaccination(objVaccinationEntity);
			if(objVaccinationEntity!=null){
				if(objVaccinationEntity.isExist() == true){
					map.put("msg","Vaccination Details already Exist");
					map.put("status", "13");
				}else{
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData",vaccinationGson.toJson(objVaccinationEntity));
				}
			}else{
				map.put("msg", MessageConstants.msg_3);
				map.put("status", "3");
			}
			map.remove("reqData");
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in saveVaccination :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = vaccinationGson.toJson(map);
		return res.toString();
	}
	
	@RequestMapping(value = "/vaccinationDetails", method = RequestMethod.POST)
	public @ResponseBody
	String vaccinationDetails(@RequestBody String vaccinationDetails) {

		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		/*GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());*/
		Gson vaccinationGson = new Gson();
		
		try {
			map = userManager.getReqDetails(vaccinationDetails,MessageConstants.ALL_PERMISSION);
			if(map.get("status").equals("1")){
			VaccinationEntity objVaccinationEntity = vaccinationGson.fromJson(map.get("reqData"), VaccinationEntity.class);
			List<VaccinationEntity> listVaccination = vaccinationManager.getVaccinationDetails(objVaccinationEntity);
			if(listVaccination.size() >0){
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData",vaccinationGson.toJson(listVaccination));
			}else{
				map.put("msg", MessageConstants.msg_2);
				map.put("status", "2");
			}
			map.remove("reqData");
			map.remove("u_id");
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in vaccinationDetails :"+e.getMessage());
			map.put("msg",MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = vaccinationGson.toJson(map);
		return res.toString();
	}

	@RequestMapping(value = "/deleteVaccination", method = RequestMethod.POST)
	public @ResponseBody
	String deleteVaccination(@RequestBody String vaccinationDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson vaccinationGson = new Gson();
		try {
			map = userManager.getReqDetails(vaccinationDetails,MessageConstants.ADMN_VET_PERMISSION);
			if (map.get("status").equals("1")) {
				VaccinationEntity objVaccinationEntity = vaccinationGson.fromJson(map.get("reqData"), VaccinationEntity.class);
				
				int status = vaccinationManager.deleteVaccination(objVaccinationEntity);
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
			logger.error("error in getVaccinationData :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = vaccinationGson.toJson(map);
		return res.toString();
	}
		
}
