package com.app.usermanagement.controller;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.handlers.AppResponse;

@RestController
@RequestMapping("/usermanagement")
public class UserController {

	@RequestMapping( 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE, consumes= MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AppResponse getusermanagements(@RequestBody String jsonRequestString) throws Exception {
		AppResponse response = new AppResponse();
		ObjectMapper mapper = new ObjectMapper();
	//	pj = mapper.readValue(json, POJO.class);
		response.put("data", "[]");

		return response;
	}
}
