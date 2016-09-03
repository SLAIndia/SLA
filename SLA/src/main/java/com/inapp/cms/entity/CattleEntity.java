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
@Table(name = "cattle",schema = "farm")
public class CattleEntity {

	@Id
	@Column(name="cattle_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer cattleid;
	
	@Column(name = "cattle_ear_tag_id")
	private String cattleeartagid;
	
	@Column(name = "cattle_location")
	private String cattlelocation;
	
	/*@Column(name = "cattle_farm_id")
	private int cattlefarmid;*/
	
   @OneToOne
   @JoinColumn(name = "cattle_farm_id")
	private FarmEntity farm;
	
/*	@Column(name = "cattle_dob") Timestamp
	private String cattledob;*/
	@Column(name = "cattle_dob") 
	private Timestamp cattledob;
	
	@Column(name = "cattle_gender")
	private String cattlegender;
	
	@Column(name = "cattle_category")
	private String cattlecategory;
	
	@Column(name = "cattle_status")
	private boolean cattlestatus;
	
	@Column(name = "cattle_code")
	private String cattlecode;
	
	/*@Column(name= "cattle_buck_id")
	private String cattlebuckid;
	
	@Column(name= "cattle_doe_id")
	private String cattledoeid;*/

	@Column(name= "cattle_created_dt")
	private Timestamp cattlecreateddt;
	
	@Column (name = "cattle_latt")
	private String cattlelatt;
	
	@Column (name = "cattle_long")
	private String cattlelong;
	
	@Column (name = "cattle_land_mark")
	private String cattlelandmark;
	
	@Column(name = "cattle_type")
	private Integer cattletype;
	
	@OneToOne
	@JoinColumn(name = "cattle_buck_id")
	private CattleEntity buckcattle;
	
	@OneToOne
	@JoinColumn(name = "cattle_doe_id")
	private CattleEntity doecattle;

	@Column(name="created_dt")
    private Timestamp createddt;
	
	@Column(name="updated_dt")
    private Timestamp updateddt;
	
	@Column(name="deleted_dt")
    private Timestamp deleteddt;
	
	@Column(name="unique_sync_key")
    private String uniquesynckey;
	
	public void setCattleid(Integer cattleid) {
		this.cattleid = cattleid;
	}

	public String getCattleeartagid() {
		return cattleeartagid;
	}

	public void setCattleeartagid(String cattleeartagid) {
		this.cattleeartagid = cattleeartagid;
	}

	

	public String getCattlelocation() {
		return cattlelocation;
	}

	public void setCattlelocation(String cattlelocation) {
		this.cattlelocation = cattlelocation;
	}

	/*public int getCattlefarmid() {
		return cattlefarmid;
	}

	public void setCattlefarmid(int cattlefarmid) {
		this.cattlefarmid = cattlefarmid;
	}*/



	public Timestamp getCattledob() {
		return cattledob;
	}

	public void setCattledob(Timestamp cattledob) {
		this.cattledob = cattledob;
	}

	public String getCattlegender() {
		return cattlegender;
	}

	public void setCattlegender(String cattlegender) {
		this.cattlegender = cattlegender;
	}

	public String getCattlecategory() {
		return cattlecategory;
	}

	public void setCattlecategory(String cattlecategory) {
		this.cattlecategory = cattlecategory;
	}

	
	
	public boolean isCattlestatus() {
		return cattlestatus;
	}

	public void setCattlestatus(boolean cattlestatus) {
		this.cattlestatus = cattlestatus;
	}

	/*public String getCattlebuckid() {
		return cattlebuckid;
	}

	public void setCattlebuckid(String cattlebuckid) {
		this.cattlebuckid = cattlebuckid;
	}

	public String getCattledoeid() {
		return cattledoeid;
	}

	public void setCattledoeid(String cattledoeid) {
		this.cattledoeid = cattledoeid;
	}*/

	public Integer getCattleid() {
		return cattleid;
	}
	

	public Timestamp getCattlecreateddt() {
		return cattlecreateddt;
	}

	public void setCattlecreateddt(Timestamp cattlecreateddt) {
		this.cattlecreateddt = cattlecreateddt;
	}

	public String getCattlelatt() {
		return cattlelatt;
	}

	public void setCattlelatt(String cattlelatt) {
		this.cattlelatt = cattlelatt;
	}

	public String getCattlelong() {
		return cattlelong;
	}

	public void setCattlelong(String cattlelong) {
		this.cattlelong = cattlelong;
	}

	public String getCattlelandmark() {
		return cattlelandmark;
	}

	public void setCattlelandmark(String cattlelandmark) {
		this.cattlelandmark = cattlelandmark;
	}

	public FarmEntity getFarm() {
		return farm;
	}

	public void setFarm(FarmEntity farm) {
		this.farm = farm;
	}

	public CattleEntity getBuckcattle() {
		return buckcattle;
	}

	public void setBuckcattle(CattleEntity buckcattle) {
		this.buckcattle = buckcattle;
	}

	public CattleEntity getDoecattle() {
		return doecattle;
	}

	public void setDoecattle(CattleEntity doecattle) {
		this.doecattle = doecattle;
	}

	public Integer getCattletype() {
		return cattletype;
	}

	public void setCattletype(Integer cattletype) {
		this.cattletype = 1; // Currently cattletype=1 is  set for goat 
		//this.cattletype = cattletype;
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

	public String getCattlecode() {
		return cattlecode;
	}

	public void setCattlecode(String cattlecode) {
		this.cattlecode = cattlecode;
	}


	
}
