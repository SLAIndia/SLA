package com.inapp.cms.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "healthcare",schema = "farm")
public class HealthCareEntity {	

	@Id
    @Column(name="healthcare_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer healthcareid;
    
	@OneToOne	
  	@JoinColumn(name = "healthcare_cattle_id")
	private CattleEntity objCattleEntity; 
	 
	@Column(name = "healthcare_vet_id")
	private Integer vetid; 
	
	
    @Column(name="healthcare_medication_given")
    private String medicationgiven;
    
    @Column(name="healthcare_vet_sign")
    private String vetsign;

    @Column(name="healthcare_service_dt")
    private Timestamp servicedate;

    @Column(name="created_dt")
    private Timestamp createddt;
	
	@Column(name="updated_dt")
    private Timestamp updateddt;
	
	@Column(name="deleted_dt")
    private Timestamp deleteddt;
	
	@Column(name="unique_sync_key")
    private String uniquesynckey;
    
	public Integer getHealthcareid() {
		return healthcareid;
	}

	public void setHealthcareid(Integer healthcareid) {
		this.healthcareid = healthcareid;
	}

	public CattleEntity getObjCattleEntity() {
		return objCattleEntity;
	}

	public void setObjCattleEntity(CattleEntity objCattleEntity) {
		this.objCattleEntity = objCattleEntity;
	}

	public Integer getVetid() {
		return vetid;
	}

	public void setVetid(Integer vetid) {
		this.vetid = vetid;
	}

	public String getMedicationgiven() {
		return medicationgiven;
	}

	public void setMedicationgiven(String medicationgiven) {
		this.medicationgiven = medicationgiven;
	}

	public String getVetsign() {
		return vetsign;
	}

	public void setVetsign(String vetsign) {
		this.vetsign = vetsign;
	}

	public Timestamp getServicedate() {
		return servicedate;
	}

	public void setServicedate(Timestamp servicedate) {
		this.servicedate = servicedate;
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
