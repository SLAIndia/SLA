package com.inapp.cms.dao;

import java.util.ArrayList;
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
import com.google.gson.Gson;
import com.inapp.cms.entity.CattleEntity;
import com.inapp.cms.entity.CattleImageEntity;
import com.inapp.cms.entity.FarmEntity;

@Repository(RepositoryConstants.CATTLE_DAO)
public class CattleDAOImpl implements CattleDAO{

	private static final Logger logger = Logger.getLogger(CattleDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<CattleEntity> getCattleDetailsByFarm(int farm_id) {
		
		String sql = "from CattleEntity  where farm.farmid =:farm_id and farm.farmstatus = true order by cattleid";
		if(farm_id == -1){
			sql = "from CattleEntity where  farm.farmstatus = true order by farm.farmid,cattleid" ;
		}
		
		List<CattleEntity> result = null;
		try {
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			if(farm_id != -1){
				query.setParameter("farm_id",farm_id);
			}
			result =  query.list();
			
		} catch (Exception e) {
			logger.error("Error in getCattleDetailsByFarm Method " + e.getMessage());
		}

		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<HashMap<String, Object>>  getCattleDetailsOfAssignedFarm(int u_id) {
		
		 String sql = "SELECT * FROM farm.fn_assigned_farm_cattle_by_user_sel(:userId)";
		 List<HashMap<String, Object>> aliasToValueMapList = null;
		try {
			
			Query query = this.sessionFactory
                    .getCurrentSession()
                    .createSQLQuery(sql)
                    .setResultTransformer(
                            AliasToEntityMapResultTransformer.INSTANCE);
			query.setParameter("userId",u_id);
			
			aliasToValueMapList = query.list(); 
			
		} catch (Exception e) {
			logger.error("Error in getCattleDetailsOfAssignedFarm Method " + e.getMessage());
		}

		return aliasToValueMapList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<HashMap<String, Object>>  getCattleDetailsOfAssignedFarmAndBreedDt(int u_id, String breeddt) {
		System.out.println("breed date========="+breeddt);
		 String sql = "SELECT * FROM farm.fn_assigned_farm_cattle_by_user_sel(:userId) where cattle_dob < '"+Common.getFormatedSqlDate(breeddt)+"'";
		 List<HashMap<String, Object>> aliasToValueMapList = null;
		try {
			
			Query query = this.sessionFactory
                    .getCurrentSession()
                    .createSQLQuery(sql)
                    .setResultTransformer(
                            AliasToEntityMapResultTransformer.INSTANCE);
			query.setParameter("userId",u_id);
			
			aliasToValueMapList = query.list(); 
			
		} catch (Exception e) {
			logger.error("Error in getCattleDetailsOfAssignedFarmAndBreedDt Method " + e.getMessage());
		}

		return aliasToValueMapList;
	}



	@SuppressWarnings("unchecked")
	@Transactional
	public HashMap<String, Object> saveCattleDetails(CattleEntity objCattleEntity) {
		
		List<HashMap<String, Object>> aliasToValueMapList = null;
		
		 String sql = "SELECT cattle_id,cattle_ear_tag_id FROM farm.fn_cattle_save(:cattleid,:cattlefarmid,:cattledob,:cattlegender,:cattlelocation,:cattlecategory,:cattlestatus,:cattlebuckid,:cattledoeid,:cattlecreateddate,:cattlelatt,:cattlelong,:cattlelandmark,:createddt,:cattlecode)";
		 Gson f =new Gson();
		 logger.info("cattle entity for save : "+f.toJson(objCattleEntity));
		  try {
			  Query query = this.sessionFactory
	                    .getCurrentSession()
	                    .createSQLQuery(sql)
	                    .setResultTransformer(
	                            AliasToEntityMapResultTransformer.INSTANCE);
			 
	           
			    query.setParameter("cattleid", objCattleEntity.getCattleid());
	            query.setParameter("cattlefarmid", objCattleEntity.getFarm().getFarmid());
	            query.setParameter("cattledob",objCattleEntity.getCattledob());
	            query.setParameter("cattlegender", objCattleEntity.getCattlegender());
	            query.setParameter("cattlelocation",objCattleEntity.getCattlelocation());
	            query.setParameter("cattlecategory",objCattleEntity.getCattlecategory());
	            query.setParameter("cattlestatus",objCattleEntity.isCattlestatus());
	            if(objCattleEntity.getBuckcattle() != null ){
	            	 query.setParameter("cattlebuckid",(objCattleEntity.getBuckcattle().getCattleid()));
	            }else{
	            	 query.setParameter("cattlebuckid",0);
	            }
	            if(objCattleEntity.getDoecattle() != null ){
	            	 query.setParameter("cattledoeid",(objCattleEntity.getDoecattle().getCattleid()));
	            }else{
	            	 query.setParameter("cattledoeid",0);
	            }
	            query.setParameter("cattlecreateddate",Common.getCurrentTimestamp());
	            query.setParameter("cattlelatt", (objCattleEntity.getCattlelatt() == null ? "null" : objCattleEntity.getCattlelatt()));
	            query.setParameter("cattlelong", (objCattleEntity.getCattlelong() == null ? "null" : objCattleEntity.getCattlelong()));
	            query.setParameter("cattlelandmark", (objCattleEntity.getCattlelandmark() == null ? "null" : objCattleEntity.getCattlelandmark()));
	            query.setParameter("createddt", (objCattleEntity.getCreateddt() == null ? "null" : objCattleEntity.getCreateddt()));
	            query.setParameter("cattlecode", (objCattleEntity.getCattlecode() == null ? "null" : objCattleEntity.getCattlecode()));
	            aliasToValueMapList = query.list(); 

		  }catch(Exception e){
			  e.printStackTrace();
			  logger.error("Error in saveCattleDetails Method "+e.getMessage());
		  }
		return aliasToValueMapList.get(0);
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public CattleEntity getCattleDetails(CattleEntity objCattleEntity) {
		String sql = "from CattleEntity  where cattleid =:cattle_id and farm.farmstatus = true";
		CattleEntity obj =null;
		try {
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			query.setParameter("cattle_id", objCattleEntity.getCattleid());
			List<CattleEntity> result =  query.list();
			if(result != null && result.size()>0){
				obj = (CattleEntity) result.get(0);
			}
		} catch (Exception e) {
			logger.error("Error in getCattleDetails Method " + e.getMessage());
		}
		return obj;
	}


	@Override
	@Transactional
	public int saveImageUrl(ArrayList<CattleImageEntity> objCattleImgList) {
		
		//String delSql = "delete CattleImageEntity where cattleimagecattleid = :cattle_image_cattle_id";
		int status = 1;
	//	int delStatus = 0;
		try {
			for (int i = 0; i < objCattleImgList.size(); i++) {
				CattleImageEntity objCattleImg = objCattleImgList.get(i);
				/*if (i == 0) {
					Query delquery = this.sessionFactory.getCurrentSession()
							.createQuery(delSql);
					delquery.setParameter("cattle_image_cattle_id",
							objCattleImg.getCattleimagecattleid());
					delStatus = delquery.executeUpdate();
				}
				if(delStatus > 0){
					objCattleImg.setDeleteddt(Common.getCurrentGMTTimestamp());
				}*/
				objCattleImg.setCreateddt(Common.getCurrentGMTTimestamp());
				objCattleImg.setUniquesynckey(objCattleImg.getCattle_ear_tag());
				this.sessionFactory.getCurrentSession().save(objCattleImg);
			}
		} catch (Exception e) {
			logger.error("Error in getCattleDetails Method " + e.getMessage());
			status = 0;
		}
		return status;
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<CattleImageEntity> getImageUrls(int cattleid) {
		String sql = "from CattleImageEntity  where cattleimagecattleid = :cattle_id and imgstatus = 1";
		List<CattleImageEntity> result = null;
		try {
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			query.setParameter("cattle_id", cattleid);
			result =  query.list();
			
		} catch (Exception e) {
			logger.error("Error in getImageUrls Method " + e.getMessage());
		}
		return result;
	}


	@Override
	@Transactional
	public int  deleteImageUrl(int cattleId) {
		int status = 0;
	try{
		String delSql = "delete CattleImageEntity where cattleimagecattleid = :cattle_id";
		Query delquery = this.sessionFactory.getCurrentSession()
				.createQuery(delSql);
		delquery.setParameter("cattle_id",cattleId);
		status = delquery.executeUpdate();
		}catch(Exception e){
			status = 0;
			logger.error("Error in deleteImageUrl Method " + e.getMessage());
		}
	return status;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<HashMap<String, Object>> getParentByFarmBithdt(CattleEntity objCattleEntity) {
		
		String sql = "";
		if(null != objCattleEntity.getCattleid() &&  objCattleEntity.getCattleid()>0){
		sql = "select * from farm.cattle inner join farm.farm on(cattle_farm_id = farm_id) where cattle_farm_id =:farm_id" +
				" and farm_status = true and cattle_dob < :cattledob and cattle_id not in (select cattle_id from farm.cattle where cattle_buck_id = :cattleId or cattle_doe_id = :cattleId  ) order by cattle_id";}
		else{
			sql = "select * from farm.cattle inner join farm.farm on(cattle_farm_id = farm_id) where cattle_farm_id =:farm_id" +
					" and farm_status = true and cattle_dob < :cattledob  order by cattle_id";	
		}
		
		 List<HashMap<String, Object>> aliasToValueMapList = null;
		try {
			Query query = this.sessionFactory
                    .getCurrentSession()
                    .createSQLQuery(sql)
                    .setResultTransformer(
                            AliasToEntityMapResultTransformer.INSTANCE);
			query.setParameter("farm_id",objCattleEntity.getFarm().getFarmid());
			query.setParameter("cattledob",objCattleEntity.getCattledob());
			if(objCattleEntity.getCattleid()>0){
			query.setParameter("cattleId",objCattleEntity.getCattleid());
			}
			
			aliasToValueMapList = query.list(); 
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getCattleDetailsByFarm Method " + e.getMessage());
		}

		return aliasToValueMapList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public FarmEntity getFarm(String cattle_farm_code) {
		String sql ="from FarmEntity  where farmcode =:farm_code";
		List<FarmEntity> result = null;
		try{
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			query.setParameter("farm_code", cattle_farm_code);
			result =  query.list();
			
		}catch(Exception e){
			logger.error("Error in getFarm Method " + e.getMessage());
		}
		if(null != result && result.size()>0){
			return result.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public CattleEntity getParentCattle(String ear_tag_id) {
		
		String sql ="from CattleEntity  where cattleeartagid =:ear_tag_id";
		List<CattleEntity> result = null;
		try{
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			query.setParameter("ear_tag_id", ear_tag_id);
			result =  query.list();
			
		}catch(Exception e){
			logger.error("Error in getParentCattle Method " + e.getMessage());
		}
		if(null != result && result.size()>0){
			return result.get(0);
		}
		return null;
	}			
		
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public String generateEarTag(String farmcode) {
		
		String sql ="select * from farm.fn_generate_eartag (:farm_code)";
		List<String> result = null;
		try{
			Query query = this.sessionFactory.getCurrentSession()
                    .createSQLQuery(sql);
			query.setParameter("farm_code", farmcode);
			result =  query.list();
			
		}catch(Exception e){
			logger.error("Error in generateEarTag Method " + e.getMessage());
		}
		if(null != result && result.size()>0){
			return result.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public void updateDeleteStatus(String[] deletedImages, int cattleId) {
		
		String sql = "update CattleImageEntity set imgstatus = 0 , updateddt = :updated_dt ,deleteddt =:updated_dt "
				+ "where cattleimagecattleid = :cattle_id and cattleimageurl =:url";
		Query query = null;
		try{
			for(int i = 0 ;i< deletedImages.length ; i++){
				query = this.sessionFactory.getCurrentSession()
						.createQuery(sql);
				query.setParameter("cattle_id", cattleId);
				query.setParameter("url", deletedImages[i]);
				query.setParameter("updated_dt", Common.getCurrentGMTTimestamp());
				query.executeUpdate();
			}
			 
		}catch(Exception e){
			logger.error(""+e.getMessage());
		}
		
	}

	@Override
	@Transactional
	public long getImageCount(int cattleId) {
		long count = 0;
		String sql = "select count(*) from CattleImageEntity where cattleimagecattleid = :cattle_id ";
		Query query = this.sessionFactory.getCurrentSession()
				.createQuery(sql);
		query.setParameter("cattle_id", cattleId);
		 count = (Long)query.uniqueResult();
		return count;
	}			
		
}
