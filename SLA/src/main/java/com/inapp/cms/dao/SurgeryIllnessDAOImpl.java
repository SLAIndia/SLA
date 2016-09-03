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
import com.inapp.cms.entity.SurgeryIllnessDetailEntity;
import com.inapp.cms.entity.SurgeryIllnessEntity;

@Repository(RepositoryConstants.SURGERY_DAO)
public class SurgeryIllnessDAOImpl implements SurgeryIllnessDAO {
	private static final Logger logger = Logger.getLogger(SurgeryIllnessDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<HashMap<String, Object>> getSurgeryMaster() {
		String sql = " select surgery_id,surgery_name from master.surgery where surgery_status = 1";
		List<HashMap<String, Object>> aliasToValueMapList = null;
		 try {
			  Query query = this.sessionFactory
	                    .getCurrentSession()
	                    .createSQLQuery(sql)
	                    .setResultTransformer(
	                            AliasToEntityMapResultTransformer.INSTANCE);
			  aliasToValueMapList = query.list();
			  
		 }catch(Exception e){
			 logger.error("error in getSurgeryMaster method"+e.getMessage());
		 }
		return aliasToValueMapList;
	}

	@Override
	@Transactional
	public List<SurgeryIllnessEntity> getSurgeryIllness(
		SurgeryIllnessEntity objSurgeryIllnessEntity,int uId) {
		List<SurgeryIllnessEntity> surgeryList = null;
		
		String sql = "";Query query = null;
		 try {
			 if(objSurgeryIllnessEntity!=null){
				 sql = " from SurgeryIllnessEntity where surgillprocdt = :surgillprocdt and cattle.cattleid = :cattleid order by surgillid";
				  query = this.sessionFactory
	                    .getCurrentSession()
	                    .createQuery(sql);
			 query.setParameter("surgillprocdt", objSurgeryIllnessEntity.getSurgillprocdt());
			 query.setParameter("cattleid", objSurgeryIllnessEntity.getCattle().getCattleid());
			 }
			 else{
				 sql = " from SurgeryIllnessEntity where vet.objUserEntity.id = :id order by surgillid";
				  query = this.sessionFactory
	                    .getCurrentSession()
	                    .createQuery(sql);
				  query.setParameter("id", uId);
			 }
			 surgeryList = query.list();
			  
		 }catch(Exception e){
			 e.printStackTrace();
			 logger.error("error in getSurgeryMaster method"+e.getMessage());
		 }
		return surgeryList;
	}

	@Override
	@Transactional
	public List<SurgeryIllnessDetailEntity> getSurgeryIllnessDetail(
			SurgeryIllnessEntity objSurgeryIllnessEntity) {
		List<SurgeryIllnessDetailEntity> surgeryDetailList = null;
		String sql = " from SurgeryIllnessDetailEntity where surgIllness.surgillid = :surgillid order by surgilldetid";
		 try {
			 Query query = this.sessionFactory
	                    .getCurrentSession()
	                    .createQuery(sql);
			 query.setParameter("surgillid", objSurgeryIllnessEntity.getSurgillid());
			 surgeryDetailList = query.list();
			 System.out.println("surgeryDetailList==="+objSurgeryIllnessEntity.getSurgillid());
			  
		 }catch(Exception e){
			 e.printStackTrace();
			 logger.error("error in getSurgeryIllnessDetail method"+e.getMessage());
		 }
		return surgeryDetailList;
	}

	@Override
	@Transactional
	public SurgeryIllnessEntity saveSurgeryIllness(
			SurgeryIllnessEntity objSurgeryIllnessEntity) {
		 try {
			  this.sessionFactory.getCurrentSession().saveOrUpdate(objSurgeryIllnessEntity);
	                    
		  }catch(Exception e){
			  e.printStackTrace();
			  logger.error("Error in saveSurgeryIllness Method "+e.getMessage());
			  objSurgeryIllnessEntity = null;
		  }
		return objSurgeryIllnessEntity;
	}
	
	@Override
	@Transactional
	public boolean saveSurgeryIllnessDetail(List<SurgeryIllnessDetailEntity> listDetail, SurgeryIllnessEntity objSurgeryIllnessEntity) {
		boolean result = false; 
		 try {
				String sql = "delete from SurgeryIllnessDetailEntity where surgIllness.surgillid = :surgillid  ";
				Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
				query.setParameter("surgillid", objSurgeryIllnessEntity.getSurgillid());
				int status = query.executeUpdate();
			if(listDetail!=null && listDetail.size()>0){	
			for (SurgeryIllnessDetailEntity objSurgeryIllnessDetailEntity : listDetail) {
				objSurgeryIllnessDetailEntity
						.setSurgIllness(objSurgeryIllnessEntity);
				if(status >0){
					objSurgeryIllnessDetailEntity.setDeleteddt(Common.getCurrentGMTTimestamp());
				}
				objSurgeryIllnessDetailEntity.setCreateddt(Common.getCurrentGMTTimestamp());
				objSurgeryIllnessDetailEntity.setUniquesynckey(objSurgeryIllnessEntity.getSurgillid().toString());
				this.sessionFactory.getCurrentSession().saveOrUpdate(
						objSurgeryIllnessDetailEntity);
				
				
				result = true;
			}
			} else{
				result = true;
			}
		  }catch(Exception e){
			  logger.error("Error in saveSurgeryIllnessDetail Method "+e.getMessage());
			  result = false;
		  }
		return result;
	}

	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<SurgeryIllnessEntity> getSurgeryIllnessData(int uId){
		List<SurgeryIllnessEntity> surgeryList = null;
		String sql = "select surgery from SurgeryIllnessEntity surgery  join surgery.cattle as cattle "
				+ " where surgery.cattle.farm.farmid in "
				+ " (select farmOwner.farmid from FarmOwnerLinkEntity farmOwner ,OwnerEntity owner "
				+ " where farmOwner.ownerid = owner.id and owner.objUserEntity.id =:id )";
		try{
			Query  query = this.sessionFactory
		              .getCurrentSession()
		              .createQuery(sql);
			query.setParameter("id", uId);
			surgeryList = query.list();
			
		}catch(Exception e){
			logger.error("Error in getSurgeryIllnessData Method "+e.getMessage());
		}
		return surgeryList;
	}

}
