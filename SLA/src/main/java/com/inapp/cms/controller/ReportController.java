package com.inapp.cms.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.utils.DateDeserializer;
import com.app.utils.DateSerializer;
import com.app.utils.MessageConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inapp.cms.entity.CattleEntity;
import com.inapp.cms.entity.MilkProductionEntity;
import com.inapp.cms.entity.ReportEntity;
import com.inapp.cms.service.ReportManager;
import com.inapp.cms.service.UserManager;


@Controller
@RequestMapping(value = "/report")
public class ReportController {
	private static Logger logger = Logger.getLogger(ReportController.class);
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private ReportManager reportManager;
	
	@Autowired
	 ServletContext context;
	
	
	@RequestMapping(value = "/getMilkProdDetails", method = RequestMethod.POST)
	public @ResponseBody
	String milkProdDetails(@RequestBody String reportDetails) {
		String res = "";
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
		Gson surgeryGson = gsonBuilder.create();
		Gson resGson = new Gson();
		try {
			map = userManager.getReqDetails(reportDetails,MessageConstants.OWNER_PERMISSION);
			if(map.get("status").equals("1")){
				ReportEntity reportObj = surgeryGson.fromJson(map.get("reqData"),ReportEntity.class);
				List<MilkProductionEntity> returnList = reportManager.getMilkProdData(reportObj);
				if(null != returnList && returnList.size()>0){
					map.put("status", "1");
					map.put("msg", MessageConstants.msg_1);
					map.put("resData", resGson.toJson(returnList));
				}else{
					map.put("status", "2");
					map.put("msg", MessageConstants.msg_2);
				}
			}
			map.remove("reqData");
		}catch(Exception e){
			map.put("status", "10");
			map.put("msg", MessageConstants.msg_10);
			logger.error("Error in Milk Production Report"+e.getMessage());
		}
		res = resGson.toJson(map);
		return res.toString();
	}

	@RequestMapping(value = "/getHealthCareDetails", method = RequestMethod.POST)
	public @ResponseBody
	String healthCareDetails(@RequestBody String reportDetails) {
		String res = "";
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
		Gson surgeryGson = gsonBuilder.create();
		Gson resGson = new Gson();
		try {
			map = userManager.getReqDetails(reportDetails,MessageConstants.OWNER_PERMISSION);
			if(map.get("status").equals("1")){
				ReportEntity reportObj = surgeryGson.fromJson(map.get("reqData"),ReportEntity.class);
				List<Object[]> returnList = reportManager.getHealthCareData(reportObj);
				if(null != returnList && returnList.size()>0){
					map.put("status", "1");
					map.put("msg", MessageConstants.msg_1);
					map.put("resData", resGson.toJson(returnList));
				}else{
					map.put("status", "2");
					map.put("msg", MessageConstants.msg_2);
				}
			}
			map.remove("reqData");
		}catch(Exception e){
			map.put("status", "10");
			map.put("msg", MessageConstants.msg_10);
			logger.error("Error in HealthCareDetails Report"+e.getMessage());
		}
		res = resGson.toJson(map);
		return res.toString();
	}
	@RequestMapping(value = "/getCattleDetails", method = RequestMethod.POST)
	public @ResponseBody
	String CattleDetails(@RequestBody String reportDetails) {
		String res = "";
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
		Gson surgeryGson = gsonBuilder.create();
		Gson resGson = new Gson();
		try {
			map = userManager.getReqDetails(reportDetails,MessageConstants.OWNER_PERMISSION);
			if(map.get("status").equals("1")){
				ReportEntity reportObj = surgeryGson.fromJson(map.get("reqData"),ReportEntity.class);
				reportObj.setUserId(Integer.parseInt(map.get("u_id")));
				List<CattleEntity> returnList = reportManager.getCattleDtls(reportObj);
				System.out.println("Result is"+resGson.toJson(returnList));
				if(null != returnList && returnList.size()>0){
					map.put("status", "1");
					map.put("msg", MessageConstants.msg_1);
					map.put("resData", resGson.toJson(returnList));
				}else{
					map.put("status", "2");
					map.put("msg", MessageConstants.msg_2);
				}
			}
			map.remove("reqData");
		}catch(Exception e){
			map.put("status", "10");
			map.put("msg", MessageConstants.msg_10);
			logger.error("Error in CattleDetails Report"+e.getMessage());
		}
		res = resGson.toJson(map);
		return res.toString();
	}
	
