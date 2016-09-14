package com.app.usermanagement.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.app.handlers.AppResponse;
import com.app.usermanagement.entity.UserDetailsEntity;
import com.app.usermanagement.service.UserService;
import com.app.utils.AppException;
import com.app.utils.AppMessage;

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
		response.put(AppResponse.DATA_FIELD, Optional.ofNullable(userService.getUser(userId))
				.orElseThrow(() -> new AppException(AppMessage.RECORD_NOT_FOUND)));
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AppResponse registerUser(@RequestBody UserDetailsEntity userDetailsEntity) throws Exception {
		AppResponse response = new AppResponse();
		userService.registerUser(userDetailsEntity);
		return response;
	}

	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AppResponse updateUser(@RequestBody UserDetailsEntity userDetailsEntity) throws Exception {
		AppResponse response = new AppResponse();
		userService.updateUser(userDetailsEntity);
		return response;
	}

	@RequestMapping(value = "/isUsernameInUse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	public AppResponse isUsernameAlreadyInUse(@RequestParam(value = "username") String username,
			@RequestParam(value = "userId", required = false) Integer userId) throws Exception {
		AppResponse response = new AppResponse();
		System.out.println("username " + username);
		if (userService.isUsernameAlreadyInUse(username, userId)) {
			response.put(AppResponse.MESSAGE_FIELD, AppMessage.USERNAME_EXISTS.getValue());
		} else {
			response.put(AppResponse.MESSAGE_FIELD, AppMessage.NOT_FOUND.getValue());
			response.put(AppResponse.STATUS, false);
		}
		return response;
	}

	@RequestMapping(value = "/isPhoneInUse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	public AppResponse isPhoneAlreadyInUse(@RequestParam(value = "phone") String phone,
			@RequestParam(value = "userId", required = false) Integer userId) throws Exception {
		AppResponse response = new AppResponse();
		System.out.println("phone " + phone);
		if (userService.isPhoneAlreadyInUse(phone, userId)) {
			response.put(AppResponse.MESSAGE_FIELD, AppMessage.PHONE_EXISTS.getValue());
		} else {
			response.put(AppResponse.MESSAGE_FIELD, AppMessage.NOT_FOUND.getValue());
			response.put(AppResponse.STATUS, false);
		}
		return response;
	}

	@RequestMapping(value = "/updateUserImages", method = RequestMethod.POST)
	@ResponseBody
	public AppResponse updateUserImages(@RequestParam("details") String details,
			MultipartHttpServletRequest fileRequest, @RequestParam(required = false) String[] images,
			@RequestParam(required = false) String deletedImgs) throws Exception {
		AppResponse response = new AppResponse();

		return response;
	}

}
