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
import com.inapp.cms.entity.FarmEntity;
import com.inapp.cms.entity.FarmOwnerLinkEntity;
import com.inapp.cms.entity.OwnerEntity;
import com.inapp.cms.service.FarmManager;

@Repository(RepositoryConstants.OWNER_DAO)
public class OwnerUserDAOImpl implements OwnerUserDAO {
	
	private static final Logger logger = Logger.getLogger(OwnerUserDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private FarmManager farmManager;

	@Override
	@Transactional
	public OwnerEntity saveOwnerDetails(OwnerEntity objOwnerEntity) {
		try{
			this.sessionFactory.getCurrentSession().saveOrUpdate(objOwnerEntity);
		}catch(Exception e){
			logger.error("error in saveOwnerDetails"+e.getMessage());
		}
		return objOwnerEntity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object> getUserDetails(int userId,String role) {
		String sql = "";String farmQuery = "";
		if(role.toUpperCase().equals("OWNER")){
			sql = "from OwnerEntity where  ";
		}
		else if(role.toUpperCase().equals("VET")){
			sql = "from VetEntity where ";
		}
		if(userId > 0)
		{
			sql += "  objUserEntity.id= "+userId+"";
		}
		else{
			System.out.println("id id "+userId);
			sql += " objUserEntity.id != 0";
		}
		List<Object> resultList = null;
		try {
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			resultList =  query.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getUserDetails Method " + e.getMessage());
		}
		
	return resultList;
	
	}

	@Transactional
		public int saveOwnerFarmMapping(OwnerEntity objOwnerEntity) {
		int status = 0;int deleteStatus =0;
			try{
			List<Integer> farmIds = objOwnerEntity.getFarmIds();
			String sql = "delete from FarmOwnerLinkEntity  where ownerid= :ownerId";
			
			Query query = this.sessionFactory.getCurrentSession().createQuery(
					sql);
			query.setParameter("ownerId", objOwnerEntity.getId());
			deleteStatus = query.executeUpdate();
			if(deleteStatus>0 || farmIds.size()>0){
			for(int farmId :farmIds)
			{
				status = 0;
				FarmOwnerLinkEntity objOwnerLinkEntity = new FarmOwnerLinkEntity();
				objOwnerLinkEntity.setOwnerid(objOwnerEntity.getId());
				objOwnerLinkEntity.setFarmid(farmId);
				FarmEntity objFarmEntity = new FarmEntity();
				objFarmEntity.setFarmid(farmId);
				String farmCode = farmManager.getFarm(objFarmEntity).getFarmcode();
				if(deleteStatus > 0){
					objOwnerLinkEntity.setDeleteddt(Common.getCurrentGMTTimestamp());
				}
				objOwnerLinkEntity.setUniquesynckey(objOwnerEntity.getUniquesynckey()+"/"+farmCode);
				objOwnerLinkEntity.setCreateddt(Common.getCurrentGMTTimestamp());
				this.sessionFactory.getCurrentSession().save(objOwnerLinkEntity);
				status = 1;
			}
			}
			}catch(Exception e){
				status = 0;
				logger.error("error in saveOwnerFarmMapping(delete and save)"+e.getMessage());
			}
			return status;
		}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object> getFarmsAssignedByUserId(int userId,String role) {
		String sql = "";
		if(role.toUpperCase().equals("OWNER")){
			sql = "select linkEntity.farm_owner_link_farm_id, farm.farm_name, farm.farm_code from farm.farm_owner_link linkEntity inner join usermanagement.owner owner on(linkEntity.farm_owner_link_owner_id=owner_id) " +
					"inner join farm.farm farm on(farm_owner_link_farm_id=farm.farm_id) inner join usermanagement.user us on(owner.owner_user_id=us.user_id) where us.user_id= "+userId+" and farm.farm_status = true";
		}
		else if(role.toUpperCase().equals("VET")){
			sql = "select linkEntity.farm_vet_link_farm_id, farm.farm_name, farm.farm_code from farm.farm_vet_link linkEntity inner join usermanagement.vet vet on(linkEntity.farm_vet_link_vet_id=vet_id) " +
					"inner join farm.farm farm on(farm_vet_link_farm_id=farm.farm_id) inner join usermanagement.user us on(vet.vet_user_id=us.user_id) where us.user_id= "+userId+" and farm.farm_status = true";
		}
		
		List<Object> resultList = null;
		try {
			Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql);
			resultList =  query.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getFarmsAssignedByUserId Method " + e.getMessage());
		}
		
	return resultList;
	
	}
	
}
