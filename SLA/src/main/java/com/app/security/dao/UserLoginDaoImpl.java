package com.app.security.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.usermanagement.entity.UserDetailsEntity;
import com.app.utils.AppUtil;
import com.app.utils.RepositoryConstants;

@Repository(RepositoryConstants.USER_LOGIN_DAO)
public class UserLoginDaoImpl implements UserLoginDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public UserDetailsEntity login(String username, String password) throws Exception {
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(UserDetailsEntity.class,"userDetails");
		cr.createAlias("userDetails.user", "userObj");
		cr.add(Restrictions.eq("userObj.username", username));
		cr.add(Restrictions.eq("userObj.password", AppUtil.getMD5(password)));
		return (UserDetailsEntity) cr.uniqueResult();
	}


}
