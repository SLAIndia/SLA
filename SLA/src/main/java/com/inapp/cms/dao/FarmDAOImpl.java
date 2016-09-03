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

import com.inapp.cms.entity.FarmEntity;
import com.inapp.cms.utils.Common;
import com.inapp.cms.utils.RepositoryConstants;

@Repository(RepositoryConstants.FARM_DAO)
public class FarmDAOImpl implements FarmDAO{
	private static final Logger logger = Logger.getLogger(FarmDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public HashMap<String, Object> saveFarm(FarmEntity objFarmEntity) {
		 List<HashMap<String, Object>> aliasToValueMapList = null;
		  String sql = "SELECT farm_id FROM farm.fn_farm_save(:farmid,:farmname,:farmaddress,:farmlocation,:farmcreatedate,:farmstatus,:createddt)";
		  try {
			  Query query = this.sessionFactory
	                    .getCurrentSession()
	                    .createSQLQuery(sql)
	                    .setResultTransformer(
	                            AliasToEntityMapResultTransformer.INSTANCE);
	           
			    query.setParameter("farmid", objFarmEntity.getFarmid());
	            query.setParameter("farmname", objFarmEntity.getFarmname());
	            query.setParameter("farmaddress",objFarmEntity.getFarmaddress());
	            query.setParameter("farmlocation",objFarmEntity.getFarmlocation());
	            query.setParameter("farmcreatedate",Common.getCurrentTimestamp());
	            query.setParameter("farmstatus", objFarmEntity.isFarmstatus());
	            query.setParameter("createddt", objFarmEntity.getCreateddt());
	            aliasToValueMapList = query.list(); 

		  }catch(Exception e){
			  logger.error("Error in saveFarm Method "+e.getMessage());
		  }
		return aliasToValueMapList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<HashMap<String, Object>> getFarmDetails(int usr_id) {
		 List<HashMap<String, Object>> aliasToValueMapList = null;
		 String sql = "SELECT * FROM farm.fn_farm_sel() order by farm_id";
		  try {
			  Query query = this.sessionFactory
	                    .getCurrentSession()
	                    .createSQLQuery(sql)
	                    .setResultTransformer(
	                            AliasToEntityMapResultTransformer.INSTANCE);
			  
	            aliasToValueMapList = query.list(); 

		  }catch(Exception e){
			  logger.error("Error in getFarmDetails Method "+e.getMessage());
		  }
		return aliasToValueMapList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public FarmEntity getFarmData(FarmEntity objFarmEntity) {

		String sql = "from FarmEntity f where farmid =:farm_id";
		FarmEntity obj =null;
		try {
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			query.setParameter("farm_id", objFarmEntity.getFarmid());
			List<FarmEntity> result =  query.list();
			if(result != null && result.size()>0){
				obj = (FarmEntity) result.get(0);
			}
		} catch (Exception e) {
			logger.error("Error in getFarmData Method " + e.getMessage());
		}
		return obj;

	}

	@Override
	@Transactional
	public int deleteFarm(FarmEntity objFarmEntity){
		int status = 0;
	
		try {
			
			String sql = "update FarmEntity set farmdeletestatus = true where farmid = :farmId  ";
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			query.setParameter("farmId", objFarmEntity.getFarmid());
			status = query.executeUpdate();
		} catch (Exception e) {
			status = 0;
			logger.error("Error in deleteFarm method "+ e.getMessage());
			
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<HashMap<String, Object>> getFarmDetailsByUser(int user_id, String roleName) {
		 List<HashMap<String, Object>> aliasToValueMapList = null;
		 String sql = "";
		 if(roleName.toUpperCase().equals("OWNER"))
		 {
			 sql =  "select farm_id,farm_code,farm_name, farm_location, farm_status from farm.fn_owner_farm_sel(:user_id)";
		 }
		 else if(roleName.toUpperCase().equals("VET"))
		 {
			 sql =  "select farm_id,farm_code,farm_name, farm_location, farm_status  from farm.fn_vet_farm_sel(:user_id)"; 
		 }
		  try {
			  Query query = this.sessionFactory
	                    .getCurrentSession()
	                    .createSQLQuery(sql)
	                    .setResultTransformer(
	                            AliasToEntityMapResultTransformer.INSTANCE);
			  
			  query.setParameter("user_id", user_id);
	          aliasToValueMapList = query.list(); 

		  }catch(Exception e){
			  e.printStackTrace();
			  logger.error("Error in getFarmDetailsByUser Method "+e.getMessage());
		  }
		return aliasToValueMapList;
	}
	

	@SuppressWarnings("unchecked")
	@Transactional
	public List<HashMap<String, Object>> getFarmDetailsByCodeorName(String codeName) {
		 List<HashMap<String, Object>> aliasToValueMapList = null;
		 String sql = "SELECT farm_id, farm_name, farm_code FROM farm.fn_farm_by_sel(:farmcodename) where farm_status = true order by farm_id";
		  try {
			  Query query = this.sessionFactory
	                    .getCurrentSession()
	                    .createSQLQuery(sql)
	                    .setResultTransformer(
	                            AliasToEntityMapResultTransformer.INSTANCE);
			    query.setParameter("farmcodename", codeName);
	            aliasToValueMapList = query.list(); 

		  }catch(Exception e){
			  e.printStackTrace();
			  logger.error("Error in getFarmDetailsByCodeorName Method "+e.getMessage());
		  }
		return aliasToValueMapList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<HashMap<String, Object>> getAllFarmDetailsByUser(int user_id, String roleName) {
		 List<HashMap<String, Object>> aliasToValueMapList = null;
		 String sql = "";
		 if(roleName.toUpperCase().equals("OWNER"))
		 {
			 sql =  "select farm_id,farm_code,farm_name, farm_location, farm_status from farm.fn_owner_farm_all_sel(:user_id)";
		 }
		 else if(roleName.toUpperCase().equals("VET"))
		 {
			 sql =  "select farm_id,farm_code,farm_name, farm_location, farm_status  from farm.fn_vet_farm_all_sel(:user_id)"; 
		 }
		  try {
			  Query query = this.sessionFactory
	                    .getCurrentSession()
	                    .createSQLQuery(sql)
	                    .setResultTransformer(
	                            AliasToEntityMapResultTransformer.INSTANCE);
			  
			  query.setParameter("user_id", user_id);
	          aliasToValueMapList = query.list(); 

		  }catch(Exception e){
			  e.printStackTrace();
			  logger.error("Error in getFarmDetailsByUser Method "+e.getMessage());
		  }
		return aliasToValueMapList;
	}

}
