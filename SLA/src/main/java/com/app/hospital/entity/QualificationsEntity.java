package com.app.hospital.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
/**
 * @author Jinesh George
 */
@Entity
@Table(name = "tbl_doctor_qualif_master",schema = "hospital")  
public class QualificationsEntity {

    @Id
    @Column(name="pki_doctor_qualif_master_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long pki_doctor_qualif_master_id;
    
    @Column(name="uvc_qualif_name")
    private String uvc_qualif_name;
    
    @Column(name="t_description")
    private String t_description;

	public Long getPki_doctor_qualif_master_id() {
		return pki_doctor_qualif_master_id;
	}

	public void setPki_doctor_qualif_master_id(Long pki_doctor_qualif_master_id) {
		this.pki_doctor_qualif_master_id = pki_doctor_qualif_master_id;
	}

	public String getUvc_qualif_name() {
		return uvc_qualif_name;
	}

	public void setUvc_qualif_name(String uvc_qualif_name) {
		this.uvc_qualif_name = uvc_qualif_name;
	}

	public String getT_description() {
		return t_description;
	}

	public void setT_description(String t_description) {
		this.t_description = t_description;
	}

}