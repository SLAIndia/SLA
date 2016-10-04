package com.app.hospital.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.app.usermanagement.entity.UserEntity;
/**
 * @author Jinesh George
 */
@Entity
@Table(name = "tbl_doctor_qualif_master",schema = "hospital")  
public class DoctorQualLinkEntity {
	@Id
    @Column(name="pki_doctor_qualif_link_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long pki_doctor_qualif_link_id;
	  
    @OneToMany
	@JoinColumn(name = "fki_doctor_qualif_master_id")
	private QualificationsEntity objQualificationsEntity;
    
    @OneToMany
   	@JoinColumn(name = "fki_doctor_id")
   	private UserEntity objUserEntity;

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
    
    

}