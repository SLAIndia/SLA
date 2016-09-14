package com.app.usermanagement.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.usermanagement.dao.SubscriptionDao;
import com.app.usermanagement.entity.UserDetailsEntity;
import com.app.usermanagement.entity.UserSubscriptionEntity;
import com.app.utils.Common;
import com.app.utils.ServiceConstants;

@Service(ServiceConstants.SUBSCRIPTION_SERVICE)
public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	private SubscriptionDao subscriptionDao;

	@Override
	@Transactional
	public List<UserSubscriptionEntity> getSubscriptions() throws Exception {
		return subscriptionDao.getSubscriptions();
	}

	@Override
	@Transactional
	public UserSubscriptionEntity getSubscription(int subscriptionId) throws Exception {
		return subscriptionDao.getSubscription(subscriptionId);
	}

	@Override
	@Transactional
	public UserSubscriptionEntity saveSubscription(UserSubscriptionEntity userSubscription) throws Exception {
		Timestamp currentTime = Common.getCurrentTimestamp();
		userSubscription.setCreatedDt(currentTime);
		userSubscription.setUpdatedDt(currentTime);		
		return subscriptionDao.saveSubscription(userSubscription);
	}

	@Override
	@Transactional
	public void updateSubscription(UserSubscriptionEntity userSubscription) throws Exception {
		Timestamp currentTime = Common.getCurrentTimestamp();

		UserSubscriptionEntity userSubscriptionDB = subscriptionDao.getSubscription(userSubscription.getId());
		userSubscriptionDB.setFromDate(userSubscription.getFromDate());
		userSubscriptionDB.setToDate(userSubscription.getToDate());
		userSubscriptionDB.setDescription(userSubscription.getDescription());
		userSubscriptionDB.setStatus(userSubscription.getStatus());
		userSubscriptionDB.setUpdatedDt(currentTime);

		subscriptionDao.updateSubscription(userSubscriptionDB);
	}

	@Override
	@Transactional
	public boolean deleteSubscription(int subscriptionId) throws Exception {
		return subscriptionDao.deleteSubscription(subscriptionId);
	}

}
