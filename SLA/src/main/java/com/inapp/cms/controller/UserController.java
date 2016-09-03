package com.inapp.cms.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.utils.Common;
import com.app.utils.EmailUser;
import com.app.utils.MessageConstants;
import com.app.utils.RandomString;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.inapp.cms.common.Constants;
import com.inapp.cms.common.Response;
import com.inapp.cms.entity.LoginEntity;
import com.inapp.cms.entity.UserEntity;
import com.inapp.cms.json.UserRegistrationJson;
import com.inapp.cms.service.UserManager;

/**
 * @author Jinesh George
 */

@Controller
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	public static HashMap<Integer, Double> loginMap = new HashMap<Integer, Double>();
	@Autowired
	private UserManager userManager;
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String UserLogin(@ModelAttribute(value = "login") LoginEntity login,
			BindingResult result) {
		return	"redirect:view/login.html";
	}
/**Fetch User Details
 * 
 * @param userId, signature
 * @return userdetails
 */
	@RequestMapping(value = "/userdetails", method = RequestMethod.POST)
	public @ResponseBody
	String listUsers(@RequestBody String s) {
		String res = "";
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		Gson Obj = new Gson();
		try {
			UserEntity objUserEntity = new UserEntity();
			UserRegistrationJson objUserRegistrationJson =  Obj
					.fromJson(s, UserRegistrationJson.class);
			logger.info("login user "
					+ objUserRegistrationJson.getId());
			objUserEntity.setId(Integer.parseInt(objUserRegistrationJson.getId().trim()));
			List<UserEntity> userlist = userManager.getUser(objUserEntity);
		}catch(Exception e){
			
		}
		resList.add(map);
		res = Obj.toJson(resList);
		return res.toString();
	}
/**User Login
 * 
 * @param username and password
 * @return status, userDetails, message, signature
 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody
	String login(@RequestBody String userDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson userlogin = new Gson();
		try {
			UserEntity objUser = userlogin.fromJson(userDetails, UserEntity.class);
			logger.info("login user "+ objUser.getUsername());
			String password = "";
			password = objUser .getPassword().trim();
			HashMap<String, Object> userDetMap = userManager.getUserDetails(objUser);
			if(userDetMap != null && userDetMap.size() >0){
				if(null != userDetMap.get("user_password") && Common.decoder(userDetMap.get("user_password").toString()).equals(password)){
				if(null !=  userDetMap.get("user_temp_password") && userDetMap.get("user_temp_password").toString().trim().length()>0){
					objUser.setId(Integer.parseInt(userDetMap.get("user_id").toString()));
					objUser.setNewpassword(userDetMap.get("user_password").toString());
					objUser.setUpdateddt(Common.getCurrentGMTTimestamp());
					userManager.updatePassword(objUser);
				}
				userDetMap.remove("user_password");
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				objUser.setId(Integer.parseInt(userDetMap.get("user_id").toString()));
				map.put("signature", Common.addSignature(objUser));
				map.put("resData",userlogin.toJson(userDetMap));
				}else if(null != userDetMap.get("user_temp_password") && Common.decoder(userDetMap.get("user_temp_password").toString()).equals(password)){
					userDetMap.remove("user_temp_password");
					userDetMap.remove("user_password");
					map.put("msg", MessageConstants.msg_5);
					map.put("status", "5");
					objUser.setId(Integer.parseInt(userDetMap.get("user_id").toString()));
					map.put("signature", Common.addSignature(objUser));
					map.put("resData",userlogin.toJson(userDetMap));
					
				}else{
					map.put("msg", MessageConstants.msg_11);
					map.put("status", "0");
				}
				
			}else{
				map.put("msg", MessageConstants.msg_0);
				map.put("status", "0");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in login :"+e.toString());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = userlogin.toJson(map);
		return res.toString();
	}

/**
 * Register a user
 * @param email, password.
 * @return userId,status,message,signature
 */
	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	public @ResponseBody
	String addUser(@RequestBody String userDetails) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";String id = "";String userData ="";
		Gson useradd = new Gson();
		String userValid = "true";
		try {
			Properties prop = new Properties();
			RandomString s = new RandomString(5);
			String emailcode = "";
			emailcode = s.nextString(); boolean signatureStatus = true;
			try {
				// load a properties file
				prop.load(EmailUser.class.getClassLoader().getResourceAsStream(
						"config.properties"));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in adduser :"+e.toString());
			map.put("error", e.toString());
			map.put("status", "0");
		}
		res = useradd.toJson(map);
		return res.toString();
	}
