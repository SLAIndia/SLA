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

import com.app.utils.RepositoryConstants;
import com.google.gson.Gson;
import com.inapp.cms.entity.BreedKidsEntity;
import com.inapp.cms.entity.BreedingEntity;

@Repository(RepositoryConstants.BREEDING_DAO)
public class BreedingDAOImpl implements BreedingDAO{
	private static final Logger logger = Logger.getLogger(BreedingDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public BreedingEntity saveBreeding(BreedingEntity objBreedingEntity) {
		  try {
			  this.sessionFactory.getCurrentSession().saveOrUpdate(objBreedingEntity);
	                    
		  }catch(Exception e){
			  logger.error("Error in saveBreeding Method "+e.getMessage());
			  objBreedingEntity = null;
		  }
		return objBreedingEntity;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public BreedKidsEntity saveBreedKids(BreedKidsEntity objBreedKidsEntity) {
		  try {
			  System.out.println("breed kids===="+new Gson().toJson(objBreedKidsEntity));
			  this.sessionFactory.getCurrentSession().saveOrUpdate(objBreedKidsEntity);
	                    
		  }catch(Exception e){
			  logger.error("Error in saveBreeding Method "+e.getMessage());
			  objBreedKidsEntity = null;
		  }
		return objBreedKidsEntity;
	}


	@SuppressWarnings("unchecked")
	@Transactional
	public List<BreedingEntity> getBreedingDetails(BreedingEntity objBreedingEntity) {
		String sql = "";
		 if(objBreedingEntity.getBreedingid()!=null && objBreedingEntity.getBreedingid()>0){
	     sql = "from BreedingEntity where id ="+objBreedingEntity.getBreedingid()+" order by breedingid";
		 }
		 else
		 {
		 sql = "from BreedingEntity order by breedingid";	 
		 }
		 List<BreedingEntity> listBreeding = null;
		  try {
			  Query query = this.sessionFactory
	                    .getCurrentSession()
	                    .createQuery(sql);
			  
			  listBreeding = query.list(); 

		  }catch(Exception e){
			  logger.error("Error in getBreedingDetails Method "+e.getMessage());
		  }
		return listBreeding;
	}

	@Override
	@Transactional
	public int deleteBreeding(BreedingEntity objBreedingEntity){
		int status = 0;
		try {
			String sql = "delete from BreedingEntity where breedingid = :breedingId  ";
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			query.setParameter("breedingId", objBreedingEntity.getBreedingid());
			status = query.executeUpdate();
		} catch (Exception e) {
			status = 0;
			logger.error("Error in deleteBreeding method "+ e.getMessage());
			
		}
		return status;
	}
	
	

	@SuppressWarnings("unchecked")
	@Transactional
	public List<HashMap<String, Object>> getBreedKidDetails(BreedingEntity objBreedingEntity) {
		 String sql = "SELECT * FROM farm.fn_breed_kid_sel(:breedingid,:breedkidid,:farmid, :cattleId) "; //where cattle_status = true [commented to show all cattle]
		 List<HashMap<String, Object>> aliasToValueMapList = null;
		  try {
			  
				Query query = this.sessionFactory
                .getCurrentSession()
                .createSQLQuery(sql)
                .setResultTransformer(
                        AliasToEntityMapResultTransformer.INSTANCE);
		query.setParameter("breedingid",objBreedingEntity.getBreedingid());
		query.setParameter("breedkidid",objBreedingEntity.getBreedkidid()); // 0 list all kids in the breed
		query.setParameter("farmid",0);// 0 list from all farms
		query.setParameter("cattleId",0);// 0 list from all kid cattle
		
		aliasToValueMapList = query.list(); 


		  }catch(Exception e){
			 e.printStackTrace();
			  logger.error("Error in getBreedingDetails Method "+e.getMessage());
		  }
		return aliasToValueMapList;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<HashMap<String, Object>> getBreedingDetailsByAssignedFarmUser(BreedingEntity objBreedingEntity, int userId, int farmId) {
		 String sql = "SELECT * FROM farm.fn_breeding_sel(:breedingid,:userid,:farmid)";
		 List<HashMap<String, Object>> aliasToValueMapList = null;
		  try {
			  
				Query query = this.sessionFactory
                .getCurrentSession()
                .createSQLQuery(sql)
                .setResultTransformer(
                        AliasToEntityMapResultTransformer.INSTANCE);
		query.setParameter("breedingid",objBreedingEntity.getBreedingid()); // 0 list all breeding details
		query.setParameter("userid",userId); // 0 list all kids in the breed
		query.setParameter("farmid",farmId);// 0 list from all farms
		
		aliasToValueMapList = query.list(); 


		  }catch(Exception e){
			  e.printStackTrace();
			  logger.error("Error in getBreedingDetailsByAssignedFarmUser Method "+e.getMessage());
		  }
		return aliasToValueMapList;
	}

	
	@Override
	@Transactional
	public BreedingEntity updateParents(BreedingEntity objBreedingEntity) {

		String sqlkid = "select breedkidscattleid from BreedKidsEntity where objBreedingEntity.breedingid = :breedingId";
		List<Integer> breedKidCattleIds = null;
		String sql = "update CattleEntity set buckcattle.cattleid = :buckId , doecattle.cattleid = :doeId, updateddt = :updateddt where cattleid = :breedKidCattleId ";
		try {
			Query queryKid = this.sessionFactory.getCurrentSession()
					.createQuery(sqlkid);
			queryKid.setParameter("breedingId",
					objBreedingEntity.getBreedingid());
			breedKidCattleIds = queryKid.list();
			if (breedKidCattleIds != null && breedKidCattleIds.size() > 0) {
				for (int kidcattleid : breedKidCattleIds) {
					Query query = this.sessionFactory.getCurrentSession()
							.createQuery(sql);
					query.setParameter("buckId",
							objBreedingEntity.getBreedingbuckid());
					query.setParameter("doeId",
							objBreedingEntity.getBreedingdoeid());
					query.setParameter("breedKidCattleId",
							kidcattleid);
					query.setParameter("updateddt",
							objBreedingEntity.getUpdateddt());
					query.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in updateParents Method "
					+ e.getMessage());
		}
		return objBreedingEntity;
	}

	@Override
	@Transactional
	public List<HashMap<String, Object>> getBreedingDetailsofDate(
			BreedingEntity objBreedingEntity, int u_id, int farm_id) {
		 String sql = "SELECT * FROM farm.fn_breeding_sel(:breedingid,:userid,:farmid) "
		 		+ "where breeding_buck_id = :buck_id"
		 		+ " and breeding_doe_id = :doe_id and breeding_date =:breeding_date";
		 List<HashMap<String, Object>> aliasToValueMapList = null;
		  try {
			  
				Query query = this.sessionFactory
                .getCurrentSession()
                .createSQLQuery(sql)
                .setResultTransformer(
                        AliasToEntityMapResultTransformer.INSTANCE);
		query.setParameter("breedingid",0); // 0 list all breeding details
		query.setParameter("userid",u_id); // 0 list all kids in the breed
		query.setParameter("farmid",farm_id);// 0 list from all farms
		query.setParameter("buck_id",objBreedingEntity.getBreedingbuckid());
		query.setParameter("doe_id",objBreedingEntity.getBreedingdoeid());
		query.setParameter("breeding_date",objBreedingEntity.getBreedingdate());
		
		aliasToValueMapList = query.list(); 


		  }catch(Exception e){
			  e.printStackTrace();
			  logger.error("Error in getBreedingDetailsByAssignedFarmUser Method "+e.getMessage());
		  }
		return aliasToValueMapList;
		
	}



}
