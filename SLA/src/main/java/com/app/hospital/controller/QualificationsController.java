package com.app.hospital.controller;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.handlers.AppResponse;
import com.app.hospital.entity.QualificationsEntity;
import com.app.hospital.service.QualificationsService;

@RestController
@RequestMapping("/hospital/qualifications")
public class QualificationsController {
	private static final Logger logger = Logger.getLogger(QualificationsController.class);
	@Autowired
	private QualificationsService qualificationsService;
	@RequestMapping( value ="/getqualifications",method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AppResponse getQualifications() throws Exception {
		AppResponse response = new AppResponse();
		try {
			response.put(AppResponse.DATA_FIELD, qualificationsService.getQualifications());
		} catch (Exception e) {
			logger.error("error in getQualifications :"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value ="/savequalifications",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AppResponse saveQualifications(@RequestBody QualificationsEntity objQualifications) throws Exception {
		AppResponse response = new AppResponse();
		try {
		if(qualificationsService.getQualificationByName(objQualifications.getUvc_qualif_name())==null){
		response.put(AppResponse.DATA_FIELD, qualificationsService.saveQualifications(objQualifications));
		}else if(qualificationsService.getQualificationByName(objQualifications.getUvc_qualif_name())!=null && objQualifications.getPki_doctor_qualif_master_id()==null){
			response.put(AppResponse.MESSAGE_FIELD,AppResponse.FAILURE_ALREADY_EXISTS_MESSAGE);
			response.put(AppResponse.STATUS,false);
		}else if(qualificationsService.getQualificationByName(objQualifications.getUvc_qualif_name())!=null && objQualifications.getPki_doctor_qualif_master_id()!=null
				&& qualificationsService.getQualificationByName(objQualifications.getUvc_qualif_name()).getPki_doctor_qualif_master_id()!=objQualifications.getPki_doctor_qualif_master_id()){
			response.put(AppResponse.MESSAGE_FIELD,AppResponse.FAILURE_ALREADY_EXISTS_MESSAGE);
			response.put(AppResponse.STATUS,false);
		}
		} catch (Exception e) {
			logger.error("error in savequalifications :"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value ="/deletequalifications/{pki_doctor_qualif_master_id}",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AppResponse deleteQualifications(@PathVariable("pki_doctor_qualif_master_id") long pki_doctor_qualif_master_id) throws Exception {
		AppResponse response = new AppResponse();
		try {
		int res = qualificationsService.deleteQualification(pki_doctor_qualif_master_id);
		response.put(AppResponse.MESSAGE_FIELD,res==0?AppResponse.FAILURE_MESSAGE:AppResponse.SUCCESS_MESSAGE);
		response.put(AppResponse.STATUS,res==0?false:true);
		} catch (Exception e) {
			logger.error("error in deleteQualifications :"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value ="/getqualification/{pki_doctor_qualif_master_id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AppResponse getQualification(@PathVariable("pki_doctor_qualif_master_id") long pki_doctor_qualif_master_id) throws Exception {
		AppResponse response = new AppResponse();
		try {
			response.put(AppResponse.DATA_FIELD,qualificationsService.getQualification(pki_doctor_qualif_master_id));
		} catch (Exception e) {
			logger.error("error in getQualification :"+e.getMessage());
		}
		return response;
	}
	
}