/**
 * user forgot his password
 * @param emailid
 * @return status,message
 */
	@RequestMapping(value = "/forgotpwd", method = RequestMethod.POST)
	public @ResponseBody
	String forgotPassword(@RequestBody String userDetails) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";String id="0";String userData = "";
		String subject = "";
		String msgText = "";Gson forgotPwd = new Gson();
		try {
			logger.info("forgot password ");
			UserRegistrationJson objUserRegistrationJson =  forgotPwd
					.fromJson(userDetails, UserRegistrationJson.class);
			List<UserEntity> userList = userManager
					.getUserDetailsByEmail(objUserRegistrationJson.getEmail()
							.trim());
			UserEntity objEntity = new UserEntity();

			RandomString s = new RandomString(5);
			// encode password
			String encodedPass = "";
			String tempPass = "";
			//userData = objUserRegistrationJson.getEmail()
					//.trim() + "##" +" " + "##" +objUserRegistrationJson.getPushtoken(); //for log
			if (userList.size() > 0) {
				objEntity = userList.get(0);
			//	List<UserDetailsEntity> userDetList = userManager.getUserDetails(objEntity);
				tempPass = s.nextString();
				try {
					encodedPass = Common.encoder(tempPass);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				//objEntity.setTemppassword(encodedPass);
				userManager.addUser(objEntity);
				subject = "iFynder Temperory Password Request";
				msgText = "Your password is : " + tempPass;
				try {
				//	String fName = userDetList.get(0).getFname()==null ?"":userDetList.get(0).getFname();
			  	//	String lName  = userDetList.get(0).getLname()==null ?"":userDetList.get(0).getLname();
				//	msgText = HtmlReader.htmlReader(fName+" "+lName, objEntity.getUsername(), msgText, "password", tempPass);
					EmailUser.emailUser(objEntity.getUsername(), subject,
							msgText);
					map.put("status", "1");
					map.put("message", "Password sent to your mail id");
				} catch (Exception e) {
					map.put("status", "0");
					map.put("message", "Mail sending failed");
				}
				id = objEntity.getId().toString();
				//userData = objEntity.getUsername() + "##" + userDetList.get(0).getFname()+" "+
						//userDetList.get(0).getLname()+ "##" +objEntity.getUserDeviceToken(); //for log
			} else {
				map.put("status", "0");
				map.put("message", "No User registered with given mail id");
			}} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in forgot password : "+e.toString());
			map.put("status", "0");
			map.put("message", e.toString());
		}
		//LogCreator.FileCreater(id, "", "forgotpassword", map.get("message"), map.get("status"), userData);
		res = forgotPwd.toJson(map);
		return res.toString();
	}
