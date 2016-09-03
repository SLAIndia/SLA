package com.inapp.cms.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "vaccination",schema = "master")
public class VaccinationEntity {
	
	@Id
    @Column(name="vaccination_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer vaccinationid;
    
    @Column(name="vaccination_name")
    private String vaccinationname;
   
   /* @Column(name="vaccination_exp_dt")
    private Timestamp vaccinationexpdt;*/
    
    @Column(name="vaccination_dose")
    private double vaccinationdose;
    
    @Column(name="vaccination_unit")
    private String vaccinationunit;
    
    @Column(name="vaccination_type")
    private String vaccinationtype;

    @Column(name="vaccination_details")
    private String vaccinationdetails;
    
    @Column(name="vaccination_status")
    private int vaccinationstatus  = 1;
    @Column(name="created_dt")
    private Timestamp createddt;
	
	@Column(name="updated_dt")
    private Timestamp updateddt;
	
	@Column(name="deleted_dt")
    private Timestamp deleteddt;
	
	@Column(name="unique_sync_key")
    private String uniquesynckey;
    
    @Transient
    private boolean exist = false;
    
	public Integer getVaccinationid() {
		return vaccinationid;
	}

	public void setVaccinationid(Integer vaccinationid) {
		this.vaccinationid = vaccinationid;
	}

	public double getVaccinationdose() {
		return vaccinationdose;
	}

	public void setVaccinationdose(double vaccinationdose) {
		this.vaccinationdose = vaccinationdose;
	}

	public String getVaccinationunit() {
		return vaccinationunit;
	}

	public void setVaccinationunit(String vaccinationunit) {
		this.vaccinationunit = vaccinationunit;
	}

	public String getVaccinationname() {
		return vaccinationname;
	}

	public void setVaccinationname(String vaccinationname) {
		this.vaccinationname = vaccinationname;
	}

	/*public Timestamp getVaccinationexpdt() {
		return vaccinationexpdt;
	}

	public void setVaccinationexpdt(Timestamp vaccinationexpdt) {
		this.vaccinationexpdt = vaccinationexpdt;
	}*/

	public String getVaccinationtype() {
		return vaccinationtype;
	}

	public void setVaccinationtype(String vaccinationtype) {
		this.vaccinationtype = vaccinationtype;
	}

	public String getVaccinationdetails() {
		return vaccinationdetails;
	}

	public void setVaccinationdetails(String vaccinationdetails) {
		this.vaccinationdetails = vaccinationdetails;
	}

	public int getVaccinationstatus() {
		return vaccinationstatus;
	}

	public void setVaccinationstatus(int vaccinationstatus) {
		this.vaccinationstatus = vaccinationstatus;
	}

	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}

	

	public Timestamp getCreateddt() {
		return createddt;
	}

	public void setCreateddt(Timestamp createddt) {
		this.createddt = createddt;
	}

	public Timestamp getUpdateddt() {
		return updateddt;
	}

	public void setUpdateddt(Timestamp updateddt) {
		this.updateddt = updateddt;
	}

	public Timestamp getDeleteddt() {
		return deleteddt;
	}

	public void setDeleteddt(Timestamp deleteddt) {
		this.deleteddt = deleteddt;
	}

	public String getUniquesynckey() {
		return uniquesynckey;
	}

	public void setUniquesynckey(String uniquesynckey) {
		this.uniquesynckey = uniquesynckey;
	}

	
	
}
