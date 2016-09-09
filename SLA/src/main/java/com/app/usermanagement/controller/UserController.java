package com.app.usermanagement.controller;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.handlers.Response;

@RestController
@RequestMapping("/usermanagement")
public class UserController {

	@RequestMapping( 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE, consumes= MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Response getusermanagements(@RequestBody String jsonRequestString) throws Exception {
		Response response = new Response();
		ObjectMapper mapper = new ObjectMapper();
	//	pj = mapper.readValue(json, POJO.class);
		response.put("data", "[]");

		return response;
	}
}
