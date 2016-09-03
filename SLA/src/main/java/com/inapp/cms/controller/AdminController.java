package com.inapp.cms.controller;


import java.sql.Timestamp;
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
import com.google.gson.GsonBuilder;
import com.inapp.cms.entity.OwnerEntity;
import com.inapp.cms.entity.RoleEntity;
import com.inapp.cms.entity.UserEntity;
import com.inapp.cms.service.AdminUserManager;
import com.inapp.cms.service.OwnerUserManager;
import com.inapp.cms.service.UserManager;
import com.inapp.cms.utils.Common;
import com.inapp.cms.utils.DateDeserializer;
import com.inapp.cms.utils.DateSerializer;
import com.inapp.cms.utils.EmailUser;
import com.inapp.cms.utils.HtmlReader;
import com.inapp.cms.utils.MessageConstants;
import com.inapp.cms.utils.RandomString;

/**
 * This is a controller which will serve all request related to the user service
 * API
 * 
 * @author Jinesh
 * @date May 21, 2015
 * @project CMS *
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private AdminUserManager adminUserManager;
	
	@Autowired
	private OwnerUserManager ownerUserManager;

	@Autowired
	private UserManager userManager;
	

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public @ResponseBody
	String saveUser(@RequestBody String userDetails) {
		
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson resGson = new Gson();
		HashMap<String, Object> retMap = new HashMap<String, Object>();
		try {
			map = userManager.getReqDetails(userDetails,MessageConstants.ADMIN_PERMISSION);
			//System.out.println("Map"+map);
			if (map.get("status").equals("1")) {
				UserEntity ObjUserEntity = resGson.fromJson(map.get("reqData"), UserEntity.class);
				RoleEntity objEntity = adminUserManager.getRole(ObjUserEntity);
				if(objEntity!=null){
					ObjUserEntity.setObjRoleEntity(objEntity);
					if(ObjUserEntity.getId()==null || ObjUserEntity.getId()==0){
					ObjUserEntity.setPassword(Common.encoder(ObjUserEntity.getPassword()));
					ObjUserEntity.setTemppassword(Common.encoder(ObjUserEntity.getPassword()));
					ObjUserEntity.setUser_updated_dt(Common.getCurrentTimestamp());
					ObjUserEntity.setCreateddt(Common.getCurrentGMTTimestamp()); //for syncing
					ObjUserEntity.setUniquesynckey(ObjUserEntity.getUsername()); //for syncing
					}
					else{
						ObjUserEntity.setUniquesynckey(ObjUserEntity.getUsername());
						ObjUserEntity.setUpdateddt(Common.getCurrentGMTTimestamp()); //for syncing
					}
					ObjUserEntity = adminUserManager.saveUserDetails(ObjUserEntity);
				
				if(ObjUserEntity != null && ObjUserEntity.getId() >0){
					retMap.put("userDtls", ObjUserEntity);
					if(ObjUserEntity.getObjRoleEntity().getRolename().toUpperCase().equals("OWNER")){
						OwnerEntity ObjOwnerEntity = resGson.fromJson(map.get("reqData"), OwnerEntity.class);
						ObjOwnerEntity.setObjUserEntity(ObjUserEntity);
						if(ObjOwnerEntity.getOwnerid()!=null && ObjOwnerEntity.getOwnerid()>0)
						{
							ObjOwnerEntity.setUpdateddt(Common.getCurrentGMTTimestamp()); // for sync
							ObjOwnerEntity.setId(ObjOwnerEntity.getOwnerid());
							ObjOwnerEntity.setUniquesynckey(ObjUserEntity.getUniquesynckey());
						}
						else{
							ObjOwnerEntity.setCreateddt(Common.getCurrentGMTTimestamp()); // for sync
							ObjOwnerEntity.setUniquesynckey(ObjUserEntity.getUniquesynckey());
						}
						ObjOwnerEntity = ownerUserManager.saveOwnerDetails(ObjOwnerEntity);
						retMap.put("ownerDtls", ObjUserEntity);
						//for sync	//save data to be deleted to a temporary table for syncing purpose
						int status = ownerUserManager.saveOwnerFarmMapping(ObjOwnerEntity);
						if(status ==1)
						{
							map.put("msg", MessageConstants.msg_1);
							map.put("status", "1");
							map.put("resData", resGson.toJson(retMap));
						}
						else{
							map.put("msg", MessageConstants.msg_3);
							map.put("status", "0");
							map.put("resData", resGson.toJson(retMap));
						}
					}
					
				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
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
			logger.error("error in saveUser :" + e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}

		res = resGson.toJson(map);
		return res.toString();
	}
	

	@RequestMapping(value = "/editUser", method = RequestMethod.POST)
	public @ResponseBody
	String editUser(@RequestBody String userDetails) {
		
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson resGson = new Gson();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
		Gson userGson = gsonBuilder.create();
		HashMap<String, Object> retMap = new HashMap<String, Object>();
		try {
			map = userManager.getReqDetails(userDetails,MessageConstants.ADMN_OWNR_PERMISSION);
			
			System.out.println("Map"+map);
			
			if (map.get("status").equals("1")) {
				UserEntity ObjUserEntity = userGson.fromJson(map.get("reqData"), UserEntity.class);
				RoleEntity objEntity = adminUserManager.getRole(ObjUserEntity);
				if(objEntity!=null){
					ObjUserEntity.setObjRoleEntity(objEntity);
					ObjUserEntity.setUpdateddt(Common.getCurrentGMTTimestamp()); //for syncing
					ObjUserEntity = adminUserManager.saveUserDetails(ObjUserEntity);
				
				if(ObjUserEntity != null && ObjUserEntity.getId() >0){
							map.put("msg", MessageConstants.msg_1);
							map.put("status", "1");
							map.put("resData", resGson.toJson(retMap));
				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
			}
				else{
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
				map.remove("reqData");
				map.remove("u_id");
			}
		} catch (Exception e) {
		//	e.printStackTrace();
			logger.error("error in editUser :" + e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}

		res = resGson.toJson(map);
		return res.toString();
	}
	
	@RequestMapping(value = "/getUserDetails", method = RequestMethod.POST)
	public @ResponseBody
	String getOwnerDetails(@RequestBody String ownerDetails) {
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		Gson userGson = new Gson();
		try {
			map = userManager.getReqDetails(ownerDetails,MessageConstants.ALL_PERMISSION);
			
			if (map.get("status").equals("1")) {
				UserEntity ObjUserEntity = userGson.fromJson(map.get("reqData"), UserEntity.class);
				List<Object> list= ownerUserManager.getUserDetails(ObjUserEntity.getId(), ObjUserEntity.getRole_name() );
				List<Object> farmList= ownerUserManager.getFarmsAssignedByUserId(ObjUserEntity.getId(), ObjUserEntity.getRole_name() );
				if(farmList.size() > 0)
				{
					HashMap<String, List<Object>> farmIds = new HashMap<String, List<Object>>();
					farmIds.put("farmIds", farmList);
					list.add(farmIds);
				}
				
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
			logger.error("error in getOwnerDetails :" + e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}

		res = userGson.toJson(map);
		return res.toString();
	}
	

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public @ResponseBody
	String changePassword(@RequestBody String userDetails) {
		
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		Gson userGson = new Gson();
		HashMap<String, Object> retMap = new HashMap<String, Object>();
		try {
			map = userManager.getReqDetails(userDetails,MessageConstants.ALL_PERMISSION);
			
			
			if (map.get("status").equals("1")) {
				UserEntity ObjUserEntity = userGson.fromJson(map.get("reqData"), UserEntity.class);
				String password = ObjUserEntity.getPassword();
				ObjUserEntity.setId(Integer.parseInt(map.get("u_id").toString()));
				List<UserEntity> list = userManager.getUser(ObjUserEntity);
				if(list != null && list.size() >0){
					
					if(Common.decoder(list.get(0).getPassword()).equals(password.trim())){
						String newpassword = Common.encoder(ObjUserEntity.getNewpassword());
						ObjUserEntity.setNewpassword(newpassword);
						ObjUserEntity.setUpdateddt(Common.getCurrentGMTTimestamp()); //for syncing
						userManager.updatePassword(ObjUserEntity);
						    map.put("msg", MessageConstants.msg_1);
							map.put("status", "1");
							map.put("resData", userGson.toJson(retMap));
					}else if(Common.decoder(list.get(0).getTemppassword()).equals(password.trim())){
						String newpassword = Common.encoder(ObjUserEntity.getNewpassword());
						ObjUserEntity.setNewpassword(newpassword);
						ObjUserEntity.setUpdateddt(Common.getCurrentGMTTimestamp()); //for syncing
						userManager.updatePassword(ObjUserEntity);
						    map.put("msg", MessageConstants.msg_1);
							map.put("status", "1");
							map.put("resData", userGson.toJson(retMap));
					}
					else{
						map.put("msg", MessageConstants.msg_2);
						map.put("status", "2");
					}
				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
				map.remove("reqData");
			}
		} catch (Exception e) {
		//	e.printStackTrace();
			logger.error("error in editUser :" + e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}

		res = userGson.toJson(map);
		return res.toString();
	}
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public @ResponseBody
	String forgotPassword(@RequestBody String userDetails) {
		
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		Gson userGson = new Gson();
		try {
			UserEntity objUser = userGson.fromJson(userDetails, UserEntity.class);
			RandomString s = new RandomString(8);
			String password = "";
			password = s.nextString(); 
			objUser.setUpdateddt(Common.getCurrentGMTTimestamp()); //for sync
			objUser.setTemppassword(Common.encoder(password));
			String platform = objUser.getPlatform()!=null?objUser.getPlatform():"web";
			objUser = userManager.validateMail(objUser);
			if(null != objUser){
				if(objUser.getObjRoleEntity().getRolename().toUpperCase().equals("OWNER") && platform.equals("web")){
					map.put("msg", MessageConstants.msg_14);
					map.put("status", "14");
				}else{
					String msgText = HtmlReader.htmlReader(objUser.getUserfname(), objUser.getUseremail(), "password",password);
					boolean status = EmailUser.emailUser(objUser.getUseremail(), "Reset Password", msgText);
					if(status){
						map.put("msg", MessageConstants.msg_1);
						map.put("status", "1");
						map.put("resData",Common.encoder(password));
					}else{
						map.put("msg", MessageConstants.msg_10);
						map.put("status", "10");
					}
				}
			}else{
				map.put("msg", MessageConstants.msg_13);
				map.put("status", "13");
			}
		} catch (Exception e) {
		//	e.printStackTrace();
			logger.error("error in forgotPassword :" + e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}

		res = userGson.toJson(map);
		return res.toString();
	}
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public @ResponseBody
	String restPassword(@RequestBody String userDetails) {
		
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		Gson userGson = new Gson();
		HashMap<String, Object> retMap = new HashMap<String, Object>();
		try {
			JSONObject obj = new JSONObject(userDetails);
			String u_id = (obj.getString("u_id") != null) ? obj
					.getString("u_id") : "";
			String reqData = (obj.getString("reqData") != null) ? obj
					.getString("reqData") : "";
				UserEntity ObjUserEntity = userGson.fromJson(reqData, UserEntity.class);
				ObjUserEntity.setId(Integer.parseInt(u_id));
				String newpassword = Common.encoder(ObjUserEntity
						.getNewpassword());
				ObjUserEntity.setNewpassword(newpassword);
				ObjUserEntity.setUpdateddt(Common.getCurrentGMTTimestamp()); // for sync
				userManager.updatePassword(ObjUserEntity);
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				map.put("resData", userGson.toJson(retMap));

				map.remove("reqData");
			
		} catch (Exception e) {
		//	e.printStackTrace();
			logger.error("error in resetPassword :" + e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}

		res = userGson.toJson(map);
		return res.toString();
	}
	
}
