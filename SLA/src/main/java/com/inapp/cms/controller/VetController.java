package com.inapp.cms.controller;

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

import com.google.gson.Gson;
import com.inapp.cms.common.Constants;
import com.inapp.cms.common.Response;
import com.inapp.cms.entity.RoleEntity;
import com.inapp.cms.entity.UserEntity;
import com.inapp.cms.entity.VetEntity;
import com.inapp.cms.service.AdminUserManager;
import com.inapp.cms.service.CattleManager;
import com.inapp.cms.service.OwnerUserManager;
import com.inapp.cms.service.UserManager;
import com.inapp.cms.service.VetManager;
import com.inapp.cms.utils.Common;
import com.inapp.cms.utils.MessageConstants;

@Controller
@RequestMapping("/vet")
public class VetController {
	public static final Logger logger = Logger.getLogger(VetController.class);
	
	@Autowired
	private VetManager  vetmanager ;

	@Autowired
	private AdminUserManager adminUserManager;
	
	@Autowired
	private CattleManager cattleManager;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private OwnerUserManager ownerUserManager;
	
	
	
	@RequestMapping(value = "/registerVet",method = RequestMethod.POST)
	public @ResponseBody String addVet(@RequestBody String vetDetails) {
		Response response = new Response();
		Gson gson = new Gson();
		try {			
			logger.info("------------------------- Add Vet -----------------------------------");
			logger.info("vetDetails  : " + vetDetails);			
			
			JSONObject jsonObject = new JSONObject(vetDetails);
			if(!jsonObject.get("password").equals(jsonObject.get("confirmpass"))){
				response.put(Constants.STATUS, Constants.MISSMATCH_PASSWORD);
				response.put(Constants.MESSAGE, "Password and Confirm password are not same.");
				return new Gson().toJson(response).toString();	
			}		
			UserEntity vetUserEntity = gson.fromJson(vetDetails, UserEntity.class);		
			RoleEntity role = adminUserManager.getRole(vetUserEntity);
			if(role != null){			
				 	vetUserEntity.setObjRoleEntity(role);				
				 	vetUserEntity.setPassword(Common.encoder(vetUserEntity.getPassword()));	
				 	vetUserEntity.setIsactive(false);
				 	if(vetUserEntity.getId()!=null && vetUserEntity.getId()>0)
				 	{
				 		vetUserEntity.setUpdateddt(Common.getCurrentGMTTimestamp());// for sync
				 	}else{
				 		vetUserEntity.setCreateddt(Common.getCurrentGMTTimestamp());// for sync
				 		vetUserEntity.setUniquesynckey(vetUserEntity.getUsername());// for sync
				 	}
				 	vetUserEntity = vetmanager.save(vetUserEntity);
				 	if(vetUserEntity != null && vetUserEntity.getId() > 0){
				 		VetEntity  vetEntity = gson.fromJson(vetDetails, VetEntity.class);
				 		if(vetEntity.getVetid()!=null && vetEntity.getVetid()>0)
				 		{
				 			vetEntity.setUpdateddt(Common.getCurrentGMTTimestamp());// for sync
				 		}else{
				 			vetEntity.setCreateddt(Common.getCurrentGMTTimestamp());// for sync
				 			vetEntity.setUniquesynckey(vetUserEntity.getUsername());// for sync	
				 		}
				 		vetEntity.setObjUserEntity(vetUserEntity);			 		
				 		vetEntity  = vetmanager.saveVetDetails(vetEntity);			
				 		
				 		response.put(Constants.STATUS, Constants.SUCCESS);
						response.put(Constants.MESSAGE, Constants.SUCCESS_MESSAGE);
				 	}else{
				 		response.put(Constants.STATUS, Constants.FAILED);
						response.put(Constants.MESSAGE, Constants.FAILED_MESSAGE);
				 	}			 	
			}else{
				response.put(Constants.STATUS, Constants.FAILED);
				response.put(Constants.MESSAGE, "Role not set");
			}		
		} catch (Exception e) {
			response.put(Constants.STATUS, Constants.FAILED);
			response.put(Constants.MESSAGE, Constants.FAILED_MESSAGE);
			logger.error("Error in add vet details" , e);
		}		
		return new Gson().toJson(response).toString();		
	}
	

