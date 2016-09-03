package com.inapp.cms.entity;

import java.sql.Timestamp;


public class CattleSyncEntity {


	private Integer cattle_id;
	

	private String cattle_ear_tag_id;
	

	private String cattle_location;
	

	private String cattle_farm_code;
	
	private String cattle_dob;

	private String cattle_gender;
	

	private String cattle_category;
	

	private boolean cattle_status;
	

	private String cattle_code;
	

	private Timestamp cattle_created_dt;
	

	private String cattle_latt;
	

	private String cattle_long;
	

	private String cattle_land_mark;
	

	private Integer cattle_type;
	

	private String doe_ear_tag_id;
	

	private String buck_ear_tag_id;


    private Timestamp created_dt;

    private Timestamp updated_dt;
	

    private Timestamp deleted_dt;

    private String unique_sync_key;
    
    private String cattle_dob_sync;

	public Integer getCattle_id() {
		return cattle_id;
	}

	public void setCattle_id(Integer cattle_id) {
		this.cattle_id = cattle_id;
	}

	public String getCattle_ear_tag_id() {
		return cattle_ear_tag_id;
	}

	public void setCattle_ear_tag_id(String cattle_ear_tag_id) {
		this.cattle_ear_tag_id = cattle_ear_tag_id;
	}

	public String getCattle_location() {
		return cattle_location;
	}

	public void setCattle_location(String cattle_location) {
		this.cattle_location = cattle_location;
	}

	

	public String getCattle_dob() {
		return cattle_dob;
	}

	public void setCattle_dob(String cattle_dob) {
		this.cattle_dob = cattle_dob;
	}

	public String getCattle_gender() {
		return cattle_gender;
	}

	public void setCattle_gender(String cattle_gender) {
		this.cattle_gender = cattle_gender;
	}

	public String getCattle_category() {
		return cattle_category;
	}

	public void setCattle_category(String cattle_category) {
		this.cattle_category = cattle_category;
	}

	public boolean isCattle_status() {
		return cattle_status;
	}

	public void setCattle_status(boolean cattle_status) {
		this.cattle_status = cattle_status;
	}

	public String getCattle_code() {
		return cattle_code;
	}

	public void setCattle_code(String cattle_code) {
		this.cattle_code = cattle_code;
	}

	public Timestamp getCattle_created_dt() {
		return cattle_created_dt;
	}

	public void setCattle_created_dt(Timestamp cattle_created_dt) {
		this.cattle_created_dt = cattle_created_dt;
	}

	public String getCattle_latt() {
		return cattle_latt;
	}

	public void setCattle_latt(String cattle_latt) {
		this.cattle_latt = cattle_latt;
	}

	public String getCattle_long() {
		return cattle_long;
	}

	public void setCattle_long(String cattle_long) {
		this.cattle_long = cattle_long;
	}

	public String getCattle_land_mark() {
		return cattle_land_mark;
	}

	public void setCattle_land_mark(String cattle_land_mark) {
		this.cattle_land_mark = cattle_land_mark;
	}

	public Integer getCattle_type() {
		return cattle_type;
	}

	public void setCattle_type(Integer cattle_type) {
		this.cattle_type = cattle_type;
	}

	public Timestamp getCreated_dt() {
		return created_dt;
	}

	public void setCreated_dt(Timestamp created_dt) {
		this.created_dt = created_dt;
	}

	public Timestamp getUpdated_dt() {
		return updated_dt;
	}

	public void setUpdated_dt(Timestamp updated_dt) {
		this.updated_dt = updated_dt;
	}

	public Timestamp getDeleted_dt() {
		return deleted_dt;
	}

	public void setDeleted_dt(Timestamp deleted_dt) {
		this.deleted_dt = deleted_dt;
	}

	public String getUnique_sync_key() {
		return unique_sync_key;
	}

	public void setUnique_sync_key(String unique_sync_key) {
		this.unique_sync_key = unique_sync_key;
	}
	

	public String getCattle_farm_code() {
		return cattle_farm_code;
	}

	public void setCattle_farm_code(String cattle_farm_code) {
		this.cattle_farm_code = cattle_farm_code;
	}

	public String getDoe_ear_tag_id() {
		return doe_ear_tag_id;
	}

	public void setDoe_ear_tag_id(String doe_ear_tag_id) {
		this.doe_ear_tag_id = doe_ear_tag_id;
	}

	public String getBuck_ear_tag_id() {
		return buck_ear_tag_id;
	}

	public void setBuck_ear_tag_id(String buck_ear_tag_id) {
		this.buck_ear_tag_id = buck_ear_tag_id;
	}

	public String getCattle_dob_sync() {
		return cattle_dob_sync;
	}

	public void setCattle_dob_sync(String cattle_dob_sync) {
		this.cattle_dob_sync = cattle_dob_sync;
	}

	
}
