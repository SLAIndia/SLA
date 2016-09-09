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

import com.app.handlers.Response;
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
	public @ResponseBody Response getSpecializations() throws Exception {
		Response response = new Response();
		try {
		ObjectMapper mapper = new ObjectMapper();
		List<SpecializationsEntity> specializationsList = specializationsService.getSpecializations();
		response.put("data", mapper.writeValueAsString(specializationsList));
		} catch (Exception e) {
			logger.error("error in SpecializationsController :"+e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value ="/saveSpecializations",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Response getSpecializations(@RequestBody String jsonRequestString) throws Exception {
		Response response = new Response();
		try {
		ObjectMapper mapper = new ObjectMapper();
		SpecializationsEntity objSpecializations = new SpecializationsEntity();
		objSpecializations = mapper.readValue(jsonRequestString, SpecializationsEntity.class);
		objSpecializations = specializationsService.saveSpecializations(objSpecializations);
		response.put("data", mapper.writeValueAsString(objSpecializations));
		} catch (Exception e) {
			logger.error("error in SpecializationsController :"+e.getMessage());
		}
		return response;
	}
	
}