	@RequestMapping(value = "/getVetDetailsByName", method = RequestMethod.POST)
	public @ResponseBody
	String getVetDetails(@RequestBody String vetDetails) {
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		Gson userGson = new Gson();
		try {
			map = userManager.getReqDetails(vetDetails,MessageConstants.ALL_PERMISSION);
			
			if (map.get("status").equals("1")) {
				VetEntity ObjVetEntity = userGson.fromJson(map.get("reqData"), VetEntity.class);
				List<HashMap<String, Object>> list= vetmanager.getAllVetByFnameOrLname(ObjVetEntity.getVetfnamelname());
				
				if(list.size() > 0){
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData", userGson.toJson(list));
				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
				map.remove("reqData");
				map.remove("u_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getVetDetails :" + e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}

		res = userGson.toJson(map);
		return res.toString();
	}
	
	@RequestMapping(value = "/saveVetFarm", method = RequestMethod.POST)
	public @ResponseBody
	String saveVetFarmDetails(@RequestBody String vetDetails) {
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		Gson userGson = new Gson();
		try {
			map = userManager.getReqDetails(vetDetails,MessageConstants.ADMIN_PERMISSION);
			
			if (map.get("status").equals("1")) {
				VetEntity ObjVetEntity = userGson.fromJson(map.get("reqData"), VetEntity.class);
				//for sync	//save data to be deleted to a temporary table for syncing purpose
				int status = vetmanager.saveVetFarm(ObjVetEntity);
				
				if(status > 0){
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData", userGson.toJson(ObjVetEntity));
				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
				map.remove("reqData");
				map.remove("u_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getVetDetails :" + e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}

		res = userGson.toJson(map);
		return res.toString();
	}

	
	@RequestMapping(value = "/approveVet", method = RequestMethod.POST)
	public @ResponseBody
	String approveVet(@RequestBody String vetDetails) {
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		Gson userGson = new Gson();
		try {
			map = userManager.getReqDetails(vetDetails,MessageConstants.ADMIN_PERMISSION);
			
			if (map.get("status").equals("1")) {
				UserEntity ObjUserEntity = userGson.fromJson(map.get("reqData"), UserEntity.class);
				VetEntity ObjVetReqEntity = userGson.fromJson(map.get("reqData"), VetEntity.class);
				List<Object> vetList = ownerUserManager.getUserDetails(ObjUserEntity.getId(), "vet");
				VetEntity ObjVetEntity = new VetEntity();
				if(vetList.size()>0){
				 ObjVetEntity = (VetEntity)vetList.get(0);
				}
				int status = 0; 
						if(ObjVetEntity!=null && ObjVetEntity.getVetid()>0){
							ObjVetEntity.setVetStatus(ObjVetReqEntity.getVetStatus());
							ObjVetEntity.setUpdateddt(Common.getCurrentGMTTimestamp());// for sync
							status = vetmanager.approveVet(ObjVetEntity);
						}
				if(status > 0){
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData", userGson.toJson(ObjVetEntity));
				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
				map.remove("reqData");
				map.remove("u_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getVetDetails :" + e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}

		res = userGson.toJson(map);
		return res.toString();
	}


	@RequestMapping(value = "/editVet",method = RequestMethod.POST)
	public @ResponseBody String editVet(@RequestBody String vetDetails) {
		Response response = new Response();
		Gson gson = new Gson();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		try {			
			logger.info("------------------------- edit Vet -----------------------------------");
			logger.info("vetDetails  : " + vetDetails);			
			map = userManager.getReqDetails(vetDetails,MessageConstants.VET_PERMISSION);
			if (map.get("status").equals("1")) {	
			UserEntity vetUserEntity = gson.fromJson(map.get("reqData"), UserEntity.class);	
			RoleEntity role = adminUserManager.getRole(vetUserEntity);
			if(role != null){			
				 	vetUserEntity.setObjRoleEntity(role);
				 	vetUserEntity.setUpdateddt(Common.getCurrentGMTTimestamp()); // for sync
				 	vetUserEntity = vetmanager.save(vetUserEntity);		
				 	if(vetUserEntity.getId() != null && vetUserEntity.getId() > 0){
				 		VetEntity  vetEntity = gson.fromJson(map.get("reqData"), VetEntity.class);
				 		vetEntity.setUpdateddt(Common.getCurrentGMTTimestamp()); // for sync
				 		vetEntity.setObjUserEntity(vetUserEntity);		
				 		vetEntity.setVetStatus(1);
				 		vetEntity  = vetmanager.saveVetDetails(vetEntity);			
				 		response.put(Constants.STATUS, Constants.SUCCESS);
						response.put(Constants.MESSAGE, Constants.SUCCESS_MESSAGE);
				 	}else{
				 		response.put(Constants.STATUS, Constants.FAILED);
						response.put(Constants.MESSAGE, Constants.FAILED_MESSAGE);
				 	}			 	
			}else{
				response.put(Constants.STATUS, Constants.FAILED);
				response.put(Constants.MESSAGE, "Role not set");
			}
		}
		} catch (Exception e) {
			response.put(Constants.STATUS, Constants.FAILED);
			response.put(Constants.MESSAGE, Constants.FAILED_MESSAGE);
			logger.error("Error in editVet" , e);
		}		
		return new Gson().toJson(response).toString();		
	}
	
	

}
