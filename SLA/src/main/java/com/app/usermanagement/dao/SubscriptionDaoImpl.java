package com.app.usermanagement.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.usermanagement.entity.UserSubscriptionEntity;

import com.app.utils.RepositoryConstants;

@Repository(RepositoryConstants.SUBSCRIPTION_DAO)
public class SubscriptionDaoImpl implements SubscriptionDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<UserSubscriptionEntity> getSubscriptions() throws Exception {
		return sessionFactory.getCurrentSession().createCriteria(UserSubscriptionEntity.class).list();
	}

	@Override
	public UserSubscriptionEntity getSubscription(int subscriptionId) throws Exception {
		return (UserSubscriptionEntity) sessionFactory.getCurrentSession().get(UserSubscriptionEntity.class,
				subscriptionId);
	}

	@Override
	public UserSubscriptionEntity saveSubscription(UserSubscriptionEntity userSubscription) throws Exception {
		this.sessionFactory.getCurrentSession().save(userSubscription);
		return userSubscription;
	}

	@Override
	public void updateSubscription(UserSubscriptionEntity userSubscription) throws Exception {
		this.sessionFactory.getCurrentSession().update(userSubscription);
	}

	@Override
	public boolean deleteSubscription(int subscriptionId) throws Exception {
		String sql = "delete UserSubscriptionEntity userSubscription where userSubscription.id = :subscriptionId";
		Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("subscriptionId", subscriptionId);
		return query.executeUpdate() > 0;
	}

}
