package com.inapp.cms.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vacc_hc_link",schema = "farm")
public class HealthcareVaccLinkEntity {
	@Id
    @Column(name="vacc_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer vaccid;
    
	
	@OneToOne
	@JoinColumn(name = "vacc_healthcare_id")
    private HealthCareEntity objHealthCareEntity;
   
    @Column(name="vacc_dose")
    private double vaccdose;
    
    @Column(name="vacc_unit")
    private String vaccunit;
    
    @Column(name="vacc_vaccination_dt")
    private Timestamp vaccvaccinationdt;
    
    @Column(name="vacc_next_vacc_dt")
    private Timestamp vaccnextvaccdt;

    @Column(name="vacc_hc_type")
    private int vacchctype;
    
    @Column(name="vacc_routine")
    private String vaccroutine;
    
    @Column(name="vacc_hc_vet_id")
    private Integer vacchcvetid;
    
    @OneToOne
	@JoinColumn(name = "vacc_vaccination_id")
    private VaccinationEntity objVaccinationEntity;
    
    @Column(name="vacc_hc_type_comments")
    private String vacchctypecomments;

    @Column(name="created_dt")
    private Timestamp createddt;
	
	@Column(name="updated_dt")
    private Timestamp updateddt;
	
	@Column(name="deleted_dt")
    private Timestamp deleteddt;
	
	@Column(name="unique_sync_key")
    private String uniquesynckey;
    
	public Integer getVaccid() {
		return vaccid;
	}

	public void setVaccid(Integer vaccid) {
		this.vaccid = vaccid;
	}

	

	public HealthCareEntity getObjHealthCareEntity() {
		return objHealthCareEntity;
	}

	public void setObjHealthCareEntity(HealthCareEntity objHealthCareEntity) {
		this.objHealthCareEntity = objHealthCareEntity;
	}

	public double getVaccdose() {
		return vaccdose;
	}

	public void setVaccdose(double vaccdose) {
		this.vaccdose = vaccdose;
	}


	public String getVaccunit() {
		return vaccunit;
	}

	public void setVaccunit(String vaccunit) {
		this.vaccunit = vaccunit;
	}


	public String getVaccroutine() {
		return vaccroutine;
	}

	public void setVaccroutine(String vaccroutine) {
		this.vaccroutine = vaccroutine;
	}

	public Timestamp getVaccvaccinationdt() {
		return vaccvaccinationdt;
	}

	public void setVaccvaccinationdt(Timestamp vaccvaccinationdt) {
		this.vaccvaccinationdt = vaccvaccinationdt;
	}

	public Timestamp getVaccnextvaccdt() {
		return vaccnextvaccdt;
	}

	public void setVaccnextvaccdt(Timestamp vaccnextvaccdt) {
		this.vaccnextvaccdt = vaccnextvaccdt;
	}

	public int getVacchctype() {
		return vacchctype;
	}

	public void setVacchctype(int vacchctype) {
		this.vacchctype = vacchctype;
	}

	public String getVacchctypecomments() {
		return vacchctypecomments;
	}

	public void setVacchctypecomments(String vacchctypecomments) {
		this.vacchctypecomments = vacchctypecomments;
	}

	public VaccinationEntity getObjVaccinationEntity() {
		return objVaccinationEntity;
	}

	public void setObjVaccinationEntity(VaccinationEntity objVaccinationEntity) {
		this.objVaccinationEntity = objVaccinationEntity;
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

	public Integer getVacchcvetid() {
		return vacchcvetid;
	}

	public void setVacchcvetid(Integer vacchcvetid) {
		this.vacchcvetid = vacchcvetid;
	}
    
	
}
