package com.inapp.cms.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.utils.RepositoryConstants;
import com.inapp.cms.entity.VaccinationEntity;

@Repository(RepositoryConstants.VACCINATION_DAO)
public class VaccinationDAOImpl implements VaccinationDAO{
	private static final Logger logger = Logger.getLogger(VaccinationDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public VaccinationEntity saveVaccination(VaccinationEntity objVaccinationEntity) {
		  try {
			  String sql = "from VaccinationEntity where vaccinationname"
			  		+ " = :vaccinationname and vaccinationtype = :vaccinationtype and vaccinationdose = :vaccinationdose and "
			  		+ "vaccinationunit = :vaccinationunit and vaccinationstatus = 1";
			  if(null != (objVaccinationEntity.getVaccinationid()) && objVaccinationEntity.getVaccinationid() > 0){
				  sql += " and vaccinationid != :vaccinationid";
			  }
			  Query query = this.sessionFactory
	                    .getCurrentSession()
	                    .createQuery(sql);
			  
			  query.setParameter("vaccinationname", objVaccinationEntity.getVaccinationname());
			  query.setParameter("vaccinationtype", objVaccinationEntity.getVaccinationtype());
			  query.setParameter("vaccinationdose", objVaccinationEntity.getVaccinationdose());
			  query.setParameter("vaccinationunit", objVaccinationEntity.getVaccinationunit());
			  if(null != (objVaccinationEntity.getVaccinationid()) && objVaccinationEntity.getVaccinationid() > 0){
				 query.setParameter("vaccinationid", objVaccinationEntity.getVaccinationid());
			  }
			  List<VaccinationEntity> result = query.list();
			  if(null != result && result.size() >0){
				  objVaccinationEntity =  result.get(0);
				  objVaccinationEntity.setExist(true);
				  return objVaccinationEntity;
			  }
			  this.sessionFactory.getCurrentSession().saveOrUpdate(objVaccinationEntity);
			  objVaccinationEntity.setUniquesynckey(objVaccinationEntity.getVaccinationid().toString()); //for sync
			  this.sessionFactory.getCurrentSession().saveOrUpdate(objVaccinationEntity);
	                    
		  }catch(Exception e){
			  e.printStackTrace();
			  logger.error("Error in saveVaccination Method "+e.getMessage());
			  objVaccinationEntity = null;
		  }
		return objVaccinationEntity;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<VaccinationEntity> getVaccinationDetails(VaccinationEntity objVaccinationEntity) {
		String queryParam = "where vaccinationstatus =1 ";boolean idStatus = false;
		if(null != (objVaccinationEntity.getVaccinationid()) && objVaccinationEntity.getVaccinationid()>0)
		{
			queryParam += " and vaccinationid = "+objVaccinationEntity.getVaccinationid()+"";	
			idStatus = true;
		}
		/*if(objVaccinationEntity.getVaccinationexpdt()!=null)
		{
			if(!idStatus){
				queryParam +=  " and vaccinationexpdt < '"+objVaccinationEntity.getVaccinationexpdt()+"'";	
			}
		}*/
	     String sql = "from VaccinationEntity "+queryParam+" order by vaccinationid";
		 List<VaccinationEntity> listVaccination = null;
		  try {
			  Query query = this.sessionFactory
	                    .getCurrentSession()
	                    .createQuery(sql);
			  
			  listVaccination = query.list(); 

		  }catch(Exception e){
			  logger.error("Error in getVaccinationDetails Method "+e.getMessage());
		  }
		return listVaccination;
	}

	@Override
	@Transactional
	public int deleteVaccination(VaccinationEntity objVaccinationEntity){
		int status = 0;
		try {
			String sql = "update VaccinationEntity set vaccinationstatus=0 where vaccinationid = :vaccinationId  ";
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			query.setParameter("vaccinationId", objVaccinationEntity.getVaccinationid());
			status = query.executeUpdate();
		} catch (Exception e) {
			status = 0;
			logger.error("Error in deleteVaccination method "+ e.getMessage());
			
		}
		return status;
	}

}
