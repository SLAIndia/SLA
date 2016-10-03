
package com.app.hospital.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.hospital.entity.QualificationsEntity;
import com.app.utils.RepositoryConstants;

@Repository(RepositoryConstants.DOCTOR_QUAL_LINK_DAO)
public class DoctorQualLinkDaoImpl implements QualificationsDao{
	private static final Logger logger = Logger.getLogger(DoctorQualLinkDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public QualificationsEntity saveQualifications(QualificationsEntity objQualifications) throws Exception {
		try {	
			this.sessionFactory.getCurrentSession().saveOrUpdate(objQualifications);			
		} catch (Exception e) {
			logger.error("Error in saveQualifications " + e.getMessage(), e);
		}
		return objQualifications;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<QualificationsEntity> getQualifications() {

		List<QualificationsEntity> result =null;
		try {
			result =  this.sessionFactory.getCurrentSession().createCriteria(QualificationsEntity.class).list();
		} catch (Exception e) {
			logger.error("Error in getQualification Method " + e.getMessage());
		}
		return result;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public QualificationsEntity getQualification(long pki_doctor_qualif_master_id) {

		String sql = "from QualificationsEntity where pki_doctor_qualif_master_id =:pki_doctor_qualif_master_id";
		QualificationsEntity obj =null;
		try {
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			query.setParameter("pki_doctor_qualif_master_id", pki_doctor_qualif_master_id);
			List<QualificationsEntity> result =  query.list();
			if(result != null && result.size()>0){
				obj = (QualificationsEntity) result.get(0);
			}
		} catch (Exception e) {
			logger.error("Error in getQualification Method " + e.getMessage());
		}
		return obj;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public QualificationsEntity getQualificationByName(String uvc_qualif_name) {

		String sql = "from QualificationsEntity where uvc_qualif_name =:uvc_qualif_name";
		QualificationsEntity obj =null;
		try {
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			query.setParameter("uvc_qualif_name", uvc_qualif_name);
			List<QualificationsEntity> result =  query.list();
			if(result != null && result.size()>0){
				obj = (QualificationsEntity) result.get(0);
			}
		} catch (Exception e) {
			logger.error("Error in getQualification Method " + e.getMessage());
		}
		return obj;

	}
	
	@Override
	@Transactional
	public int deleteQualification(long pki_doctor_qualif_master_id){
		int status = 0;
	
		try {
			
			String sql = "delete QualificationsEntity where pki_doctor_qualif_master_id = :pki_doctor_qualif_master_id  ";
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			query.setParameter("pki_doctor_qualif_master_id", pki_doctor_qualif_master_id);
			status = query.executeUpdate();
		} catch (Exception e) {
			status = 0;
			logger.error("Error in deleteQualification method "+ e.getMessage());
			
		}
		return status;
	}

}
