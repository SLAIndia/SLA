package com.app.usermanagement.service;

import java.util.List;

import com.app.usermanagement.entity.UserSubscriptionEntity;

public interface SubscriptionService {
	public List<UserSubscriptionEntity> getSubscriptions() throws Exception;

	public UserSubscriptionEntity getSubscription(int subscriptionId) throws Exception;

	public UserSubscriptionEntity saveSubscription(UserSubscriptionEntity userSubscription) throws Exception;

	public void updateSubscription(UserSubscriptionEntity userSubscription) throws Exception;

	public boolean deleteSubscription(int subscriptionId) throws Exception;
}
