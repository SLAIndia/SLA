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

import com.inapp.cms.entity.BreedKidsEntity;
import com.inapp.cms.entity.HealthCareEntity;
import com.inapp.cms.entity.HealthcareVaccLinkEntity;
import com.inapp.cms.utils.RepositoryConstants;

@Repository(RepositoryConstants.HEALTHCARE_DAO)
public class HealthCareDAOImpl implements HealthCareDAO{
	private static final Logger logger = Logger.getLogger(HealthCareDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public HealthCareEntity saveHealthCare(HealthCareEntity objHealthCareEntity) {
		  try {
			  this.sessionFactory.getCurrentSession().saveOrUpdate(objHealthCareEntity);
	                    
		  }catch(Exception e){
			  logger.error("Error in saveHealthCare Method "+e.getMessage());
			  objHealthCareEntity = null;
		  }
		return objHealthCareEntity;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public BreedKidsEntity saveBreedKids(BreedKidsEntity objBreedKidsEntity) {
		  try {
			  this.sessionFactory.getCurrentSession().saveOrUpdate(objBreedKidsEntity);
	                    
		  }catch(Exception e){
			  logger.error("Error in saveHealthCare Method "+e.getMessage());
			  objBreedKidsEntity = null;
		  }
		return objBreedKidsEntity;
	}


	@SuppressWarnings("unchecked")
	@Transactional
	public List<HealthCareEntity> getHealthCareDetails(HealthCareEntity objHealthCareEntity) {
		
	     String sql = "";
		 List<HealthCareEntity> listHealthCare = null;
		  try {
			  Query query = null;
					if(objHealthCareEntity!=null)  {
					  sql = "from HealthCareEntity where objCattleEntity.cattleid = :cattleid and  servicedate = :servicedate order by healthcareid";
					  query = this.sessionFactory
	                    .getCurrentSession()
	                    .createQuery(sql);
					  query.setParameter("cattleid", objHealthCareEntity.getObjCattleEntity().getCattleid());
					  query.setParameter("servicedate", objHealthCareEntity.getServicedate());
					}
					
					else{
					sql = "from HealthCareEntity order by healthcareid";
						  query = this.sessionFactory
		                    .getCurrentSession()
		                    .createQuery(sql);
					}
			  
			  listHealthCare = query.list(); 

		  }catch(Exception e){
			  e.printStackTrace();
			  logger.error("Error in getHealthCareDetails Method "+e.getMessage());
		  }
		return listHealthCare;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<HashMap<String, Object>> getHealthCareDetailsByRole(HealthCareEntity objHealthCareEntity, String rolename, int userId) {
		
		List<HashMap<String, Object>> aliasToValueMapList = null; Query query = null;
		
		 String sql = "";
		  try {
			  
			  if(rolename.equals("OWNER")){
				  sql = "select healthcare_service_dt,cattle_id,cattle_ear_tag_id,cattle_dob,farm_code,farm_name,user_fname || ' ' || user_lname as vet_name "
				  		+ "from farm.healthcare inner join farm.cattle" 
				  		+ " on(healthcare_cattle_id=cattle_id)  inner join "
				  		+ "usermanagement.vet on (healthcare_vet_id = vet_id) "
				  		+ "inner join usermanagement.user on (vet_user_id = user_id) "
				  		+ "inner join farm.farm on(farm_id = cattle_farm_id) where cattle_farm_id" +
				  		" in(select farm_owner_link_farm_id from farm.farm_owner_link" +
				  		" where farm_owner_link_owner_id = (select owner_id from" +
				  		" usermanagement.owner where owner_user_id = "+userId+")) and  farm.farm_status = true";
			  query = this.sessionFactory
	                    .getCurrentSession()
	                    .createSQLQuery(sql)
	                    .setResultTransformer(
	                            AliasToEntityMapResultTransformer.INSTANCE);
			  
			  }
			  if(rolename.equals("VET")){
				sql =  "select healthcare_service_dt,cattle_id,cattle_ear_tag_id,cattle_dob,farm_code,farm_name from farm.healthcare inner join farm.cattle" +
						" on(healthcare_cattle_id=cattle_id) inner join farm.farm on(farm_id = cattle_farm_id) where cattle_farm_id" +
						" in(select farm_vet_link_farm_id from farm.farm_vet_link" +
						" where farm_vet_link_vet_id = (select vet_id from" +
						" usermanagement.vet where vet_user_id = "+userId+"))   and farm.farm_status = true";
				  query = this.sessionFactory
		                    .getCurrentSession()
		                    .createSQLQuery(sql)
		                    .setResultTransformer(
		                            AliasToEntityMapResultTransformer.INSTANCE); 
			  }
			
	            aliasToValueMapList = query.list(); 

		  }catch(Exception e){
			  e.printStackTrace();
			  logger.error("Error in getHealthCareDetailsByRole Method "+e.getMessage());
		  }
		return aliasToValueMapList;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<HealthcareVaccLinkEntity> getHealthCareTypeDetails(HealthCareEntity objHealthCareEntity) {
		System.out.println("-----------------"+objHealthCareEntity.getHealthcareid());
	     String sql = "from HealthcareVaccLinkEntity where objHealthCareEntity.healthcareid = :healthcareId";
		 List<HealthcareVaccLinkEntity> listHealthCare = null;
		  try {
			  Query query = this.sessionFactory
	                    .getCurrentSession()
	                    .createQuery(sql);
			  query.setParameter("healthcareId", objHealthCareEntity.getHealthcareid());
			  listHealthCare = query.list(); 

		  }catch(Exception e){
			  e.printStackTrace();
			  logger.error("Error in getHealthCareTypeDetails Method "+e.getMessage());
		  }
		return listHealthCare;
	}
	
	
	@Override
	@Transactional
	public int deleteHealthCare(HealthCareEntity objHealthCareEntity){
		int status = 0;
		try {
			String sql = "delete from HealthCareEntity where healthcareid = :healthcareId  ";
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			query.setParameter("healthcareId", objHealthCareEntity.getVetid());
			status = query.executeUpdate();
		} catch (Exception e) {
			status = 0;
			logger.error("Error in deleteHealthCare method "+ e.getMessage());
			
		}
		return status;
	}

	@Override
	@Transactional
	public HealthcareVaccLinkEntity saveHealthCareTypeDetails(
			HealthcareVaccLinkEntity objVaccLinkEntity) {
		 try {
			  this.sessionFactory.getCurrentSession().saveOrUpdate(objVaccLinkEntity);
			  objVaccLinkEntity.setUniquesynckey(objVaccLinkEntity.getVaccid()+""); // for sync
			  this.sessionFactory.getCurrentSession().saveOrUpdate(objVaccLinkEntity);          
		  }catch(Exception e){
			  logger.error("Error in saveHealthCareTypeDetails Method "+e.getMessage());
			  objVaccLinkEntity = null;
		  }
		return objVaccLinkEntity;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<HealthcareVaccLinkEntity> getHealthCareTypeDetailsById(int vaccId) {
	     String sql = "from HealthcareVaccLinkEntity where vaccid = :vaccid";
		 List<HealthcareVaccLinkEntity> listHealthCare = null;
		  try {
			  Query query = this.sessionFactory
	                    .getCurrentSession()
	                    .createQuery(sql);
			  query.setParameter("vaccid", vaccId);
			  listHealthCare = query.list(); 

		  }catch(Exception e){
			  e.printStackTrace();
			  logger.error("Error in getHealthCareTypeDetailsById Method "+e.getMessage());
		  }
		return listHealthCare;
	}

}
