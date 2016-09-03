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
@Table(name = "milk_production",schema = "farm")
public class MilkProductionEntity {
	
	@Id
    @Column(name="milk_prod_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer milkprodid;
    
	@OneToOne
	@JoinColumn(name = "milk_prod_doe_id")
    private CattleEntity objdoe;
    
    @Column(name="milk_prod_dt")
    private Timestamp milkproddt;
    
    @Column(name="milk_prod_qty")
    private double milkprodqty;
    
    @Column(name="milk_prod_comments")
    private String milkprodcomments;
    
    @Column(name="created_dt")
    private Timestamp createddt;
	
	@Column(name="updated_dt")
    private Timestamp updateddt;
	
	@Column(name="deleted_dt")
    private Timestamp deleteddt;
	
	@Column(name="unique_sync_key")
    private String uniquesynckey;

	public Integer getMilkprodid() {
		return milkprodid;
	}

	public void setMilkprodid(Integer milkprodid) {
		this.milkprodid = milkprodid;
	}

	public CattleEntity getObjdoe() {
		return objdoe;
	}

	public void setObjdoe(CattleEntity objdoe) {
		this.objdoe = objdoe;
	}

	public Timestamp getMilkproddt() {
		return milkproddt;
	}

	public void setMilkproddt(Timestamp milkproddt) {
		this.milkproddt = milkproddt;
	}

	public double getMilkprodqty() {
		return milkprodqty;
	}

	public void setMilkprodqty(double milkprodqty) {
		this.milkprodqty = milkprodqty;
	}

	public String getMilkprodcomments() {
		return milkprodcomments;
	}

	public void setMilkprodcomments(String milkprodcomments) {
		this.milkprodcomments = milkprodcomments;
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
