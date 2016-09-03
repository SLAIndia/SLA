package com.app.hospital.controller;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.common.Response;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

	@RequestMapping( 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response getHospitals() throws Exception {
		Response response = new Response();
		response.put("data", "[]");

		return response;
	}
}
