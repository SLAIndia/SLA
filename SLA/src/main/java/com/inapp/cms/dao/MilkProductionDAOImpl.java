package com.inapp.cms.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.utils.RepositoryConstants;
import com.inapp.cms.entity.BreedKidsEntity;
import com.inapp.cms.entity.MilkProductionEntity;

@Repository(RepositoryConstants.MILKPRODUCTION_DAO)
public class MilkProductionDAOImpl implements MilkProductionDAO{
	private static final Logger logger = Logger.getLogger(MilkProductionDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public MilkProductionEntity saveMilkProduction(MilkProductionEntity objMilkProductionEntity) {
		  try {
			  this.sessionFactory.getCurrentSession().saveOrUpdate(objMilkProductionEntity);
	                    
		  }catch(Exception e){
			  logger.error("Error in saveMilkProduction Method "+e.getMessage());
			  objMilkProductionEntity = null;
		  }
		return objMilkProductionEntity;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public BreedKidsEntity saveBreedKids(BreedKidsEntity objBreedKidsEntity) {
		  try {
			  this.sessionFactory.getCurrentSession().saveOrUpdate(objBreedKidsEntity);
	                    
		  }catch(Exception e){
			  logger.error("Error in saveMilkProduction Method "+e.getMessage());
			  objBreedKidsEntity = null;
		  }
		return objBreedKidsEntity;
	}


	@SuppressWarnings("unchecked")
	@Transactional
	public List<MilkProductionEntity> getMilkProductionDetails(MilkProductionEntity objMilkProductionEntity) {
		
	     String sql = ""; Query query  = null;
	     
		 List<MilkProductionEntity> listMilkProduction = null;
		  try {
			  if(objMilkProductionEntity!=null){
			  sql = "from MilkProductionEntity where objdoe.cattleid = :doeId and milkproddt = :milkProdDt and objdoe.cattlegender = :gender order by milkprodid";
			  query = this.sessionFactory
	                    .getCurrentSession()
	                    .createQuery(sql);
			  query.setParameter("doeId", objMilkProductionEntity.getObjdoe().getCattleid());
			  query.setParameter("milkProdDt", objMilkProductionEntity.getMilkproddt());
			  query.setParameter("gender", "F");
			  }
			  else{
				  sql = "from MilkProductionEntity order by milkprodid";
				  query = this.sessionFactory
		                    .getCurrentSession()
		                    .createQuery(sql);  
			  }
			  listMilkProduction = query.list(); 

		  }catch(Exception e){
			  logger.error("Error in getMilkProductionDetails Method "+e.getMessage());
		  }
		return listMilkProduction;
	}

	@Override
	@Transactional
	public int deleteMilkProduction(MilkProductionEntity objMilkProductionEntity){
		int status = 0;
		try {
			String sql = "delete from MilkProductionEntity where milkprodid = :milkproductionId  ";
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			query.setParameter("milkproductionId", objMilkProductionEntity.getMilkprodid());
			status = query.executeUpdate();
		} catch (Exception e) {
			status = 0;
			logger.error("Error in deleteMilkProduction method "+ e.getMessage());
			
		}
		return status;
	}


@SuppressWarnings("unchecked")
@Transactional
public List<MilkProductionEntity> getMilkProductionDetailsByOwner(int userId) {
	
     String sql = ""; Query query  = null;
     
	 List<MilkProductionEntity> listMilkProduction = null;
	  try {
	
		  sql = " from MilkProductionEntity where objdoe.farm.farmid in (select farmOwner.farmid from FarmOwnerLinkEntity farmOwner ,OwnerEntity owner "
+ " where farmOwner.ownerid = owner.id and owner.objUserEntity.id =:userId  ) and objdoe.cattlegender = :gender order by milkprodid";
		  query = this.sessionFactory
                    .getCurrentSession()
                    .createQuery(sql);
		  query.setParameter("userId", userId);
		  query.setParameter("gender", "F");
		  listMilkProduction = query.list(); 

	  }catch(Exception e){
		  logger.error("Error in getMilkProductionDetailsByOwner Method "+e.getMessage());
	  }
	return listMilkProduction;
}

@SuppressWarnings("unchecked")
@Transactional
public List<MilkProductionEntity> getMilkProductionById(MilkProductionEntity objMilkProductionEntity) {
	
     String sql = ""; Query query  = null;
     
	 List<MilkProductionEntity> listMilkProduction = null;
	  try {
		  if(objMilkProductionEntity!=null){
		  sql = "from MilkProductionEntity where milkprodid = :milkprodid and objdoe.cattlegender = :gender order by milkprodid";
		  query = this.sessionFactory
                    .getCurrentSession()
                    .createQuery(sql);
		  query.setParameter("milkprodid", objMilkProductionEntity.getMilkprodid());
		  query.setParameter("gender", "F");
		  }
		  listMilkProduction = query.list(); 

	  }catch(Exception e){
		  logger.error("Error in getMilkProductionById Method "+e.getMessage());
	  }
	return listMilkProduction;
}
@SuppressWarnings("unchecked")
@Transactional
public List<MilkProductionEntity> getMilkProductionDetailsByCattleId(int cattle_id) {
	
     String sql = ""; Query query  = null;
     
	 List<MilkProductionEntity> listMilkProduction = null;
	  try {
		  if(cattle_id!=0){
		  sql = "from MilkProductionEntity where objdoe.cattleid = :doeId and objdoe.cattlegender = :gender";
		  query = this.sessionFactory
                    .getCurrentSession()
                    .createQuery(sql);
		  query.setParameter("doeId", cattle_id);
		  query.setParameter("gender", "F");
		  listMilkProduction = query.list(); 
		  }
		  

	  }catch(Exception e){
		  logger.error("Error in getMilkProductionDetails Method "+e.getMessage());
	  }
	return listMilkProduction;
}

}