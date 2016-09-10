package com.app.usermanagement.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.usermanagement.entity.UserTypeEntity;
import com.app.utils.RepositoryConstants;

@Repository(RepositoryConstants.USER_TYPE_DAO)
public class UserTypeDaoImpl implements UserTypeDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<UserTypeEntity> getUserTypes() throws Exception {
		return sessionFactory.getCurrentSession().createCriteria(UserTypeEntity.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public UserTypeEntity getUserType(int userTypeId) throws Exception {
		return (UserTypeEntity) sessionFactory.getCurrentSession().get(UserTypeEntity.class, userTypeId);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public UserTypeEntity saveUserType(UserTypeEntity userTypeEntity) throws Exception {
		this.sessionFactory.getCurrentSession().save(userTypeEntity);
		return userTypeEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void updateUserType(UserTypeEntity userTypeEntity) throws Exception {
		this.sessionFactory.getCurrentSession().update(userTypeEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean deleteUserType(int userTypeId) throws Exception {
		String sql = "delete UserTypeEntity userType where userType.id = :userTypeId";
		Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("userTypeId", userTypeId);
		return query.executeUpdate() > 0;
	}

}
