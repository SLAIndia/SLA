package com.app.usermanagement.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.usermanagement.entity.RoleEntity;
import com.app.utils.RepositoryConstants;

@Repository(RepositoryConstants.ROLE_DAO)
public class RoleDaoImpl implements RoleDao {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<RoleEntity> getRoles()  throws Exception{
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(RoleEntity.class);
		return cr.list();
	}

}
