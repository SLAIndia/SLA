package com.app.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.handlers.AppResponse;
import com.app.usermanagement.entity.UserDetailsEntity;
import com.app.usermanagement.service.UserService;

@RestController
@RequestMapping("/usermanagement/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AppResponse getUsers() throws Exception {
		AppResponse response = new AppResponse();
		response.put(AppResponse.DATA_FIELD, userService.getUsers());
		return response;
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AppResponse getUser(@PathVariable("userId") int userId) throws Exception {
		AppResponse response = new AppResponse();
		response.put(AppResponse.DATA_FIELD, userService.getUser(userId));
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AppResponse registerUser(@RequestBody UserDetailsEntity userDetailsEntity) throws Exception {
		AppResponse response = new AppResponse();
		userService.registerUser(userDetailsEntity);
		return response;
	}

	@RequestMapping(value = "/isUsernameInUse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	public AppResponse isUsernameAlreadyInUse(@RequestParam(value = "username") String username) throws Exception {
		AppResponse response = new AppResponse();

		System.out.println("username " + username);

		response.put("data", "[]");

		return response;
	}

}
