package com.app.usermanagement.entity;

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
/**
 * @author Jinesh George
 */
@Entity
@Table(name = "owner",schema = "usermanagement")  
public class OwnerEntity {
     
    @Id
    @Column(name="owner_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
   
    @OneToOne
	@JoinColumn(name = "owner_user_id")
    private UserEntity objUserEntity;
    
    @Column(name="owner_status")
    private boolean ownerstatus = true;

    @Column(name="created_dt")
    private Timestamp createddt;
	
	@Column(name="updated_dt")
    private Timestamp updateddt;
	
	@Column(name="deleted_dt")
    private Timestamp deleteddt;
	
	@Column(name="unique_sync_key")
    private String uniquesynckey;
    
    @Transient
    private List<Integer> farmIds;
    
    @Transient
    private Integer ownerid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserEntity getObjUserEntity() {
		return objUserEntity;
	}

	public void setObjUserEntity(UserEntity objUserEntity) {
		this.objUserEntity = objUserEntity;
	}

	public boolean isOwnerstatus() {
		return ownerstatus;
	}

	public void setOwnerstatus(boolean ownerstatus) {
		this.ownerstatus = ownerstatus;
	}


	public List<Integer> getFarmIds() {
		return farmIds;
	}

	public void setFarmIds(List<Integer> farmIds) {
		this.farmIds = farmIds;
	}

	public Integer getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(Integer ownerid) {
		this.ownerid = ownerid;
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