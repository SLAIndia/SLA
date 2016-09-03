package com.inapp.cms.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Jinesh George
 */
@Entity
@Table(name ="farm",schema = "farm")  
public class FarmEntity {
    
	@Id
    @Column(name="farm_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer farmid;
	
    @Column(name="farm_name")
    private String farmname;
    
    @Column(name="farm_address")
    private String farmaddress;
    
    @Column(name="farm_location")
    private String farmlocation;
    
    @Column(name="farm_status")
	private boolean farmstatus;
    
    @Column(name="farm_code")
    private String farmcode;
	
	@Column(name ="farm_created_dt")
	private Timestamp farmcreateddt;
    
	@Column(name="created_dt")
    private Timestamp createddt;
	
	@Column(name="updated_dt")
    private Timestamp updateddt;
	
	@Column(name="deleted_dt")
    private Timestamp deleteddt;
	
	@Column(name="unique_sync_key")
    private String uniquesynckey;
	
	public Integer getFarmid() {
		return farmid;
	}
	public void setFarmid(Integer farmid) {
		this.farmid = farmid;
	}
	public String getFarmname() {
		return farmname;
	}
	public void setFarmname(String farmname) {
		this.farmname = farmname;
	}
	public String getFarmaddress() {
		return farmaddress;
	}
	public void setFarmaddress(String farmaddress) {
		this.farmaddress = farmaddress;
	}
	public String getFarmlocation() {
		return farmlocation;
	}
	public void setFarmlocation(String farmlocation) {
		this.farmlocation = farmlocation;
	}
	public boolean isFarmstatus() {
		return farmstatus;
	}
	public void setFarmstatus(boolean farmstatus) {
		this.farmstatus = farmstatus;
	}
	
	
	public String getFarmcode() {
		return farmcode;
	}
	public void setFarmcode(String farmcode) {
		this.farmcode = farmcode;
	}


	public Timestamp getFarmcreateddt() {
		return farmcreateddt;
	}
	public void setFarmcreateddt(Timestamp farmcreateddt) {
		this.farmcreateddt = farmcreateddt;
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