	@RequestMapping(value = "/exportFuncionality", method = RequestMethod.POST)
	public @ResponseBody String   ExportController(HttpServletResponse response,@RequestBody String reportDetails) {
		
		String res = "";
		String exportFileName=null;
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Timestamp.class, new DateDeserializer());
		Gson surgeryGson = gsonBuilder.create();
		Gson resGson = new Gson();
		try {
			map = userManager.getReqDetails(reportDetails,MessageConstants.OWNER_PERMISSION);
			if(map.get("status").equals("1")){
				ReportEntity reportObj = surgeryGson.fromJson(map.get("reqData"),ReportEntity.class);
				reportObj.setUserId(Integer.parseInt(map.get("u_id")));
				
				System.out.println(" reportObj Type --> "+reportObj.getType());
					if("goat-report".equals(reportObj.getType())){
						 exportFileName=exportGoatReport(reportObj);
					}else if("milk-production".equals(reportObj.getType())){
						exportFileName=exportMilkProductionReport(reportObj);
					}
			
				if(exportFileName!=null){
					map.put("status", "1");
					map.put("msg", MessageConstants.msg_1);
					map.put("resData", resGson.toJson(exportFileName));
				}else{
					map.put("status", "2");
					map.put("msg", MessageConstants.msg_2);
				}
		     	
			}	
			
			map.remove("reqData");
		}catch(Exception e){
			e.printStackTrace();
			map.put("status", "10");
			map.put("msg", MessageConstants.msg_10);
			logger.error("Error in CattleDetails Report"+e.getMessage());
		}
		
		res = resGson.toJson(map);
		
