
package com.app.hospital.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.hospital.entity.SpecializationsEntity;
import com.app.usermanagement.entity.UserDetailsEntity;
import com.app.utils.RepositoryConstants;

@Repository(RepositoryConstants.SPECIALIZATIONS_DAO)
public class SpecializationsDaoImpl implements SpecializationsDao{
	private static final Logger logger = Logger.getLogger(SpecializationsDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public SpecializationsEntity saveSpecializations(SpecializationsEntity objSpecializations) throws Exception {
		try {	
			this.sessionFactory.getCurrentSession().saveOrUpdate(objSpecializations);			
		} catch (Exception e) {
			logger.error("Error in saveSpecializations " + e.getMessage(), e);
		}
		return objSpecializations;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<SpecializationsEntity> getSpecializations() {

		List<SpecializationsEntity> result =null;
		try {
			result =  this.sessionFactory.getCurrentSession().createCriteria(SpecializationsEntity.class).list();
		} catch (Exception e) {
			logger.error("Error in getSpecialization Method " + e.getMessage());
		}
		return result;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public SpecializationsEntity getSpecialization(long pki_hos_dept_type_id) {

		String sql = "from SpecializationsEntity where pki_hos_dept_type_id =:pki_hos_dept_type_id";
		SpecializationsEntity obj =null;
		try {
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			query.setParameter("pki_hos_dept_type_id", pki_hos_dept_type_id);
			List<SpecializationsEntity> result =  query.list();
			if(result != null && result.size()>0){
				obj = (SpecializationsEntity) result.get(0);
			}
		} catch (Exception e) {
			logger.error("Error in getSpecialization Method " + e.getMessage());
		}
		return obj;

	}

	@Override
	@Transactional
	public int deleteSpecialization(long pki_hos_dept_type_id){
		int status = 0;
	
		try {
			
			String sql = "delete SpecializationsEntity where pki_hos_dept_type_id = :pki_hos_dept_type_id  ";
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			query.setParameter("pki_hos_dept_type_id", pki_hos_dept_type_id);
			status = query.executeUpdate();
		} catch (Exception e) {
			status = 0;
			logger.error("Error in deleteSpecialization method "+ e.getMessage());
			
		}
		return status;
	}

}
