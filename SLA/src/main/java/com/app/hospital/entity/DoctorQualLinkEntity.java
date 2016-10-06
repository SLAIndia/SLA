package com.app.hospital.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.app.usermanagement.entity.UserEntity;
/**
 * @author Jinesh George
 */
/**
 * @author Jinesh
 *
 */
@Entity
@Table(name = "tbl_doctor_qualif_link",schema = "hospital")  
public class DoctorQualLinkEntity {
	@Id
    @Column(name="pki_doctor_qualif_link_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long pki_doctor_qualif_link_id;
	  
    @ManyToOne
	@JoinColumn(name = "fki_doctor_qualif_master_id")
	private QualificationsEntity objQualificationsEntity;
    
    @ManyToOne
   	@JoinColumn(name = "fki_doctor_id")
   	private UserEntity objUserEntity;
    
    @Column(name="t_description")
    private String t_description;

	public Long getPki_doctor_qualif_link_id() {
		return pki_doctor_qualif_link_id;
	}

	public void setPki_doctor_qualif_link_id(Long pki_doctor_qualif_link_id) {
		this.pki_doctor_qualif_link_id = pki_doctor_qualif_link_id;
	}

	public QualificationsEntity getObjQualificationsEntity() {
		return objQualificationsEntity;
	}

	public void setObjQualificationsEntity(
			QualificationsEntity objQualificationsEntity) {
		this.objQualificationsEntity = objQualificationsEntity;
	}

	public UserEntity getObjUserEntity() {
		return objUserEntity;
	}

	public void setObjUserEntity(UserEntity objUserEntity) {
		this.objUserEntity = objUserEntity;
	}

	public String getT_description() {
		return t_description;
	}

	public void setT_description(String t_description) {
		this.t_description = t_description;
	}
    
    

}