
package com.app.hospital.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.hospital.entity.DoctorQualLinkEntity;
import com.app.utils.RepositoryConstants;

@Repository(RepositoryConstants.DOCTOR_QUAL_LINK_DAO)
public class DoctorQualLinkDaoImpl implements DoctorQualLinkDao{
	private static final Logger logger = Logger.getLogger(DoctorQualLinkDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public DoctorQualLinkEntity saveDoctorQualLink(DoctorQualLinkEntity objDoctorQualLink) throws Exception {
		try {	
			this.sessionFactory.getCurrentSession().saveOrUpdate(objDoctorQualLink);			
		} catch (Exception e) {
			logger.error("Error in saveDoctorQualLink " + e.getMessage(), e);
		}
		return objDoctorQualLink;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<DoctorQualLinkEntity> getDoctorQualLink(long pki_doctor_id) {

		List<DoctorQualLinkEntity> result =null;
		try {
			result =  this.sessionFactory.getCurrentSession().createCriteria(DoctorQualLinkEntity.class).list();
		} catch (Exception e) {
			logger.error("Error in getDoctorQualLink Method " + e.getMessage());
		}
		return result;

	}

	/*@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public DoctorQualLinkEntity getDoctorQualLink(long pki_doctor_qualif_master_id) {

		String sql = "from DoctorQualLinkEntity where pki_doctor_qualif_master_id =:pki_doctor_qualif_master_id";
		DoctorQualLinkEntity obj =null;
		try {
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			query.setParameter("pki_doctor_qualif_master_id", pki_doctor_qualif_master_id);
			List<DoctorQualLinkEntity> result =  query.list();
			if(result != null && result.size()>0){
				obj = (DoctorQualLinkEntity) result.get(0);
			}
		} catch (Exception e) {
			logger.error("Error in getDoctorQualLink Method " + e.getMessage());
		}
		return obj;

	}*/

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public DoctorQualLinkEntity getDoctorQualLinkByName(String uvc_qualif_name) {

		String sql = "from DoctorQualLinkEntity where uvc_qualif_name =:uvc_qualif_name";
		DoctorQualLinkEntity obj =null;
		try {
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			query.setParameter("uvc_qualif_name", uvc_qualif_name);
			List<DoctorQualLinkEntity> result =  query.list();
			if(result != null && result.size()>0){
				obj = (DoctorQualLinkEntity) result.get(0);
			}
		} catch (Exception e) {
			logger.error("Error in getDoctorQualLink Method " + e.getMessage());
		}
		return obj;

	}
	
	@Override
	@Transactional
	public int deleteDoctorQualLink(long pki_doctor_qualif_master_id){
		int status = 0;
	
		try {
			
			String sql = "delete DoctorQualLinkEntity where pki_doctor_qualif_master_id = :pki_doctor_qualif_master_id  ";
			Query query = this.sessionFactory.getCurrentSession().createQuery(sql);
			query.setParameter("pki_doctor_qualif_master_id", pki_doctor_qualif_master_id);
			status = query.executeUpdate();
		} catch (Exception e) {
			status = 0;
			logger.error("Error in deleteDoctorQualLink method "+ e.getMessage());
			
		}
		return status;
	}

}
