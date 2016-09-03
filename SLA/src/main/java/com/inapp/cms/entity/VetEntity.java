package com.inapp.cms.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "vet",schema = "usermanagement")
public class VetEntity {
	
	@Id
    @Column(name="vet_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer vetid;

    @Column(name="vet_type")
    private int vettype;

	@Column(name="vet_status")
    private int vetStatus;

	@OneToOne
	@JoinColumn(name = "vet_user_id")
	private UserEntity objUserEntity ; 
	
    @Column(name="vet_speciality")
    private String vetspeciality;
    
    @Column(name="created_dt")
    private Timestamp createddt;
	
	@Column(name="updated_dt")
    private Timestamp updateddt;
	
	@Column(name="deleted_dt")
    private Timestamp deleteddt;
	
	@Column(name="unique_sync_key")
    private String uniquesynckey;
    
    @Transient
    private String vetfnamelname;
    
    @Transient
    private List<Integer> farmIds;
	
	public Integer getVetid() {
		return vetid;
	}

	public void setVetid(Integer vetid) {
		this.vetid = vetid;
	}

	public int getVettype() {
		return vettype;
	}

	public void setVettype(int vettype) {
		this.vettype = vettype;
	}


	public String getVetspeciality() {
		return vetspeciality;
	}

	public void setVetspeciality(String vetspeciality) {
		this.vetspeciality = vetspeciality;
	}

	public UserEntity getObjUserEntity() {
		return objUserEntity;
	}

	public void setObjUserEntity(UserEntity objUserEntity) {
		this.objUserEntity = objUserEntity;
	}

	public int getVetStatus() {
		return vetStatus;
	}

	public void setVetStatus(int vetStatus) {
		this.vetStatus = vetStatus;
	}

	public String getVetfnamelname() {
		return vetfnamelname;
	}

	public void setVetfnamelname(String vetfnamelname) {
		this.vetfnamelname = vetfnamelname;
	}

	public List<Integer> getFarmIds() {
		return farmIds;
	}

	public void setFarmIds(List<Integer> farmIds) {
		this.farmIds = farmIds;
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
