package com.app.usermanagement.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.handlers.AppResponse;
import com.app.usermanagement.entity.UserTypeEntity;
import com.app.usermanagement.service.UserTypeService;
import com.app.utils.AppException;
import com.app.utils.AppMessage;

@RestController
@RequestMapping("/usermanagement/usertype")
public class UserTypeController {

	@Autowired
	private UserTypeService userTypeService;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AppResponse getUserTypes() throws Exception {
		AppResponse response = new AppResponse();
		response.put(AppResponse.DATA_FIELD, userTypeService.getUserTypes());
		return response;
	}
	
	@RequestMapping(value = "/{usertypeId}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AppResponse getUserType(@PathVariable("usertypeId") int usertypeId) throws Exception {
		AppResponse response = new AppResponse();
		response.put(AppResponse.DATA_FIELD, 
				Optional.ofNullable(userTypeService.getUserType(usertypeId))
				.orElseThrow(() -> new AppException(AppMessage.RECORD_NOT_FOUND)));
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AppResponse saveUserTypes(@RequestBody UserTypeEntity userTypeEntity) throws Exception {
		AppResponse response = new AppResponse();
		response.put(AppResponse.DATA_FIELD, userTypeService.saveUserType(userTypeEntity));
		return response;
	}

	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AppResponse updateUserType(@RequestBody UserTypeEntity userTypeEntity) throws Exception {
		AppResponse response = new AppResponse();
		userTypeService.updateUserType(userTypeEntity);
		return response;
	}

	@RequestMapping(value = "/{usertypeId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AppResponse deleteUserType(@PathVariable("usertypeId") int usertypeId) throws Exception {
		AppResponse response = new AppResponse();
		userTypeService.deleteUserType(usertypeId);
		return response;
	}
}
