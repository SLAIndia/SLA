package com.inapp.cms.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.inapp.cms.entity.SurgeryMasterEntity;
import com.inapp.cms.utils.RepositoryConstants;

@Repository(RepositoryConstants.SURGERYTYPE_DAO)
public class SurgeryMasterDAOImpl implements SurgeryMasterDAO{
	
	private static Logger logger = Logger.getLogger(SurgeryMasterDAOImpl.class);

	@Autowired 
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public SurgeryMasterEntity saveSurgeryType(
			SurgeryMasterEntity surgerymasterEntity) {
		try{
			
			 this.sessionFactory.getCurrentSession().saveOrUpdate(surgerymasterEntity);
			
		}catch(Exception e){
			logger.error("error in  saveSurgeryType"+e.getMessage());
		}
		
		
		return surgerymasterEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<SurgeryMasterEntity> getSurgeryList(
			SurgeryMasterEntity surgerymasterEntity) {
		List<SurgeryMasterEntity> result  = null;
		String sql = " from SurgeryMasterEntity  ";
		if(null !=surgerymasterEntity.getSurgeryid() && surgerymasterEntity.getSurgeryid() >0){
			sql += "where surgeryid =:id ";
		}
		try{
			Query query = this.sessionFactory.getCurrentSession()
					.createQuery(sql);
			if(null !=surgerymasterEntity.getSurgeryid() && surgerymasterEntity.getSurgeryid() >0){
				query.setParameter("id", surgerymasterEntity.getSurgeryid());
			}
			result = query.list();
			
		}catch(Exception e){
			logger.error("Error in getSurgeryList method "+e.getMessage());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean isSurgeryNameExist(SurgeryMasterEntity surgerymasterEntity) {
		List<SurgeryMasterEntity> result  = null;
		String sql = " from SurgeryMasterEntity where surgeryname =:name ";
		if(null !=surgerymasterEntity.getSurgeryid() && surgerymasterEntity.getSurgeryid() >0){
			sql += "and surgeryid !=:id ";
		}
		try{
			Query query = this.sessionFactory.getCurrentSession()
					.createQuery(sql);
			query.setParameter("name", surgerymasterEntity.getSurgeryname());
			if(null !=surgerymasterEntity.getSurgeryid() && surgerymasterEntity.getSurgeryid() >0){
				query.setParameter("id", surgerymasterEntity.getSurgeryid());
			}
			result = query.list();
		}catch(Exception e){
			logger.error("Error in isSurgeryNameExist method"+e.getMessage());
		}
		
		 return !result.isEmpty();
	}
}
