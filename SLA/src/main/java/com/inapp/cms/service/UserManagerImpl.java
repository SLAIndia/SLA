package com.inapp.cms.service;


import java.io.InputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.inapp.cms.dao.UserDAO;
import com.inapp.cms.entity.LoginEntity;
import com.inapp.cms.entity.UserEntity;
import com.inapp.cms.utils.Common;
import com.inapp.cms.utils.MessageConstants;
import com.inapp.cms.utils.ServiceConstants;
/**
 * @author Jinesh George
 */

@Service(ServiceConstants.USER_MANAGER)
public class UserManagerImpl implements UserManager {
	
	@Autowired
    private UserDAO userDAO;
	
	private static final Logger logger = Logger.getLogger(UserManagerImpl.class);

	@Transactional
	public void addUser(UserEntity user) {
		userDAO.addUser(user);
	}
	

	@Override
	public  List<UserEntity> getUser(UserEntity user) {
		return userDAO.getUser(user);
	}


	
	@Transactional
	public List<UserEntity> getAllUsers() {
		return userDAO.getAllUsers();
	}

	@Transactional
	public void deleteUser(Integer userId) {
		userDAO.deleteUser(userId);
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Transactional
	public List<UserEntity> userLogin(LoginEntity login) {
		return userDAO.userLogin(login);		
	}

	@Override
	public LinkedHashMap<String, String> userValidation(String signature,
			UserEntity objUserEntity, HashMap<Integer, Double> loginMap,
			Logger logger) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedHashMap<String, String> userValidationWeb(
			UserEntity objUserEntity, HashMap<Integer, Double> loginMap,
			Logger logger) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getHostDetailsByUserId(int userID,
			int requestedUserID) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getClientDetailsByUserId(int userID,
			int requestedUserID) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateUserMappingStatus(int hostID, int clientID,
			int mappingStatus, int inviteeMode) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Map<String, Object>> getClientTrackByUserId(int userID)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getHostTrackByUserId(int userID)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveMultipleFiles(InputStream inputStream, String filePath,
			String fileName, int userID) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHostIdByUserID(int userID) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getClientIdByUserID(int userID) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, Object> getUserDetails(int userID) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logout(String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUserOnlne(int userId, Timestamp dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getClientStatus(int clientID) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void cleariPhonePushToken(String string) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<LinkedHashMap<String, String>> getInvitationTypeAndStatus(
			int fromUserId, int toUserId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isClientAccepted(int clientID) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<UserEntity> getUserDetailsBySocialAc(Integer acType, String acId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateSocialLinks(int userID, int socilType, String socialID)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Object> getUserDetailsBySocialAcIds(int actype,
			String socialacids, int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrUpdateNotificationInterval(int hostUserId,
			int clientUserId, float timeInterval) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getNotificationInterval(int hostId, int clientUserId)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<UserEntity> getUserDetailsByEmail(String trim) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> getUserDetailsByEmail(JsonArray jArray, int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, Object> getUserDetails(UserEntity userObj) {

		return userDAO.getUserDetails(userObj);
	}

	@Override
	public boolean isUserNameExist(String username) throws Exception {
		return userDAO.isUserNameExist(username);
	}

	@Override
	public LinkedHashMap<String, String> getReqDetails(String reqJson,
			String[] permissionRoles) {

		LinkedHashMap<String, String> map = null;
		try {
			JSONObject obj = new JSONObject(reqJson);
			String u_id = (obj.getString("u_id") != null) ? obj
					.getString("u_id") : "";
			String signature = (obj.getString("signature") != null) ? obj
					.getString("signature") : "";
			String reqData = (obj.getString("reqData") != null) ? obj
					.getString("reqData") : "";
			map = Common.userValidation(signature, Integer.parseInt(u_id));

			if (map.get("status").equals("1")) {
				 map = roleVerification(Integer.parseInt(u_id),permissionRoles);
				if (map.get("status").equals("1")) {
					map.put("reqData", reqData);
					map.put("u_id", u_id);
				}
			}

		} catch (Exception e) {
			 logger.error("Error in getReqDetails method"+e.getMessage());
		}

		return map;
	}

	@Override
	public LinkedHashMap<String, String> getReqDetails(String signature,
			String u_id, String reqData, String[] permissionRoles) {

		LinkedHashMap<String, String> map = null;
		try {

			u_id = (u_id != null) ? u_id : "";
			signature = (signature != null) ? signature : "";
			reqData = (reqData != null) ? reqData : "";

			map = Common.userValidation(signature, Integer.parseInt(u_id));
			if (map.get("status").equals("1")) {
				map = roleVerification(Integer.parseInt(u_id), permissionRoles);
				if (map.get("status").equals("1")) {
					map.put("reqData", reqData);
					map.put("u_id", u_id);
				}
			}

		} catch (Exception e) {
			 logger.error("Error in getReqDetails method"+e.getMessage());
		}

		return map;
	}

	private LinkedHashMap<String, String> roleVerification(int u_id,
			String[] permissionRoles) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

		try {
			UserEntity obj = userDAO.getUser(u_id);
			boolean permissionFlag = false;
			if (null != obj) {
				String rolename = obj.getObjRoleEntity().getRolename()
						.toUpperCase();
				logger.info("rolename " + rolename + "  permissionFlag"+ permissionFlag);
				for(String role : permissionRoles)
				{
					if(role.equals(rolename)){
						permissionFlag = true;
					}
				}
				if (permissionFlag==true) {
					map.put("status", "1");
					map.put("msg", MessageConstants.msg_1);
				} else {
					map.put("status", "12");
					map.put("msg",MessageConstants.msg_12);
				}

			} else {
				map.put("status", "0");
			}
		} catch (Exception e) {
			 logger.error("error in roleVerification method"+e.getMessage());
		}
		return map;
	}

	@Override
	public void updatePassword(UserEntity objUserEntity) throws Exception {
		userDAO.updatePassword(objUserEntity);
		
	}


	@Override
	public UserEntity validateMail(UserEntity objUser) {
		return userDAO.validateMail(objUser);
	}


	@Override
	public boolean isemailExist(String email,String uId) {
		return userDAO.isemailExist(email,uId);
	}
	
	
	/*
	@Transactional
	public LinkedHashMap<String, String> userValidation(String signature, UserEntity objUserEntity, HashMap<Integer, Double>loginMap, Logger logger) {
		LinkedHashMap<String, String> resultMap = new LinkedHashMap<String, String>();
		List<UserSubscriptionEntity> subsciptionList = new ArrayList<UserSubscriptionEntity>();
		try{
		UserSubscriptionEntity objSubscriptionEntity = new UserSubscriptionEntity();
		subsciptionList = getUserSubscription(objUserEntity
				.getId());
		Timestamp currentdateTime = Common.getCurrentGMTTimestamp();boolean subscriptionFlag = false;
		if(subsciptionList.size()>0)
		{
			subscriptionFlag = true
			objSubscriptionEntity = subsciptionList.get(0);
		}
		if(loginMap.containsKey(objUserEntity.getId())){
		if (loginMap.get(objUserEntity.getId()).toString().equals(signature)) {
			if (subscriptionFlag) {
			if (getUser(objUserEntity).get(0).isIsactive()) {
				if ((objSubscriptionEntity.getValidto().after(currentdateTime) && objSubscriptionEntity
						.getValidfrom().before(currentdateTime))) {
					resultMap.put("result", "true");
					resultMap.put("status", "1");
					resultMap.put("message", "user subscribed");
				} else {
					resultMap.put("result", "false");
					resultMap.put("status", "0");
					resultMap.put("message", "user not subscribed");
				}
				
			} else {
				resultMap.put("result", "false");
				resultMap.put("status", "0");
				resultMap.put("message", "User verification pending/user not valid");
			}
		}
			else {
				resultMap.put("result", "false");
				resultMap.put("status", "0");
				resultMap.put("message", "User verification & subscription  pending");
			}
			} else {
			resultMap.put("result", "false");
			resultMap.put("status", "9");
			resultMap.put("message", "wrong signature");
		}
		}
		else
		{
			resultMap.put("result", "false");
			resultMap.put("status", "0");
			resultMap.put("message", "User Not log In");
		}
		logger.info("user validation : " + resultMap.get("message"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("error in userValidation :"+e.toString());
			resultMap.put("result", "false");
			resultMap.put("status", "0");
			resultMap.put("message", e.toString());
		}
		return resultMap;
	}*/
	/*
	@Transactional
	public LinkedHashMap<String, String> userValidationWeb(UserEntity objUserEntity, HashMap<Integer, Double>loginMap, Logger logger) {
		LinkedHashMap<String, String> resultMap = new LinkedHashMap<String, String>();
		List<UserSubscriptionEntity> subsciptionList = new ArrayList<UserSubscriptionEntity>();
		try{
		UserSubscriptionEntity objSubscriptionEntity = new UserSubscriptionEntity();
		subsciptionList = getUserSubscription(objUserEntity
				.getId());
		Timestamp currentdateTime = Common.getCurrentGMTTimestamp();boolean subscriptionFlag = false;
		if(subsciptionList.size()>0)
		{
			subscriptionFlag = true;
			objSubscriptionEntity = subsciptionList.get(0);
		}
	
			if (subscriptionFlag) {
			if (getUser(objUserEntity).get(0).isIsactive()) {
				if ((objSubscriptionEntity.getValidto().after(currentdateTime) && objSubscriptionEntity
						.getValidfrom().before(currentdateTime))) {
					resultMap.put("result", "true");
					resultMap.put("status", "1");
					resultMap.put("message", "user subscribed");
				} else {
					resultMap.put("result", "false");
					resultMap.put("status", "0");
					resultMap.put("message", "user not subscribed");
				}
				
			} else {
				resultMap.put("result", "false");
				resultMap.put("status", "0");
				resultMap.put("message", "User verification pending");
			}
		}
			else {
				resultMap.put("result", "false");
				resultMap.put("status", "0");
				resultMap.put("message", "User verification & subscription  pending");
			}
			
		
		logger.info("user validation : " + resultMap.get("message"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("error in userValidation :"+e.toString());
			resultMap.put("result", "false");
			resultMap.put("status", "0");
			resultMap.put("message", e.toString());
		}
		return resultMap;
	}
*/	
	
	
}
