package com.app.usermanagement.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.usermanagement.entity.UserDetailsEntity;
import com.app.usermanagement.entity.UserEntity;
import com.app.utils.RepositoryConstants;

@Repository(RepositoryConstants.USER_DAO)
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public List<UserDetailsEntity> getUsers() throws Exception {
		return sessionFactory.getCurrentSession().createCriteria(UserDetailsEntity.class).list();
	}

	@Override
	@Transactional
	public UserDetailsEntity getUser(int userTypeId) throws Exception {
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(UserDetailsEntity.class);
		cr.add(Restrictions.eq("user.id", userTypeId));
		return (UserDetailsEntity) cr.add(Restrictions.eq("user.id", userTypeId)).uniqueResult();
	}

	@Override
	@Transactional
	public UserEntity saveUser(UserEntity userEntity) throws Exception {
		this.sessionFactory.getCurrentSession().save(userEntity);
		return userEntity;
	}

	@Override
	@Transactional
	public UserDetailsEntity saveUserDetails(UserDetailsEntity userDetailsEntity) throws Exception {
		this.sessionFactory.getCurrentSession().save(userDetailsEntity);
		return userDetailsEntity;
	}

	@Override
	@Transactional
	public void updateUser(UserEntity userEntity) throws Exception {
		this.sessionFactory.getCurrentSession().update(userEntity);
	}
	
	@Override
	@Transactional
	public void updateUserDetails(UserDetailsEntity userDetailsEntity) throws Exception {
		this.sessionFactory.getCurrentSession().update(userDetailsEntity);
	}

	@Override
	@Transactional
	public boolean isUsernameAlreadyInUse(String username, Integer userId) throws Exception {
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(UserDetailsEntity.class);
		cr.createAlias("user", "u");
		cr.add(Restrictions.eq("u.username", username));
		if (userId != null && userId != 0) {
			cr.add(Restrictions.ne("u.id", userId));
		}
		return cr.uniqueResult() != null;
	}

	@Override
	@Transactional
	public boolean isPhoneAlreadyInUse(String phone1, Integer userId) throws Exception {
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(UserDetailsEntity.class);
		cr.createAlias("user", "u");
		cr.add(Restrictions.eq("phone1", phone1));
		if (userId != null && userId != 0) {
			cr.add(Restrictions.ne("u.id", userId));
		}
		return cr.uniqueResult() != null;
	}

	@Override
	@Transactional
	public boolean deleteUser(int userId) throws Exception {
		String sql = "delete UserEntity userEntity where userEntity.id = :userId";
		Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("userId", userId);
		return query.executeUpdate() > 0;
	}

}
