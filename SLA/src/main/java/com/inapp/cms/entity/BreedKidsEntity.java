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
@Table(name = "breed_kids",schema = "farm")
public class BreedKidsEntity {
	
	@Id
    @Column(name="breed_kids_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @OneToOne
   	@JoinColumn(name="breed_kids_breeding_id")
    private BreedingEntity objBreedingEntity;
    
    @Column(name="breed_kids_cattle_id")
    private int breedkidscattleid;
    
    @Column(name="breed_kids_nur_lact_dt")
    private Timestamp breedkidsnurlactdt;
    
    @Column(name="breed_kids_weaning_dt")
    private Timestamp breedkidsweaningdt;
    
    @Column(name="breed_kids_weight")
    private double breedkidsweight;
    
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

	public BreedingEntity getObjBreedingEntity() {
		return objBreedingEntity;
	}

	public void setObjBreedingEntity(BreedingEntity objBreedingEntity) {
		this.objBreedingEntity = objBreedingEntity;
	}

	public int getBreedkidscattleid() {
		return breedkidscattleid;
	}

	public void setBreedkidscattleid(int breedkidscattleid) {
		this.breedkidscattleid = breedkidscattleid;
	}

	public Timestamp getBreedkidsnurlactdt() {
		return breedkidsnurlactdt;
	}

	public void setBreedkidsnurlactdt(Timestamp breedkidsnurlactdt) {
		this.breedkidsnurlactdt = breedkidsnurlactdt;
	}

	public Timestamp getBreedkidsweaningdt() {
		return breedkidsweaningdt;
	}

	public void setBreedkidsweaningdt(Timestamp breedkidsweaningdt) {
		this.breedkidsweaningdt = breedkidsweaningdt;
	}

	public double getBreedkidsweight() {
		return breedkidsweight;
	}

	public void setBreedkidsweight(double breedkidsweight) {
		this.breedkidsweight = breedkidsweight;
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
