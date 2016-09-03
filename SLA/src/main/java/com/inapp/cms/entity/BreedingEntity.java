package com.inapp.cms.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.app.utils.Common;

@Entity
@Table(name = "breeding",schema = "farm")
public class BreedingEntity {
	
	@Id
    @Column(name="breeding_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer breedingid;
    
    @Column(name="breeding_buck_id")
    private int breedingbuckid;
   
    @Column(name="breeding_doe_id")
    private int breedingdoeid;
    
    @Column(name="breeding_date")
    private Timestamp breedingdate;
    
    @Column(name="breeding_village")
    private String breedingvillage;
    
    @Column(name="breeding_aborted_dt")
    private Timestamp breedingaborted_dt;
    
    @Column(name="breeding_kids_no")
    private int breedingkidsno;
    
    @Column(name="breeding_aborted_no")
    private int breedingabortedno;
    
    @Column(name="breeding_lact_start_dt")
    private Timestamp breedinglactstartdt;

    @Column(name="breeding_lact_end_dt")
    private Timestamp breedinglactenddt;
    
   
    @Column(name="created_dt")
    private Timestamp createddt;
	
	@Column(name="updated_dt")
    private Timestamp updateddt;
	
	@Column(name="deleted_dt")
    private Timestamp deleteddt;
	
	@Column(name="unique_sync_key")
    private String uniquesynckey;
	
	@Column(name="breeding_doe_ear_tag")
	private String breedingdoeeartag;
	
	@Column(name="breeding_buck_ear_tag")
	private String breedingbuckeartag;

    @Transient
    private int breedkidid;
    
	public Integer getBreedingid() {
		return breedingid;
	}

	public void setBreedingid(Integer breedingid) {
		this.breedingid = breedingid;
	}

	public int getBreedingbuckid() {
		return breedingbuckid;
	}

	public void setBreedingbuckid(int breedingbuckid) {
		this.breedingbuckid = breedingbuckid;
	}

	public int getBreedingdoeid() {
		return breedingdoeid;
	}

	public void setBreedingdoeid(int breedingdoeid) {
		this.breedingdoeid = breedingdoeid;
	}

	public Timestamp getBreedingdate() {
		return breedingdate;
	}

	public void setBreedingdate(Timestamp breedingdate) {
		this.breedingdate = breedingdate;
	}

	public String getBreedingvillage() {
		return breedingvillage;
	}

	public void setBreedingvillage(String breedingvillage) {
		this.breedingvillage = breedingvillage;
	}

	public Timestamp getBreedingaborted_dt() {
		return breedingaborted_dt;
	}

	public void setBreedingaborted_dt(Timestamp breedingaborted_dt) {
		this.breedingaborted_dt = breedingaborted_dt;
	}

	public int getBreedingkidsno() {
		return breedingkidsno;
	}

	public void setBreedingkidsno(int breedingkidsno) {
		this.breedingkidsno = breedingkidsno;
	}

	public int getBreedingabortedno() {
		return breedingabortedno;
	}

	public void setBreedingabortedno(int breedingabortedno) {
		this.breedingabortedno = breedingabortedno;
	}

	public Timestamp getBreedinglactstartdt() {
		return breedinglactstartdt;
	}

	public void setBreedinglactstartdt(Timestamp breedinglactstartdt) {
		this.breedinglactstartdt = breedinglactstartdt;
	}

	public Timestamp getBreedinglactenddt() {
		return breedinglactenddt;
	}

	public void setBreedinglactenddt(Timestamp breedinglactenddt) {
		this.breedinglactenddt = breedinglactenddt;
	}

	public int getBreedkidid() {
		return breedkidid;
	}

	public void setBreedkidid(int breedkidid) {
		this.breedkidid = breedkidid;
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

	public String getBreedingdoeeartag() {
		return breedingdoeeartag;
	}

	public void setBreedingdoeeartag(String breedingdoeeartag) {
		this.breedingdoeeartag = breedingdoeeartag;
	}

	public String getBreedingbuckeartag() {
		return breedingbuckeartag;
	}

	public void setBreedingbuckeartag(String breedingbuckeartag) {
		this.breedingbuckeartag = breedingbuckeartag;
	}


}