/**
 * resend email code
 * @param userId, signature
 * @return status, message
 */
	@RequestMapping(value = "/resendcode", method = RequestMethod.POST)
	public @ResponseBody
	String resendCode(@RequestBody String userDetails) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";String id="0";String userData ="";
		Gson resendCode = new Gson();
		try {
			Properties prop = new Properties();

			try {
				// load a properties file
				prop.load(EmailUser.class.getClassLoader().getResourceAsStream(
						"config.properties"));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			logger.info("resend code ");
			UserRegistrationJson objUserRegistrationJson =  resendCode
					.fromJson(userDetails, UserRegistrationJson.class);
			UserEntity objEntity = new UserEntity();
	//		UserDetailsEntity objDetailsEntity = new UserDetailsEntity();
			objEntity.setId(Integer.parseInt(objUserRegistrationJson.getId()));
			userManager.updateUserOnlne(objEntity.getId(), Common.getCurrentGMTTimestamp());//useronline
			List<UserEntity> userList = userManager.getUser(objEntity);
			boolean signatureStatus = true;
			if (objUserRegistrationJson.getSignature() != null) {
				if (loginMap.containsKey(objEntity.getId())) {
					if (!loginMap
							.get(objEntity.getId())
							.toString()
							.equals(objUserRegistrationJson.getSignature()
									.trim())) {
						map.put("id", objEntity.getId() + "");
						map.put("result", "false");
						map.put("status", "9");
						map.put("message", "wrong signature");
						signatureStatus = false;
					}
				}
			}
			if (signatureStatus) {
				if (userList.size() > 0) {
					RandomString s = new RandomString(5);
					String emailcode = s.nextString();
					objEntity = userList.get(0);
				//	objDetailsEntity = userManager.getUserDetails(objEntity)
							//.get(0);
					String subject = prop.getProperty("verification");
					String msgText = prop.getProperty("verificationcode")
							+ emailcode;
				//	objDetailsEntity.setEmailcode(emailcode);
				//	userManager.addUserDetails(objDetailsEntity);
					try {
					//	String fName = objDetailsEntity.getFname()==null ?"":objDetailsEntity.getFname();
				  	//	String lName  = objDetailsEntity.getLname()==null ?"":objDetailsEntity.getLname();
					//	msgText = HtmlReader.htmlReader(fName+" "+lName, objEntity.getUsername(), msgText,"verify", emailcode);
						EmailUser.emailUser(objEntity.getUsername().trim(),
								subject, msgText);
						map.put("id", objEntity.getId() + "");
						map.put("result", "success");
						map.put("status", "1");
						map.put("message",
								"Verification Code sent to your mail id");
					} catch (Exception e) {
						map.put("result", "failed");
						map.put("status", "0");
						map.put("message", "Mail sending failed");
						e.printStackTrace();
					}
					//userData = objEntity.getUsername() + "##" + objDetailsEntity.getFname()+" "+
						//	objDetailsEntity.getLname()+ "##" +objEntity.getUserDeviceToken(); //for log
				} else if (userList.size() == 0) {
					map.put("result", "failed");
					map.put("status", "0");
					map.put("message", "No User details found");
				}
			}
			if(map.get("id")!=null)
				id = map.get("id");
			//LogCreator.FileCreater(id, "", "resendemailcode", map.get("message"), map.get("status"), userData);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in resendcode : " + e.toString());
			map.put("status", "0");
			map.put("message", e.toString());
		}
		res = resendCode.toJson(map);
		return res.toString();
	}
