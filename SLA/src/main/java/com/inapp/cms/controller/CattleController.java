package com.inapp.cms.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inapp.cms.common.AppZip;
import com.inapp.cms.entity.CattleEntity;
import com.inapp.cms.entity.CattleImageEntity;
import com.inapp.cms.entity.CattleSyncEntity;
import com.inapp.cms.entity.FarmEntity;
import com.inapp.cms.entity.SyncEntity;
import com.inapp.cms.service.CattleManager;
import com.inapp.cms.service.SyncManager;
import com.inapp.cms.service.UserManager;
import com.inapp.cms.utils.Common;
import com.inapp.cms.utils.DateDeserializer;
import com.inapp.cms.utils.DateSerializer;
import com.inapp.cms.utils.FileUpload;
import com.inapp.cms.utils.MessageConstants;

@Controller
@RequestMapping(value = "/cattle")
public class CattleController {

	private static final Logger logger = Logger.getLogger(CattleController.class);
	@Autowired
	private CattleManager cattleManager;
	 
	@Autowired
	 ServletContext context;
	@Autowired
	private UserManager userManager;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private SyncManager syncManager;
	
	@RequestMapping(value = "/getCattleDetailsByFarm", method = RequestMethod.POST)
	public @ResponseBody
	String getCattleDetailsByFarm(@RequestParam("signature") String signature,
			@RequestParam("u_id") String u_id,@RequestParam("farmid") String farm_id,
			@RequestParam("reqData") String reqData,@RequestBody String cattleDetails) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson farmGson = new Gson();
		try {
			map = userManager.getReqDetails(signature, u_id, reqData,MessageConstants.ALL_PERMISSION);
			if (map.get("status").equals("1")) {
				List<CattleEntity> gotDetMap  = cattleManager.getCattleDetailsByFarm(Integer.parseInt(farm_id));
				if(gotDetMap != null && gotDetMap.size() >0){
					HashMap<String,Object> cattleObjMap = new HashMap<String, Object>();
					cattleObjMap.put("cattle_dtls", gotDetMap);
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData", farmGson.toJson(cattleObjMap));

				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
				map.remove("reqData");
				map.remove("u_id");
			}
			
		} catch (Exception e) {
			logger.error("error in getFarmDetailsByUser :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}
		res = farmGson.toJson(map);
		return res.toString();
	}
	
	
	/* this method is used to save and update cattle and images through web */
	@RequestMapping(value = "/saveCattle", method = RequestMethod.POST)
	public @ResponseBody String saveCattle(@RequestParam("signature") String signature,
			@RequestParam("u_id") String u_id,@RequestParam("reqData") String reqData,
			@RequestParam("cattlePrimaryImg") String cattlePrimaryImg,
			@RequestParam(value="updateImages", required=false) String[] updateImages,
			@RequestParam(value="deletedImages", required=false) String[] deletedImages,
			@RequestParam(value="fileData", required=false) MultipartFile[] files) {
		
		logger.info(" ************** saveCattle ************************");
		if (null!=updateImages&&updateImages.length > 0) {
			for (int j = 0; j < updateImages.length; j++) {
				logger.info(" updateImages "+updateImages[j]);

			}
		}
		if (null!=deletedImages&&deletedImages.length > 0) { 
			for (int j = 0; j < deletedImages.length; j++) {
				logger.info(" deletedImages :: --> "+deletedImages[j]);

			}
		}	
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		Gson farmGson = new Gson();
		try {
			map = userManager.getReqDetails(signature, u_id, reqData,MessageConstants.ADMN_OWNR_PERMISSION);
			
			if (map.get("status").equals("1")) {
				GsonBuilder gsonBuilder = new GsonBuilder();
				// This is to convert cattle dob dd-mm-yyy(String) from frontend to timestamp 
				gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
				gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
				Gson gson = gsonBuilder.create();
				CattleEntity ObjCattleEntity = gson.fromJson(reqData, CattleEntity.class);

			//	CattleEntity ObjCattleEntity = farmGson.fromJson(reqData, CattleEntity.class);
				
				ObjCattleEntity.setCreateddt(Common.getCurrentGMTTimestamp()); // for sync
				HashMap<String, Object> cattleDetMap = cattleManager.saveCattleDetails(ObjCattleEntity);
				if(cattleDetMap != null && cattleDetMap.size() >0){
					if(null != deletedImages && deletedImages.length > 0){
						cattleManager.updateDeleteStatus(deletedImages,Integer.parseInt(cattleDetMap.get("cattle_id").toString()));
						try {
							Properties prop = new Properties();
							prop.load(FileUpload.class.getClassLoader().getResourceAsStream(
									"config.properties"));
							String file_path = prop.getProperty("image_filePath");
							for (int j = 0; j < deletedImages.length; j++) {
								File serverFile = new File(file_path + File.separator
										+ "images" + File.separator + deletedImages[j]);
								if(serverFile.exists()){
									serverFile.delete();
								}
							}
						}catch(IOException ex){
							logger.error("IOException in file loading");
						}
						
						
					}
					
					if((null != files && files.length >0)){
						long count = cattleManager.getImageCount(Integer.parseInt(cattleDetMap.get("cattle_id").toString()));
						
						ArrayList<CattleImageEntity> objCattleImgList = FileUpload.uploadFile(files,"images",cattleDetMap.get("cattle_ear_tag_id").toString(),Integer.parseInt(cattleDetMap.get("cattle_id").toString()),cattlePrimaryImg,updateImages,count);
						cattleManager.saveImageUrl(objCattleImgList);
					//for sync	//save data to be deleted to a temporary table for syncing purpose
					}
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData", farmGson.toJson(cattleDetMap));
					

				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in saveCattle :" + e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}

		res = farmGson.toJson(map);
		return res.toString();
	}
	@RequestMapping(value = "/getCattleDetails", method = RequestMethod.POST)
	public @ResponseBody
	String getCattleDetails(@RequestBody String cattleDetails) {
		ArrayList<LinkedHashMap<String, String>> resList = new ArrayList<LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson farmGson = new Gson();
		try {
			map = userManager.getReqDetails(cattleDetails,MessageConstants.ALL_PERMISSION);
			if (map.get("status").equals("1")) {
				CattleEntity ObjCattleEntity = farmGson.fromJson(map.get("reqData"), CattleEntity.class);
				
				ObjCattleEntity = cattleManager.getCattleDetails(ObjCattleEntity);
				if (ObjCattleEntity != null ) {
					HashMap<String,Object> objMap = new HashMap<String, Object>();
					objMap.put("cattleDetails", ObjCattleEntity);
					List<CattleImageEntity> objCattleImgList = cattleManager.getImageUrls(Integer.parseInt(ObjCattleEntity.getCattleid().toString()));
					objMap.put("cattleImageDetails", objCattleImgList);
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData", farmGson.toJson(objMap));

				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
				map.remove("reqData");
				map.remove("u_id");
			}
			
		} catch (Exception e) {
			logger.error("error in getCattleDetails :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}resList.add(map);
		res = farmGson.toJson(map);
		return res.toString();
	}
	

	@RequestMapping(value = "/getImage", method = RequestMethod.GET, produces = "image/jpeg")
    public @ResponseBody
    String downloadFile( @RequestParam("fileUrl") String fileUrl,          
            HttpServletResponse response)
            throws Exception {       
        logger.info("-------------------------/getImage---------------------------------------");
       
        InputStream inputStream  =null;
        Map<String, Object> result = new HashMap<String, Object>();
         Gson gson = new Gson();
         try {           
           
             Properties prop = new Properties();
                try {
                    prop.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
                } catch (IOException ex) {
                    logger.error(ex.getMessage());               
                }
            String parentDir =  prop.getProperty("image_filePath");
                       
            if(fileUrl.isEmpty() || fileUrl.trim().length() <= 0 ){
                
                        
            }           
           
            fileUrl = parentDir+"/images/" +fileUrl;
            
            
            long len = 10468;
            File fileToDownload  = null;
            if(!new File(fileUrl).exists()){
            	fileUrl = context.getRealPath("") + File.separator + "view/common/images/noImage.png";
            	//inputStream = this.getClass().getResourceAsStream("/view/common/images/noImage.png");   
            }
            logger.info("Download path of File : " + fileUrl);   
            	fileToDownload = new File(fileUrl);
                inputStream = new FileInputStream(fileToDownload);
                len = fileToDownload.length();
               
            
            response.setContentType("image/jpeg");
            response.setHeader("Content-Disposition",
                    "attachment; filename=test.jpg");
            response.setHeader("Content-Length", len+"");
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
            
           
        } catch (Exception e) {
            logger.error("Error in file downloading." + e.getMessage(), e);               
            throw new Exception("Error in file downloading." + e.getMessage(), e);
        } finally {
           
        }
        return gson.toJson(result);
    }

	// this method is not using
	@RequestMapping(value = "/saveCattleNoImg", method = RequestMethod.POST)
	public @ResponseBody String saveCattleNoImg(@RequestParam("signature") String signature,
			@RequestParam("u_id") String u_id,@RequestParam("reqData") String reqData
			
			) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		Gson farmGson = new Gson();
		try {
			map = userManager.getReqDetails(signature, u_id, reqData,MessageConstants.ADMN_OWNR_PERMISSION);
			
			if (map.get("status").equals("1")) {
				
			
				GsonBuilder gsonBuilder = new GsonBuilder();
				// This is to convert cattle dob dd-mm-yyy(String) from frontend to timestamp 
				gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
				gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
				//gsonBuilder.registerTypeAdapter(FarmEntity.class, new ClassInstanceCreator<FarmEntity>());
				Gson gson = gsonBuilder.create();
				CattleEntity ObjCattleEntity = gson.fromJson(reqData, CattleEntity.class);

				ObjCattleEntity.setCreateddt(Common.getCurrentGMTTimestamp()); //for sync
				HashMap<String, Object> cattleDetMap = cattleManager.saveCattleDetails(ObjCattleEntity);
				if(cattleDetMap != null && cattleDetMap.size() >0){
					/*if(files.length >0){
						ArrayList<CattleImageEntity> objCattleImgList = FileUpload.uploadFile(files,"images",cattleDetMap.get("cattle_ear_tag_id").toString(),Integer.parseInt(cattleDetMap.get("cattle_id").toString()),cattlePrimaryImg);
						cattleManager.saveImageUrl(objCattleImgList);
					}*/
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData", farmGson.toJson(cattleDetMap));
					

				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in saveCattle :" + e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}

		res = farmGson.toJson(map);
		return res.toString();
	}
	
	
	@RequestMapping(value = "/getCattleDetailsOfAssignedFarm", method = RequestMethod.POST)
	public @ResponseBody
	String getCattleDetailsOfAssignedFarm(@RequestParam("signature") String signature,
			@RequestParam("u_id") String u_id,@RequestParam("farmid") String farm_id,
			@RequestParam("reqData") String reqData,@RequestBody String cattleDetails) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson farmGson = new Gson();
		try {
			map = userManager.getReqDetails(signature, u_id, reqData,MessageConstants.ALL_PERMISSION);
			if (map.get("status").equals("1")) {
				List<HashMap<String, Object>> gotDetList  = cattleManager.getCattleDetailsOfAssignedFarm(Integer.parseInt(u_id));
				if(gotDetList != null && gotDetList.size() >0){
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData", farmGson.toJson(gotDetList));

				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
				map.remove("reqData");
				map.remove("u_id");
			}
			
		} catch (Exception e) {
			logger.error("error in getCattleDetailsOfAssignedFarm :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}
		res = farmGson.toJson(map);
		return res.toString();
	}
	
	@RequestMapping(value = "/getCattleDetailsOfAssignedFarmAndBreedDt", method = RequestMethod.POST)
	public @ResponseBody
	String getCattleDetailsOfAssignedFarmAndBreedDt(@RequestParam("signature") String signature,
			@RequestParam("u_id") String u_id,@RequestParam("breedingdt") String breedingdt,
			@RequestParam("reqData") String reqData,@RequestBody String cattleDetails) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";Gson farmGson = new Gson();
		try {
			map = userManager.getReqDetails(signature, u_id, reqData,MessageConstants.ALL_PERMISSION);
			if (map.get("status").equals("1")) {
				List<HashMap<String, Object>> gotDetList  = cattleManager.getCattleDetailsOfAssignedFarmAndBreedDt(Integer.parseInt(u_id),breedingdt);
				if(gotDetList != null && gotDetList.size() >0){
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData", farmGson.toJson(gotDetList));

				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
				map.remove("reqData");
				map.remove("u_id");
			}
			
		} catch (Exception e) {
			logger.error("error in getCattleDetailsOfAssignedFarm :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}
		res = farmGson.toJson(map);
		return res.toString();
	}
	

	@RequestMapping(value = "/getParentByFarmBirthdt", method = RequestMethod.POST)
	public @ResponseBody
	String getParentByFarmBithdt(@RequestBody String cattleDetails) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
		Gson farmGson = gsonBuilder.create();
		try {
			map = userManager.getReqDetails(cattleDetails,MessageConstants.OWNER_PERMISSION);
			if (map.get("status").equals("1")) {
				CattleEntity ObjCattleEntity = farmGson.fromJson(map.get("reqData"), CattleEntity.class);
				List<HashMap<String, Object>> cattleDetList  = cattleManager.getParentByFarmBithdt(ObjCattleEntity);
				if(cattleDetList != null && cattleDetList.size() >0){
					HashMap<String,Object> cattleObjMap = new HashMap<String, Object>();
					cattleObjMap.put("cattle_dtls", cattleDetList);
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData", farmGson.toJson(cattleObjMap));

				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
				map.remove("reqData");
				map.remove("u_id");
			}
			
		} catch (Exception e) {
			logger.error("error in getFarmDetailsByUser :"+e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}
		res = farmGson.toJson(map);
		return res.toString();
	}
	
	//sync cattle images from desktop to web
	@RequestMapping(value = "/saveCattleImages", method = RequestMethod.POST)
	public @ResponseBody String saveCattleImages(
			@RequestParam(value="earTag", required=false) String earTag,
			@RequestParam(value="signature", required=false) String signature,
			@RequestParam(value="u_id", required=false) String u_id,
			@RequestParam(value="imgData", required=false) MultipartFile[] files,
			@RequestParam(value="deletedImages", required=false) String deletedImage
			) {
		
		logger.info(" ************** saveCattleImages ************************"+deletedImage);
		if(files!=null && files.length>0)
			logger.info("files============================================="+files.length);
		else{
			logger.info("===================================No Files Found==========================");
		}
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		Gson farmGson = new Gson();
		try {
			map = userManager.getReqDetails(signature,u_id,"",MessageConstants.ADMN_OWNR_PERMISSION);
			if (map.get("status").equals("1")) {
			CattleEntity cattle = cattleManager.getParentCattle(earTag);
			if(null !=cattle && null != cattle.getCattleid() && cattle.getCattleid() >0 ){
				//cattleManager.deleteImageUrl(cattle.getCattleid());
				String [] deletedImages = deletedImage.split(",");
				if(null != deletedImages && deletedImages.length > 0){
					cattleManager.updateDeleteStatus(deletedImages, cattle.getCattleid());
					try {
						Properties prop = new Properties();
						prop.load(FileUpload.class.getClassLoader().getResourceAsStream(
								"config.properties"));
						String file_path = prop.getProperty("image_filePath");
						for (int j = 0; j < deletedImages.length; j++) {
							logger.info("deletedImages --->"+j+"===="+deletedImages[j]);
							File serverFile = new File(file_path + File.separator
									+ "images" + File.separator + deletedImages[j]);
							if(serverFile.exists()){
								serverFile.delete();
							}
						}
					}catch(IOException ex){
						logger.error("IOException in file loading");
					}
				}
				ArrayList<CattleImageEntity> objCattleImgList = FileUpload.uploadFile(earTag,cattle.getCattleid(),files);
				cattleManager.saveImageUrl(objCattleImgList);
				map.put("msg", MessageConstants.msg_1);
				map.put("status", "1");
				
			}else{
				map.put("msg", "Cattle Not Synced with server");
				map.put("status", "2");
			}
				
			map.remove("reqData");	
		}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in saveCattleImages :" + e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}

		res = farmGson.toJson(map);
		return res.toString();
	}
	// this method is not using
	@RequestMapping(value = "/saveCattleDesktop", method = RequestMethod.POST)
	public @ResponseBody String saveCattleDesktop(@RequestBody String cattleDetails) {
		
		logger.info(" ************** saveCattle ************************");
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		Gson farmGson = new Gson();
		try {
			
			map = userManager.getReqDetails(cattleDetails,MessageConstants.ADMN_OWNR_PERMISSION);
			if (map.get("status").equals("1")) {
				String reqData = map.get("reqData");
				JSONObject obj = new JSONObject(reqData);
				String cattleInfo = obj.getString("cattle_info");
				CattleSyncEntity ObjCattleSyncEntity = farmGson.fromJson(cattleInfo, CattleSyncEntity.class);
			//	CattleEntity ObjCattleEntity = farmGson.fromJson(reqData, CattleEntity.class);
				CattleEntity ObjCattleEntity = converttoCattleEntity(ObjCattleSyncEntity);
				ObjCattleEntity.setCreateddt(Common.getCurrentGMTTimestamp()); // for sync
				HashMap<String, Object> cattleDetMap = cattleManager.saveCattleDetails(ObjCattleEntity);
				if(cattleDetMap != null && cattleDetMap.size() >0){
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData", farmGson.toJson(cattleDetMap));

				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in saveCattleDesktop :" + e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}

		res = farmGson.toJson(map);
		return res.toString();
	}
	// this method is not using
	private CattleEntity converttoCattleEntity(	CattleSyncEntity objCattleSyncEntity) {
		CattleEntity objCattle = new CattleEntity();
		try{
			logger.info("objCattleSyncEntity "+new Gson().toJson(objCattleSyncEntity));
			if(null != objCattleSyncEntity.getCattle_id() && objCattleSyncEntity.getCattle_id() >0){
				CattleEntity cattle = cattleManager.getParentCattle(objCattleSyncEntity.getCattle_ear_tag_id());
				objCattle.setCattleid(cattle.getCattleid());
			}else{
				objCattle.setCattleid(0);
			}
		//objCattle.setCattleid(objCattleSyncEntity.getCattle_id()!=null?objCattleSyncEntity.getCattle_id():0);
		objCattle.setCattleeartagid(objCattleSyncEntity.getCattle_ear_tag_id());
		objCattle.setCattlelocation(objCattleSyncEntity.getCattle_location());
		
		FarmEntity farm = cattleManager.getFarm(objCattleSyncEntity.getCattle_farm_code());
		objCattle.setFarm(farm);
		objCattle.setCattledob(Common.parseStringDate(objCattleSyncEntity.getCattle_dob_sync()));
		objCattle.setCattlegender(objCattleSyncEntity.getCattle_gender());
		objCattle.setCattlecategory(objCattleSyncEntity.getCattle_category());
		objCattle.setCattlestatus(objCattleSyncEntity.isCattle_status());
		objCattle.setCattlecode(objCattleSyncEntity.getCattle_code());
		objCattle.setCattlecreateddt(Common.getCurrentTimestamp());
		objCattle.setCattlelatt(objCattleSyncEntity.getCattle_latt());
		objCattle.setCattlelong(objCattleSyncEntity.getCattle_long());
		objCattle.setCattlelandmark(objCattleSyncEntity.getCattle_land_mark());
		objCattle.setCattletype(objCattleSyncEntity.getCattle_type());
		CattleEntity buckCattle = cattleManager.getParentCattle(objCattleSyncEntity.getBuck_ear_tag_id());
		objCattle.setBuckcattle(buckCattle);
		CattleEntity doeCattle = cattleManager.getParentCattle(objCattleSyncEntity.getDoe_ear_tag_id());
		objCattle.setDoecattle(doeCattle);
		/*objCattle.setCreateddt(objCattleSyncEntity.getCreated_dt());
		objCattle.setUpdateddt(objCattleSyncEntity.getUpdated_dt());
		objCattle.setDeleteddt(objCattleSyncEntity.getDeleted_dt());
		objCattle.setUniquesynckey(objCattleSyncEntity.getUnique_sync_key());*/
		}catch(Exception e){
			logger.error("error in converttoCattleEntity"+e.getMessage());
		}
		logger.info("objCattle "+new Gson().toJson(objCattle));
		return objCattle;
	}
	
	@RequestMapping(value = "/generateEarTag", method = RequestMethod.POST)
	public @ResponseBody String generateEarTag(@RequestBody String cattleDetails) {
		
		logger.info("====generateEarTag=====");
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		Gson farmGson = new Gson();
		try {
			
			map = userManager.getReqDetails(cattleDetails,MessageConstants.ADMN_OWNR_PERMISSION);
			if (map.get("status").equals("1")) {
				String reqData = map.get("reqData");
				JSONObject obj = new JSONObject(reqData);
				String farmCode = obj.getString("farm_code");
				String earTag = cattleManager.generateEarTag(farmCode);
				if(earTag != null && !earTag.equals("")){
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
					map.put("resData", farmGson.toJson(earTag));

				} else {
					map.put("msg", MessageConstants.msg_2);
					map.put("status", "2");
				}
				map.remove("reqData");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in generateEarTag :" + e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}

		res = farmGson.toJson(map);
		return res.toString();
	}
	
	// this method is not using
	@RequestMapping(value = "/checkCattleSync", method = RequestMethod.POST)
	public @ResponseBody String checkCattleSync(@RequestBody String cattleDetails) {
		
		logger.info("====generateEarTag=====");
		
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String res = "";
		Gson farmGson = new Gson();
		try {
			
			map = userManager.getReqDetails(cattleDetails,MessageConstants.ADMN_OWNR_PERMISSION);
			if (map.get("status").equals("1")) {
				String reqData = map.get("reqData");
				JSONObject obj = new JSONObject(reqData);
				String earTag = obj.getString("earTag");
				CattleEntity cattle = cattleManager.getParentCattle(earTag);
				if(null != cattle && null != cattle.getCattleid() && cattle.getCattleid() >0 ){
					cattleManager.deleteImageUrl(cattle.getCattleid());
					map.put("msg", MessageConstants.msg_1);
					map.put("status", "1");
				}else{
					map.put("msg", "Cattle Not Synced with server");
					map.put("status", "2");
				}
				map.remove("reqData");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in checkCattleSync :" + e.getMessage());
			map.put("msg", MessageConstants.msg_10);
			map.put("status", "10");
		}

		res = farmGson.toJson(map);
		return res.toString();
	}
	
	//Sync web images to desktop
	@RequestMapping(value ="/getNewImages", method = RequestMethod.GET)
	public @ResponseBody String getNewImages(
			@RequestParam(value="signature", required=false) String signature,
			@RequestParam(value="u_id", required=false) String u_id,
			@RequestParam(value="lastSyncDt", required=false) String lastSyncDt,
			@RequestParam(value="farms", required=false) List<String> farms)
			throws Exception {
		logger.info("-------------------------/getImages---------------------------------------"+lastSyncDt);
		InputStream inputStream = null;
		String fileUrl = "";
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		Gson syncGson = new Gson();
		

		try {
			List<Integer> newFarmIdList = null;
				SyncEntity objSyncEntity = new SyncEntity();
				objSyncEntity.setLastSyncDt(lastSyncDt);
				Properties prop = new Properties();
				try {
					prop.load(this.getClass().getClassLoader()
							.getResourceAsStream("config.properties"));
				} catch (IOException ex) {
					logger.error(ex.getMessage());
				}
				String parentDir = prop.getProperty("image_filePath");
				fileUrl = parentDir + "downloadimages";
				File dir = new File(fileUrl);
				if(dir.exists()){
					logger.info("dir exist");
					deletefile(dir);
				}
				dir.mkdir();

				// get url and copy all images
				/*List<Integer> farmIdList = syncManager
						.getFarmIdsAssignedByUserId(
								Integer.parseInt(u_id), "OWNER");*/
				List<Integer> farmIdList = syncManager.getfarmsByUserId(Integer.parseInt(u_id), "OWNER", 1, farms);
				if(farms!=null && farms.size()>0){
					newFarmIdList = syncManager.getfarmsByUserId(Integer.parseInt(u_id), "OWNER", 0, farms);
				}
				objSyncEntity.setUserId(Integer.parseInt(u_id));
				List<HashMap<String, Object>> listSync = syncManager
						.getImageDetails(objSyncEntity, farmIdList, newFarmIdList);
					
					_copyImages(listSync,fileUrl,parentDir);
					
					// zip it
					String zipFolder = fileUrl + File.separator+"CattleImages.zip";
					AppZip.zipAllFiles(zipFolder, fileUrl);

					long len = 10468;
					File fileToDownload = null;

					logger.info("Download path of File : " + zipFolder);
					fileToDownload = new File(zipFolder);
					inputStream = new FileInputStream(fileToDownload);
					len = fileToDownload.length();

					response.setContentType("application/octet-stream");
					response.setHeader("Content-Disposition",
							"attachment; filename=CattleImages.zip");
					response.setHeader("Content-Length", len + "");
					IOUtils.copy(inputStream, response.getOutputStream());
					response.flushBuffer();
					inputStream.close();
			

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in file downloading." + e.getMessage(), e);
			throw new Exception("Error in file downloading." + e.getMessage(),
					e);
		} finally {

		}
		
		return "";
	}

	private void _copyImages(List<HashMap<String, Object>> listSync,
			String fileUrl, String parentDir) {
		String image_url = "";
		HashMap<String, Object> imageMap = null;
		String farm_code = "";
		String fileName = "";
		if(null != listSync){
			for (int i =0; i<listSync.size() ;i++){
				 imageMap = (HashMap<String, Object>)listSync.get(i);
				 image_url = imageMap.get("cattle_image_url").toString();
					//FARM-94/ET-397_6.jpg
				 farm_code = image_url.split("/")[0];
				 fileName = image_url.split("/")[1];
				 File dir = new File(fileUrl+File.separator+farm_code);
				 if(!dir.exists()){
					 dir.mkdir();
				 }
				 File srcFile  = new File(parentDir+"/images/"+farm_code+File.separator+fileName);
				 File destFile = new File(dir+File.separator+fileName);
				try {
					if(srcFile.exists()){
						FileUtils.copyFile(srcFile, destFile);
					}
					
				} catch (IOException e) {
					logger.error("Error in _copyImages method"+e.getMessage());
				}
			}
		}
		String[] subNote = (new File(fileUrl)).list();
		if(null == subNote || subNote.length == 0){
			File folder = new File(fileUrl+File.separator+"noImg");
    		folder.mkdir();
			File file  = new File(folder+File.separator+"noImg.jpg");
			try {
				file.createNewFile();
			} catch (IOException ex) {
				logger.error("exception in creating file"+ex.getMessage());
			}
		}
	}
	private void deletefile(File node){
		if(node.isFile()){
			node.delete();
		}
		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				deletefile(new File(node, filename));
			}
			node.delete();
		}
	}
}
