package com.inapp.cms.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "surgery",schema = "master")
public class SurgeryMasterEntity {

	@Id
	@Column(name="surgery_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer surgeryid;
	
	@Column(name ="surgery_name")
	private String surgeryname;
	
	@Column(name ="surgery_comments")
	private String surgerycomments;
	
	@Column(name="created_dt")
	private Timestamp createddt;
	
	@Column(name="updated_dt")
	private Timestamp updateddt;
	
	@Column(name="deleted_dt")
	private Timestamp deleteddt;
	
	@Column(name="unique_sync_key")
	private String uniquesynckey;
	
	@Column(name="surgery_status")
	private int surgerystatus;

	public Integer getSurgeryid() {
		return surgeryid;
	}

	public void setSurgeryid(Integer surgeryid) {
		this.surgeryid = surgeryid;
	}

	public String getSurgeryname() {
		return surgeryname;
	}

	public void setSurgeryname(String surgeryname) {
		this.surgeryname = surgeryname;
	}


	public String getSurgerycomments() {
		return surgerycomments;
	}

	public void setSurgerycomments(String surgerycomments) {
		this.surgerycomments = surgerycomments;
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

	public int getSurgerystatus() {
		return surgerystatus;
	}

	public void setSurgerystatus(int surgerystatus) {
		this.surgerystatus = surgerystatus;
	}
	
}
