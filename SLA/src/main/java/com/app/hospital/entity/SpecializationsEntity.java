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
@Table(name = "tbl_hos_dept_type",schema = "hospital")  
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="pki_hos_dept_type_id")
public class SpecializationsEntity {

    @Id
    @Column(name="pki_hos_dept_type_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long pki_hos_dept_type_id;
    
    @Column(name="vc_hos_dept_type_name")
    private String vc_hos_dept_type_name;

 
	@Column(name="dt_updated_date")
    private Timestamp dt_updated_date;
	
	@OneToOne
	@JoinColumn(name = "fki_parent_dept_type_id")
	private SpecializationsEntity objSpecializationsParent;

	public Long getPki_hos_dept_type_id() {
		return pki_hos_dept_type_id;
	}

	public void setPki_hos_dept_type_id(Long pki_hos_dept_type_id) {
		this.pki_hos_dept_type_id = pki_hos_dept_type_id;
	}

	public String getVc_hos_dept_type_name() {
		return vc_hos_dept_type_name;
	}

	public void setVc_hos_dept_type_name(String vc_hos_dept_type_name) {
		this.vc_hos_dept_type_name = vc_hos_dept_type_name;
	}

	public Timestamp getDt_updated_date() {
		return dt_updated_date;
	}

	public void setDt_updated_date(Timestamp dt_updated_date) {
		this.dt_updated_date = dt_updated_date;
	}

	public SpecializationsEntity getObjSpecializationsParent() {
		return objSpecializationsParent;
	}

	public void setObjSpecializationsParent(SpecializationsEntity objSpecializationsParent) {
		this.objSpecializationsParent = objSpecializationsParent;
	}

}