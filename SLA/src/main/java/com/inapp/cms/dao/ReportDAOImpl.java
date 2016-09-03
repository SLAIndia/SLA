package com.inapp.cms.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.utils.RepositoryConstants;
import com.inapp.cms.entity.CattleEntity;
import com.inapp.cms.entity.MilkProductionEntity;
import com.inapp.cms.entity.ReportEntity;

@Repository(RepositoryConstants.REPORT_DAO)
public class ReportDAOImpl implements ReportDAO{
	
	private static Logger logger = Logger.getLogger(ReportDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<MilkProductionEntity> getMilkProdData(ReportEntity reportObj) {
		 List<MilkProductionEntity> milkProdList = null;
		
		String sql = "from MilkProductionEntity  where milkproddt between :fromDt and :toDt ";
		if(reportObj.getCattleId()>0){
			sql += " and objdoe.cattleid =:cattleId ";
		}else{
			sql += " and objdoe.farm.farmid =:farmid ";
		}
		sql += " order by objdoe.farm.farmid,objdoe.cattleid ";
		try{
		Query query = this.sessionFactory.getCurrentSession()
				.createQuery(sql);
		query.setParameter("fromDt",reportObj.getFromDt());
		query.setParameter("toDt",reportObj.getToDt());
		if(reportObj.getCattleId()>0){
			query.setParameter("cattleId",reportObj.getCattleId());
		}else{
			query.setParameter("farmid",reportObj.getFarmId());
		}
		milkProdList = query.list();
		}catch(Exception e){
			logger.error("Error in getMilkProdData"+e.getMessage());
		}
		return milkProdList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> getHealthCareData(ReportEntity reportObj) {
		List<Object[]> resList = null;
		String sql = " select linkObj,vet from HealthcareVaccLinkEntity linkObj,"
				+ " VetEntity vet "
				+ " where linkObj.objHealthCareEntity.vetid = vet.vetid "
				+ " and linkObj.objHealthCareEntity.objCattleEntity.cattleid =:cattleid "
				+ "and linkObj.objHealthCareEntity.servicedate between :fromDt and :toDt ";
		if(reportObj.getVaccineType() !=2){
			sql += " and vacchctype =:vaccType ";
		}
		sql += "order by linkObj.objHealthCareEntity.healthcareid";
		try{
			Query query = this.sessionFactory.getCurrentSession()
					.createQuery(sql);
			query.setParameter("fromDt",reportObj.getFromDt());
			query.setParameter("toDt",reportObj.getToDt());
			query.setParameter("cattleid", reportObj.getCattleId());
			if(reportObj.getVaccineType() !=2){
				query.setParameter("vaccType", reportObj.getVaccineType());
			}
			resList = query.list();
			
			//System.out.println("output "+new Gson().toJson(resList));
		}catch(Exception e){
			logger.error("Error in getHealthCareData"+e.getMessage());
		}
		return resList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<CattleEntity> getCattleDtls(ReportEntity reportObj) {
		List<CattleEntity> cattleList = null;
		String sql = " from CattleEntity where cattledob between :fromDt and :toDt and farm.farmstatus = true ";
		if(reportObj.getFarmId()>0){
			sql += " and farm.farmid =:farmId ";
		}else{
			sql += " and farm.farmid in (select farmOwner.farmid from FarmOwnerLinkEntity farmOwner ,OwnerEntity owner "
				+ " where farmOwner.ownerid = owner.id and owner.objUserEntity.id =:id )";
		}
		sql += "order by farm.farmid,cattleid";
		try{
			Query query = this.sessionFactory.getCurrentSession()
					.createQuery(sql);
			query.setParameter("fromDt",reportObj.getFromDt());
			query.setParameter("toDt",reportObj.getToDt());
			if(reportObj.getFarmId()>0){
				query.setParameter("farmId", reportObj.getFarmId());
			}else{
				query.setParameter("id", reportObj.getUserId());
			}
			cattleList = query.list();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error in getCattleDtls"+e.getMessage());
		}
		return cattleList;
	}
	
}
