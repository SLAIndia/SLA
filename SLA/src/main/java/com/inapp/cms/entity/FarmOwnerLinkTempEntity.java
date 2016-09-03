package com.inapp.cms.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "farm_owner_link_temp",schema = "farm")
public class FarmOwnerLinkTempEntity {
	
	@Id
    @Column(name="farm_owner_link_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name="farm_owner_link_farm_id")
    private int farmid;
 
	@Column(name="farm_owner_link_owner_id")
    private int ownerid;
	
	@Column(name="created_dt")
    private Timestamp createddt;
	
	@Column(name="updated_dt")
    private Timestamp updateddt;
	
	@Column(name="deleted_dt")
    private Timestamp deleteddt;
	
	@Column(name="unique_sync_key")
    private String uniquesynckey;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getFarmid() {
		return farmid;
	}

	public void setFarmid(int farmid) {
		this.farmid = farmid;
	}

	public int getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(int ownerid) {
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