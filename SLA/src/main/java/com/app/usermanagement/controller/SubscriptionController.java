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
import com.app.usermanagement.entity.UserSubscriptionEntity;
import com.app.usermanagement.service.SubscriptionService;
import com.app.utils.AppException;
import com.app.utils.AppMessage;

@RestController
@RequestMapping("/usermanagement/subscription")
public class SubscriptionController {
	@Autowired
	private SubscriptionService subscriptionService;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AppResponse getSubscriptions() throws Exception {
		AppResponse response = new AppResponse();
		response.put(AppResponse.DATA_FIELD, subscriptionService.getSubscriptions());
		return response;
	}

	@RequestMapping(value = "/{subscriptionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AppResponse getSubscription(@PathVariable("subscriptionId") int subscriptionId) throws Exception {
		AppResponse response = new AppResponse();
		response.put(AppResponse.DATA_FIELD, Optional.ofNullable(subscriptionService.getSubscriptions())
				.orElseThrow(() -> new AppException(AppMessage.RECORD_NOT_FOUND)));
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AppResponse saveSubscription(@RequestBody UserSubscriptionEntity userSubscriptionEntity) throws Exception {
		AppResponse response = new AppResponse();
		response.put(AppResponse.DATA_FIELD, subscriptionService.saveSubscription(userSubscriptionEntity));
		return response;
	}

	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AppResponse updateSubscription(@RequestBody UserSubscriptionEntity userSubscriptionEntity) throws Exception {
		AppResponse response = new AppResponse();
		subscriptionService.updateSubscription(userSubscriptionEntity);
		return response;
	}

	@RequestMapping(value = "/{subscriptionId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public AppResponse deleteSubscription(@PathVariable("subscriptionId") int subscriptionId) throws Exception {
		AppResponse response = new AppResponse();
		subscriptionService.deleteSubscription(subscriptionId);
		return response;
	}
}
