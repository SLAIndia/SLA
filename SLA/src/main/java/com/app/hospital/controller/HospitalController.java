package com.app.hospital.controller;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.handlers.Response;
import com.app.usermanagement.dao.UserDaoImpl;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

	@RequestMapping( 
			value = "/post",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response getHospitals() throws Exception {
		Response response = new Response();
		//ObjectMapper mapper = new ObjectMapper();
		//UserDaoImpl pj = new UserDaoImpl();
		//pj = mapper.readValue(jsonRequestString, UserDaoImpl.class);
		response.put("data", "[]");
		return response;
	}
}
