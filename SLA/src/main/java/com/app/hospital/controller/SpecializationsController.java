package com.app.hospital.controller;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
	@RequestMapping( value ="/getSpecializations",method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AppResponse getSpecializations() throws Exception {
		AppResponse response = new AppResponse();
		try {
		ObjectMapper mapper = new ObjectMapper();
		List<SpecializationsEntity> specializationsList = specializationsService.getSpecializations();
		response.put("data", mapper.writeValueAsString(specializationsList));
		} catch (Exception e) {
			logger.error("error in getSpecializations :"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value ="/saveSpecializations",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AppResponse getSpecializations(@RequestBody String jsonRequestString) throws Exception {
		AppResponse response = new AppResponse();
		try {
		ObjectMapper mapper = new ObjectMapper();
		SpecializationsEntity objSpecializations = new SpecializationsEntity();
		objSpecializations = mapper.readValue(jsonRequestString, SpecializationsEntity.class);
		objSpecializations = specializationsService.saveSpecializations(objSpecializations);
		response.put("data", mapper.writeValueAsString(objSpecializations));
		} catch (Exception e) {
			logger.error("error in saveSpecializations :"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value ="/deleteSpecializations",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody AppResponse deleteSpecializations(@RequestBody String jsonRequestString) throws Exception {
		AppResponse response = new AppResponse();
		try {
		ObjectMapper mapper = new ObjectMapper();
		SpecializationsEntity objSpecializations = new SpecializationsEntity();
		objSpecializations = mapper.readValue(jsonRequestString, SpecializationsEntity.class);
		int res = specializationsService.deleteSpecialization(objSpecializations);
		response.put("data", mapper.writeValueAsString(res));
		} catch (Exception e) {
			logger.error("error in deleteSpecializations :"+e.getMessage());
		}
		return response;
	}
	
}
