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
import com.app.hospital.entity.SpecializationsEntity;
import com.app.hospital.service.SpecializationsService;

@RestController
@RequestMapping("/hospital/specializations")
public class SpecializationsController {
	private static final Logger logger = Logger.getLogger(SpecializationsController.class);
	@Autowired
	private SpecializationsService specializationsService;
	@RequestMapping( value ="/getspecializations",method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AppResponse getSpecializations() throws Exception {
		AppResponse response = new AppResponse();
		try {
			response.put(AppResponse.DATA_FIELD, specializationsService.getSpecializations());
		} catch (Exception e) {
			logger.error("error in getSpecializations :"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value ="/savespecializations",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AppResponse saveSpecializations(@RequestBody SpecializationsEntity objSpecializations) throws Exception {
		AppResponse response = new AppResponse();
		try {
		response.put(AppResponse.DATA_FIELD, specializationsService.saveSpecializations(objSpecializations));
		} catch (Exception e) {
			logger.error("error in savespecializations :"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value ="/deletespecializations/{pki_hos_dept_type_id}",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AppResponse deleteSpecializations(@PathVariable("pki_hos_dept_type_id") long pki_hos_dept_type_id) throws Exception {
		AppResponse response = new AppResponse();
		try {
		int res = specializationsService.deleteSpecialization(pki_hos_dept_type_id);
		response.put(AppResponse.MESSAGE_FIELD,res==0?AppResponse.FAILURE_MESSAGE:AppResponse.SUCCESS_MESSAGE);
		response.put(AppResponse.STATUS,res==0?false:true);
		} catch (Exception e) {
			logger.error("error in deleteSpecializations :"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value ="/getspecialization/{pki_hos_dept_type_id}",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AppResponse getSpecialization(@PathVariable("pki_hos_dept_type_id") long pki_hos_dept_type_id) throws Exception {
		AppResponse response = new AppResponse();
		try {
			response.put(AppResponse.DATA_FIELD,specializationsService.getSpecialization(pki_hos_dept_type_id));
		} catch (Exception e) {
			logger.error("error in getSpecialization :"+e.getMessage());
		}
		return response;
	}
	
}
