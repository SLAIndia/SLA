package com.inapp.cms.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.utils.Common;
import com.app.utils.RepositoryConstants;
import com.inapp.cms.entity.FarmEntity;
import com.inapp.cms.entity.FarmOwnerLinkEntity;
import com.inapp.cms.entity.FarmVetLinkEntity;
import com.inapp.cms.entity.UserEntity;
import com.inapp.cms.entity.VetEntity;
import com.inapp.cms.service.FarmManager;

@Repository(RepositoryConstants.VET_DAO)
public class VetDAOImpl implements VetDAO{
	
	private static final Logger logger = Logger.getLogger(VetDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private FarmManager farmManager;
	
	@Override
	@Transactional
	public UserEntity save(UserEntity vetUserEntity) throws Exception {
		try {
			vetUserEntity.setUsercreateddt(Common.getCurrentTimestamp());
			this.sessionFactory.getCurrentSession().saveOrUpdate(vetUserEntity);			
		} catch (Exception e) {
			logger.error("Error in save vet user details " + e.getMessage(), e);
		}
		return vetUserEntity;
	}

	@Override
	@Transactional
	public VetEntity saveVetDetails(VetEntity vetEntity) throws Exception {
		try {			
			this.sessionFactory.getCurrentSession().saveOrUpdate(vetEntity);			
		} catch (Exception e) {
			logger.error("Error in save vet details " + e.getMessage(), e);
		}
		return vetEntity;
	}

	@Override
	@Transactional
	public List<HashMap<String, Object>> getAllVetByFnameOrLname(String code) {
		List<HashMap<String, Object>> aliasToValueMapList = null;
		String sql = "select user_fname,user_lname,user_name,vet_id from usermanagement.fn_vet_all_by_name_sel(:code) where user_active = true and vet_status = 1";
		try{
		  Query query = this.sessionFactory
                  .getCurrentSession()
                  .createSQLQuery(sql)
                  .setResultTransformer(
                          AliasToEntityMapResultTransformer.INSTANCE);
		    query.setParameter("code", code);
          aliasToValueMapList = query.list(); }
          catch(Exception e)
			{
				logger.error("Error in save getAllVetByFnameOrLname " + e.getMessage(), e);
			}
		
		return aliasToValueMapList;

	}
	
	@Transactional
	public int saveVetFarm(VetEntity objVetEntity) {
	int status = 0;int deleteStatus =0;
		try{
		List<Integer> farmIds = objVetEntity.getFarmIds();
		String sql = "delete from FarmVetLinkEntity  where vetid= :vetId";
		
		Query query = this.sessionFactory.getCurrentSession().createQuery(
				sql);
		query.setParameter("vetId", objVetEntity.getVetid());
		deleteStatus = query.executeUpdate();
		if(deleteStatus>0 || farmIds.size()>0){
		for(int farmId :farmIds)
		{
			status = 0;
			FarmVetLinkEntity objVetLinkEntity = new FarmVetLinkEntity();
			objVetLinkEntity.setVetid(objVetEntity.getVetid());
			objVetLinkEntity.setFarmid(farmId);
			FarmEntity objFarmEntity = new FarmEntity();
			objFarmEntity.setFarmid(farmId);
			String farmCode = farmManager.getFarm(objFarmEntity).getFarmcode();
			if(deleteStatus > 0){
				objVetLinkEntity.setDeleteddt(Common.getCurrentGMTTimestamp());
			}
			objVetLinkEntity.setUniquesynckey(objVetEntity.getUniquesynckey()+"/"+farmCode);
			objVetLinkEntity.setCreateddt(Common.getCurrentGMTTimestamp());
			this.sessionFactory.getCurrentSession().save(objVetLinkEntity);
			status = 1;
		}
		}
		}catch(Exception e){
			status = 0;
			logger.error("error in saveVetFarm(delete and save)"+e.getMessage());
		}
		return status;
	}

	@Transactional
	public int approveVet(VetEntity objVetEntity) {
	int status = 0;
		try{
		String sql = "update  VetEntity  set vetStatus = :vetStatus, updateddt = :updateddt where vetid= :vetId";
		String sqlUser = "";
		if(objVetEntity.getVetStatus()==0)
		{
			 sqlUser = "update  UserEntity  set isactive = false ,updateddt = :updateddt where id= :userId";
		}else{
			 sqlUser = "update  UserEntity  set isactive = true, updateddt = :updateddt where id= :userId";
		}
		
		Query query = this.sessionFactory.getCurrentSession().createQuery(
				sql);
		query.setParameter("vetStatus", objVetEntity.getVetStatus());
		if(null == objVetEntity.getUpdateddt()){
			objVetEntity.setUpdateddt(Common.getCurrentGMTTimestamp());
		}
		query.setParameter("updateddt", objVetEntity.getUpdateddt());
		query.setParameter("vetId", objVetEntity.getVetid());
		query.executeUpdate();
		
		//update User
		Query queryUser = this.sessionFactory.getCurrentSession().createQuery(
				sqlUser);
		queryUser.setParameter("userId", objVetEntity.getObjUserEntity().getId());
		queryUser.setParameter("updateddt", objVetEntity.getUpdateddt());
		status = queryUser.executeUpdate();
		//status = 1;
		
		}catch(Exception e){
			e.printStackTrace();
			status = 0;
			logger.error("error in approveVet)"+e.getMessage());
		}
		return status;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public VetEntity getVetDetails(int userId) {
		String sql = "from  VetEntity  where objUserEntity.id= :userId";
		VetEntity objVetEntity = null;
		try {
			Query query = this.sessionFactory.getCurrentSession().createQuery(
					sql);
			query.setParameter("userId", userId);	
			List<VetEntity> list = query.list();
			if(list!=null && list.size()>0)
			{
				objVetEntity = list.get(0);
			}
		} catch (Exception e) {
			logger.error("Error in getVetDetails " + e.getMessage(), e);
		}
		return objVetEntity;
	}


}
