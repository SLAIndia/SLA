/*package com.app.hospital.controller;
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
import com.app.hospital.entity.DoctorQualLinkEntity;
import com.app.hospital.service.DoctorQualLinkService;
import com.app.utils.AppMessage;

@RestController
@RequestMapping("/hospital/doctorDoctorQualLink")
public class DoctorQualLinkController {
	private static final Logger logger = Logger.getLogger(DoctorQualLinkController.class);
	@Autowired
	private DoctorQualLinkService qualificationsService;
	@RequestMapping( value ="/getDoctorDoctorQualLink",method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AppResponse getDoctorQualLink() throws Exception {
		AppResponse response = new AppResponse();
		try {
			response.put(AppResponse.DATA_FIELD, qualificationsService.getDoctorQualLink());
		} catch (Exception e) {
			logger.error("error in getDoctorQualLink :"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value ="/savequalifications",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AppResponse saveDoctorQualLink(@RequestBody DoctorQualLinkEntity objDoctorQualLink) throws Exception {
		AppResponse response = new AppResponse();
		try {
		if(qualificationsService.getQualificationByName(objDoctorQualLink.getUvc_qualif_name())==null){
		response.put(AppResponse.DATA_FIELD, qualificationsService.saveDoctorQualLink(objDoctorQualLink));
		}else if(qualificationsService.getQualificationByName(objDoctorQualLink.getUvc_qualif_name())!=null && objDoctorQualLink.getPki_doctor_qualif_master_id()==null){
			response.put(AppResponse.MESSAGE_FIELD,AppMessage.NAME_ALREADY_EXISTS);
			response.put(AppResponse.STATUS,false);
		}else if(qualificationsService.getQualificationByName(objDoctorQualLink.getUvc_qualif_name())!=null && objDoctorQualLink.getPki_doctor_qualif_master_id()!=null
				&& qualificationsService.getQualificationByName(objDoctorQualLink.getUvc_qualif_name()).getPki_doctor_qualif_master_id()!=objDoctorQualLink.getPki_doctor_qualif_master_id()){
			response.put(AppResponse.MESSAGE_FIELD,AppMessage.NAME_ALREADY_EXISTS);
			response.put(AppResponse.STATUS,false);
		}
		} catch (Exception e) {
			logger.error("error in savequalifications :"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value ="/deletequalifications/{pki_doctor_qualif_master_id}",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AppResponse deleteDoctorQualLink(@PathVariable("pki_doctor_qualif_master_id") long pki_doctor_qualif_master_id) throws Exception {
		AppResponse response = new AppResponse();
		try {
		int res = qualificationsService.deleteQualification(pki_doctor_qualif_master_id);
		response.put(AppResponse.MESSAGE_FIELD,res==0?AppResponse.FAILURE_MESSAGE:AppResponse.SUCCESS_MESSAGE);
		response.put(AppResponse.STATUS,res==0?false:true);
		} catch (Exception e) {
			logger.error("error in deleteDoctorQualLink :"+e.getMessage());
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
*/