		return res.toString();
		
	}
	
	
	private String exportMilkProductionReport(ReportEntity reportObj) {
		// TODO Auto-generated method stub
		String fileName = null;
		List<MilkProductionEntity> returnList = reportManager.getMilkProdData(reportObj);
		
		Workbook wb = new HSSFWorkbook();
		Sheet reportSheet = wb.createSheet("ReportList");
		Row headerRow = reportSheet.createRow(0);
		Cell cell1 = headerRow.createCell(0);
		Cell cell2 = headerRow.createCell(1);
		Cell cell3 = headerRow.createCell(2);
		Cell cell4 = headerRow.createCell(3);
		Cell cell5 = headerRow.createCell(4);
		Cell cell6 = headerRow.createCell(5);
		Cell cell7 = headerRow.createCell(6);
		
		cell1.setCellValue("Date Of Lactation");
		cell2.setCellValue("Doe Ear Tag");
		cell3.setCellValue("D.O.B");
		cell4.setCellValue("Location");
		cell5.setCellValue("Farm");
		cell6.setCellValue("Farm Code");
		cell7.setCellValue("Milk Quantity");
		
		int row = 1;
		
		for(MilkProductionEntity milkProductionEntityobj:returnList){
			
			String milkProductiondate = milkProductionEntityobj.getMilkproddt()+"";
			String doeEartag = milkProductionEntityobj.getObjdoe().getDoecattle().getCattleeartagid()!=null?milkProductionEntityobj.getObjdoe().getDoecattle().getCattleeartagid():"";
			String cattleDob = milkProductionEntityobj.getObjdoe().getCattledob()+"";
			String location = milkProductionEntityobj.getObjdoe().getCattlelocation();
			String farmName = milkProductionEntityobj.getObjdoe().getFarm().getFarmname();
			String farmCode = milkProductionEntityobj.getObjdoe().getFarm().getFarmcode();
			Double milkQuantity=milkProductionEntityobj.getMilkprodqty();
			
			Row dataRow = reportSheet.createRow(row);
			
			Cell data1 = dataRow.createCell(0);
			data1.setCellValue(milkProductiondate);
			Cell data2 = dataRow.createCell(1);
			data2.setCellValue(doeEartag);
			Cell data3 = dataRow.createCell(2);
			data3.setCellValue(cattleDob);
			Cell data4 = dataRow.createCell(3);
			data4.setCellValue(location);
			Cell data5 = dataRow.createCell(4);
			data5.setCellValue(farmName);
			Cell data6 = dataRow.createCell(5);
			data6.setCellValue(farmCode);
			Cell data7 = dataRow.createCell(6);
			data7.setCellValue(milkQuantity);
			
			row = row + 1;
			
		}
		try{
			String parentDirectory =  context.getRealPath("") + File.separator + "uploads";
			String outputDirPath = "MilkProductionReport"+System.nanoTime()+".xls";
			File file = new File(parentDirectory+File.separator+outputDirPath);
			if(!file.exists()){
				file.createNewFile();
			}
			FileOutputStream fileOut = new FileOutputStream(file);
			wb.write(fileOut);
			fileOut.close();
	         
			if(null != returnList && returnList.size()>0){
				fileName=outputDirPath;
			}
		}catch(Exception er){			
			logger.error("Error in exportMilkProductionReport "+er.getMessage());		
		}
				
		return fileName;
	}

	public String exportGoatReport(ReportEntity reportObj) {
		
		String fileName=null;
		
		
		List<CattleEntity> returnList = reportManager.getCattleDtls(reportObj);
		//System.out.println("Result is"+resGson.toJson(returnList));
		
		Workbook wb = new HSSFWorkbook();
		Sheet reportSheet = wb.createSheet("ReportList");
		Row headerRow = reportSheet.createRow(0);
		Cell cell1 = headerRow.createCell(0);
		Cell cell2 = headerRow.createCell(1);
		Cell cell3 = headerRow.createCell(2);
		Cell cell4 = headerRow.createCell(3);
		Cell cell5 = headerRow.createCell(4);
		Cell cell6 = headerRow.createCell(5);
		
		cell1.setCellValue("Farm code");
		cell2.setCellValue("Ear Tag");
		cell3.setCellValue("D.O.B");
		cell4.setCellValue("Buck Ear Tag");
		cell5.setCellValue("Doe Ear Tag");
		cell6.setCellValue("Status");
		
		int row = 1;
		for (CattleEntity cattleEntityObj:returnList){
			
			String farmName = cattleEntityObj.getFarm().getFarmname();
			String earTag = cattleEntityObj.getCattleeartagid();
			String cattleDob=cattleEntityObj.getCattledob()+"";
			cattleDob = cattleDob.replace("00:00:00.0", "");
			String buckEarTag = cattleEntityObj.getBuckcattle()!=null ? cattleEntityObj.getBuckcattle().getCattleeartagid():"" ;
			String doeEarTag =  cattleEntityObj.getDoecattle()!=null ? cattleEntityObj.getDoecattle().getCattleeartagid():"";
			String status = cattleEntityObj.isCattlestatus()==true?"Active":"Inactive";
		
			Row dataRow = reportSheet.createRow(row);
			
			Cell dataCattleId = dataRow.createCell(0);
			dataCattleId.setCellValue(farmName);
			Cell dataFarmName = dataRow.createCell(1);
			dataFarmName.setCellValue(earTag);
			Cell dataCattleDob = dataRow.createCell(2);
			dataCattleDob.setCellValue(cattleDob);
			Cell dataBuckEarTag = dataRow.createCell(3);
			dataBuckEarTag.setCellValue(buckEarTag);
			Cell dataDoeEarTag = dataRow.createCell(4);
			dataDoeEarTag.setCellValue(doeEarTag);
			Cell dataStatus = dataRow.createCell(5);
			dataStatus.setCellValue(status);
			
			row = row + 1;
		
		}
		try{
			String parentDirectory =  context.getRealPath("") + File.separator + "uploads";
			String outputDirPath = "Goatreport"+System.nanoTime()+".xls";
			File file = new File(parentDirectory+File.separator+outputDirPath);
			if(!file.exists()){
				file.createNewFile();
			}
			FileOutputStream fileOut = new FileOutputStream(file);
			wb.write(fileOut);
			fileOut.close();
	         
			if(null != returnList && returnList.size()>0){
				fileName=outputDirPath;
			}
		}catch(Exception er){
			logger.error("Error in exportGoatReport "+er.getMessage());	
			
		}
     	
         return fileName;		
	}
	
}