/**
 * change password
 * @param userId,oldpassword,newpassword,signature
 * @return status, message
 */
	@RequestMapping(value = "/changepwd", method = RequestMethod.POST)
	public @ResponseBody
	String changePwd(@RequestBody String userDetails) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";String id ="";String userData = "";
		Gson changePwd = new Gson();
		try {
			logger.info("change password ");
			UserRegistrationJson objUserRegistrationJson =  changePwd
					.fromJson(userDetails, UserRegistrationJson.class);
			UserEntity objEntity = new UserEntity();
			objEntity.setId(Integer.parseInt(objUserRegistrationJson.getId()));
			map = userManager.userValidation(objUserRegistrationJson
					.getSignature().trim(), objEntity, loginMap, logger);
			List<UserEntity> userList = userManager.getUser(objEntity);
			String decodedPass = "";
			String subject = "iFynder Change Password Request";
			String msgText = "SuccessFully Changed Your Password";
			if (userList.size() > 0 && map.get("result").equals("true")) {
			//	List<UserDetailsEntity> userDetailList = userManager.getUserDetails(userList.get(0));
				objEntity = userList.get(0);
				try {
					decodedPass = Common.decoder(objEntity.getPassword());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if (decodedPass.equals(objUserRegistrationJson.getPassword()
						.trim())) {
					// encode password
					String encodedPass = objUserRegistrationJson.getPassword()
							.trim();
					try {
						encodedPass = Common.encoder(objUserRegistrationJson
								.getNewpassword().trim());
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					objEntity.setPassword(encodedPass);
					//objEntity.setUser_online(Common.getCurrentGMTTimestamp());//useronline
					userManager.addUser(objEntity);
					map.put("id", objEntity.getId() + "");
					map.put("result", "success");
					map.put("status", "1");
					map.put("message", "Password Successfully Changed");
					try {
					//	String fName = userDetailList.get(0).getFname()==null ?"":userDetailList.get(0).getFname();
				  	//	String lName  = userDetailList.get(0).getLname()==null ?"":userDetailList.get(0).getLname();
					//	msgText = HtmlReader.htmlReader(fName+" "+lName, objEntity.getUsername(), msgText, "password", "");
						EmailUser.emailUser(objEntity.getUsername(), subject,
								msgText);
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(e);
						map.put("status", "0");
						map.put("message", "Email not sent");
					}
				} else {
					map.put("id", objEntity.getId() + "");
					map.put("result", "failed");
					map.put("status", "0");
					map.put("message", "Wrong Password");
				}
			} else if (userList.size() == 0 && map.get("result").equals("true")) {
				map.put("id", objEntity.getId() + "");
				map.put("result", "failed");
				map.put("status", "0");
				map.put("message", "No User Details Found");
			}
			if(userList.size()>0)
			{
			//	List<UserDetailsEntity> userDetailList = userManager.getUserDetails(userList.get(0));
				//userData = objEntity.getUsername() + "##" + userDetailList.get(0).getFname()+" "+
						//userDetailList.get(0).getLname()+ "##" +objEntity.getUserDeviceToken(); //for log
			}
			if(map.get("id")!=null)
				id = map.get("id");
			//LogCreator.FileCreater(id, "", "changepassword", map.get("message"), map.get("status"), userData);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in changepwd :" + e.toString());
			map.put("status", "0");
			map.put("message", e.toString());
		}
		res = changePwd.toJson(map);
		return res.toString();
	}
/**
 * reset password
 * @param userId, newpassword, signature
 * @return status, message
 */
	@RequestMapping(value = "/resetpwd", method = RequestMethod.POST)
	public @ResponseBody
	String resetPwd(@RequestBody String userDetails) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson changePwd = new Gson();String id = "0";String userData = "";
		try {
			logger.info("resetPwd ");
			UserRegistrationJson objUserRegistrationJson =  changePwd
					.fromJson(userDetails, UserRegistrationJson.class);
			UserEntity objEntity = new UserEntity();
			objEntity.setId(Integer.parseInt(objUserRegistrationJson.getId()));
			map = userManager.userValidation(objUserRegistrationJson
					.getSignature().trim(), objEntity, loginMap, logger);
			List<UserEntity> userList = userManager.getUser(objEntity);
			if (userList.size() > 0 && map.get("result").equals("true")) {
				objEntity = userList.get(0);
				// encode password
				String encodedPass = "";
				try {
					encodedPass = Common.encoder(objUserRegistrationJson
							.getNewpassword().trim());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				objEntity.setPassword(encodedPass);
				//objEntity.setTemppassword(null);
				//objEntity.setUser_online(Common.getCurrentGMTTimestamp());//useronline
				userManager.addUser(objEntity);
				map.put("id", objEntity.getId() + "");
				map.put("result", "success");
				map.put("status", "1");
				map.put("message", "Password Successfully Changed");
			} else if (userList.size() == 0 && map.get("result").equals("true")) {
				map.put("id", objEntity.getId() + "");
				map.put("result", "failed");
				map.put("status", "0");
				map.put("message", "No User Details Found");
			}
			if(userList.size()>0)
			{
			//	List<UserDetailsEntity> userDetailList = userManager.getUserDetails(userList.get(0));
				//userData = objEntity.getUsername() + "##" + userDetailList.get(0).getFname()+" "+
					//	userDetailList.get(0).getLname()+ "##" +objEntity.getUserDeviceToken(); //for log
			}
			if(map.get("id")!=null)
				id = map.get("id");
			//LogCreator.FileCreater(id, "", "resetpassword", map.get("message"), map.get("status"), userData);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in reset password : " + e.toString());
			map.put("status", "0");
			map.put("message", e.toString());
		}res = changePwd.toJson(map);
		return res.toString();
	}
/**
 * search users and their connection with a user
 * @param userId, set of emails,set of phone numbers,signature
 * @return userdetails with isadded status, message
 */
	@RequestMapping(value = "/searchusers", method = RequestMethod.POST)
	public @ResponseBody
	String searchUsers(@RequestBody String s) {
		String res = "";
		Gson Obj = new Gson();
		logger.info("search users");
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		try {
			JsonParser parser = new JsonParser();
			boolean signatureStatus = true;
			JsonObject obj = parser.parse(s).getAsJsonObject();
			JsonArray jArray = (JsonArray) obj.get("contacts");
			int userId = 0;
			String signature = "";
			if (obj.get("user_id") != null) {
				userId = Integer.parseInt(obj.get("user_id").toString().trim()
						.replace("\"", ""));
				userManager.updateUserOnlne(userId, Common.getCurrentGMTTimestamp()); //useronline
			}
			if (obj.get("signature") != null) {
				signature = obj.get("signature").toString().trim()
						.replace("\"", "");
			}
			if (loginMap.containsKey(userId)) {
				if (!loginMap.get(userId).toString().equals(signature)) {
					map.put("id", userId + "");
					map.put("result", "false");
					map.put("status", "9");
					map.put("message", "wrong signature");
					resList.add(map);
					signatureStatus = false;
					res = Obj.toJson(resList).toString();
				}
			} else {
				map.put("id", userId + "");
				map.put("result", "false");
				map.put("status", "9");
				map.put("message", "User Not Log In");
				resList.add(map);
				signatureStatus = false;
				res = Obj.toJson(resList).toString();
			}
			List<Object> list = userManager.getUserDetailsByEmail(jArray,
					userId);
			if (list.size() > 0 && signatureStatus) {
				res = Obj.toJson(list).toString();
			} else if (list.size() == 0 && signatureStatus == true) {
				map.put("message", "no details found");
				map.put("status", "0");
				resList.add(map);
				res = Obj.toJson(resList).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in searchusers :" + e.toString());
			map.put("message", e.toString());
			map.put("status", "0");
			resList.add(map);
			res = Obj.toJson(resList).toString();
		}
		return res;
	}
	
/**
 * logout a user
 * @param userId, signature
 * @return status, message
 * @throws IOException
 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public @ResponseBody
	 String logout(@RequestBody String loginOutInfo) throws IOException {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String response = "";Gson logout = new Gson();
		int id = 0;
		try{
			map = userManager.getReqDetails(loginOutInfo,MessageConstants.ALL_PERMISSION);
			if(map.get("status").equals("1")){
				 id = Integer.parseInt(map.get("u_id").toString()) ;
					 //Common.loginMap.remove(id);
					 map.put("status", "1");
					 map.put("msg", MessageConstants.msg_1);
			}else{
				map.put("status", "1");
				 map.put("msg", MessageConstants.msg_1);
			}
		//LogCreator.FileCreater(id, "", "logout", map.get("message"), map.get("status"), userData);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			map.put("status", "0");
			map.put("error in logout", e.toString());
		}
		response = logout.toJson(map);
		return response.toString();
	}
/**
 * checks password
 * @param type
 * @param objEntity
 * @return status, message
 */
	public LinkedHashMap<String, String> CheckPassword(String type,
			UserEntity objEntity) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		try{
	//	map.put("signature", addSignature(objEntity))  ;
		if (type.equals("login")) {
			/*if(objEntity.getTemppassword()!=null)
				{objEntity.setTemppassword(null);
				userManager.addUser(objEntity); //clears temperory password since he login with old password
				}*/
			map.put("message", "user valid");
			map.put("status", "1");
		} else {
			map.put("message", "reset password");
			map.put("status", "2");
		}
		if(objEntity!=null && !objEntity.isIsactive())
		{
			map.put("status", "3");
			map.put("message", "Verification pending");
		}
		logger.info("CheckPassword : " + map.get("message"));
		}catch(Exception e)
		{
			e.printStackTrace();
			logger.error("error in check password :"+e.toString());
			map.put("message", e.toString());
			map.put("status", "0");
		}
		return map;
	}
	
	
	@RequestMapping(value="/checkusername", method = RequestMethod.POST)
	public @ResponseBody String checkUsername(@RequestBody String data) {
		logger.info("--------------------checkusername--------------------------------");
		logger.info("data : " + data);
		Response response = new Response();
		Gson gson = new Gson();
		try {				
			JSONObject jsonObject = new JSONObject(data);
			boolean isUserNameExist  = userManager.isUserNameExist( jsonObject.getString("username"));		
			if(isUserNameExist){
				response.put(Constants.STATUS, Constants.USER_EXIST);
				response.put(Constants.MESSAGE, "User already exists.");
			}else{
				response.put(Constants.STATUS, Constants.SUCCESS);
				response.put(Constants.MESSAGE, Constants.SUCCESS_MESSAGE);
			}			
		}catch(Exception e){
			response.put(Constants.STATUS, Constants.FAILED);
			response.put(Constants.MESSAGE, Constants.FAILED_MESSAGE);
			logger.error("Error in checkUsername" , e);
		}
		return new Gson().toJson(response).toString();
	}
	
	@RequestMapping(value="/checkEmail", method = RequestMethod.POST)
	public @ResponseBody String checkEmail(@RequestBody String data) {
		logger.info("--------------------checkEmail--------------------------------");
		logger.info("data : " + data);
		Response response = new Response();
		try {				
			JSONObject jsonObject = new JSONObject(data);
			String uId = "";
			if(jsonObject.has("u_id")){
				uId = jsonObject.getString("u_id");
			}
			boolean isEmailExist  = userManager.isemailExist( jsonObject.getString("email"),uId);		
			if(isEmailExist){
				response.put(Constants.STATUS, Constants.USER_EXIST);
				response.put(Constants.MESSAGE, "Email already exists.");
			}else{
				response.put(Constants.STATUS, Constants.SUCCESS);
				response.put(Constants.MESSAGE, Constants.SUCCESS_MESSAGE);
			}			
		}catch(Exception e){
			response.put(Constants.STATUS, Constants.FAILED);
			response.put(Constants.MESSAGE, Constants.FAILED_MESSAGE);
			logger.error("Error in checkUsername" , e);
		}
		return new Gson().toJson(response).toString();
	}
	
	
/**
 * addsignature
 * @param objEntity
 * @return map
 */
	/*private String addSignature(UserEntity objEntity) {
		double random = 0.0;
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		int min = 1000;
		int max = 100000;

		random = (Math.random() * (max - min));

		while (!loginMap.containsValue((random))) {
			loginMap.put(objEntity.getId(),random);
		}
		map.put("id", objEntity.getId().toString());
		map.put("signature", random + "");
		map.put("status", "success");
		//return map;
		return random + "";
	}
*/
}

