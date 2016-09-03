package com.inapp.cms.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.utils.Common;
import com.app.utils.RepositoryConstants;
import com.inapp.cms.entity.RoleEntity;
import com.inapp.cms.entity.UserEntity;

@Repository(RepositoryConstants.ADMIN_DAO)
public class AdminUserDAOImpl implements AdminUserDAO {
	private static final Logger logger = Logger.getLogger(AdminUserDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public UserEntity saveUserDetails(UserEntity objUserEntity) {
		try {
			this.sessionFactory.getCurrentSession().saveOrUpdate(objUserEntity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in saveUserDetails Method " + e.getMessage());
		}
		return objUserEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public RoleEntity getRole(UserEntity objUserEntity) {
		String sql = "from RoleEntity  where UPPER(rolename) = :rolename";
		List<RoleEntity> result = null;
		try {
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			query.setParameter("rolename", objUserEntity.getRole_name().toUpperCase());
			result =  query.list();
			
		} catch (Exception e) {
			logger.error("Error in getRole Method " + e.getMessage());
		}
		if(result.size()>0){
			return result.get(0);
		}
		else{
			return null;
					}
					
		
	}

}
