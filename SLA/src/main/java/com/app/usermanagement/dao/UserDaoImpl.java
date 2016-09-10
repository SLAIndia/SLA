package com.app.usermanagement.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
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
	public List<UserEntity> getUsers() throws Exception {
		return sessionFactory.getCurrentSession().createCriteria(UserEntity.class).list();
	}

	@Override
	@Transactional
	public UserEntity getUser(int userTypeId) throws Exception {
		return (UserEntity) sessionFactory.getCurrentSession().get(UserEntity.class, userTypeId);
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
	public boolean isUsernameAlreadyInUse(String username, int userId) {
		// TODO Auto-generated method stub
		return false;
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
