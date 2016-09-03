package com.inapp.cms.service;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.inapp.cms.entity.LoginEntity;
import com.inapp.cms.entity.UserEntity;
/**
 * @author Jinesh George
 */
public interface UserManager {
	public void addUser(UserEntity user);
	public List<UserEntity> getUser(UserEntity user);
	
	public List<UserEntity> userLogin(LoginEntity login);
    public List<UserEntity> getAllUsers();
    public void deleteUser(Integer userId);
	
	public LinkedHashMap<String, String> userValidation(String signature,
			UserEntity objUserEntity, HashMap<Integer, Double>  loginMap, Logger logger);
	public LinkedHashMap<String, String> userValidationWeb(UserEntity objUserEntity, HashMap<Integer, Double>  loginMap, Logger logger);
	public  List<Map<String,Object>>  getHostDetailsByUserId(int userID, int requestedUserID) throws Exception;
	public List<Map<String, Object>> getClientDetailsByUserId(int userID, int requestedUserID ) throws Exception;
	public int updateUserMappingStatus(int hostID, int clientID,
			int mappingStatus, int inviteeMode) throws Exception;
	public List<Map<String, Object>> getClientTrackByUserId(int userID) throws Exception;
	public List<Map<String, Object>> getHostTrackByUserId(int userID)  throws Exception;
	public String saveMultipleFiles(InputStream inputStream, String filePath, String fileName, int userID) throws Exception;
	public int getHostIdByUserID(int userID) throws Exception;
	public int getClientIdByUserID(int userID)throws Exception;	
	public Map<String, Object> getUserDetails(int userID)throws Exception;
	public void logout(String string);
	public void updateUserOnlne(int userId, Timestamp dateTime);
	public int getClientStatus(int clientID)throws Exception;
	public void cleariPhonePushToken(String string) throws Exception;
	public List<LinkedHashMap<String, String>> getInvitationTypeAndStatus(int fromUserId, int toUserId);
	public boolean isClientAccepted(int clientID) throws Exception;
	public List<UserEntity> getUserDetailsBySocialAc(Integer acType, String acId);
	public int updateSocialLinks(int userID, int socilType, String socialID)  throws Exception;
	public List<Object> getUserDetailsBySocialAcIds(int actype, String socialacids, int userId);
	public void saveOrUpdateNotificationInterval(int hostUserId,
			int clientUserId, float timeInterval)throws Exception;
	public double getNotificationInterval(int hostId,
			int clientUserId)throws Exception;
	public List<UserEntity> getUserDetailsByEmail(String trim);
	public List<Object> getUserDetailsByEmail(JsonArray jArray, int userId);
	public HashMap<String, Object> getUserDetails(UserEntity userObj);
	public boolean isUserNameExist(String string) throws Exception;
	LinkedHashMap<String, String> getReqDetails(String reqJson,
			String[] permissionRoles);
	LinkedHashMap<String, String> getReqDetails(String signature, String u_id,
			String reqData, String[] permissionRoles);
	public void updatePassword(UserEntity objUserEntity) throws Exception;
	public UserEntity validateMail(UserEntity objUser);
	public boolean isemailExist(String email,String uId);
	
}
