package com.inapp.cms.entity;

import java.util.List;

import javax.persistence.Transient;



public class SyncEntity {
	private String lastSyncDt;

	private Integer userId;
	
	private String username;
	
	private List<String> farms;
	
	public String getLastSyncDt() {
		return lastSyncDt;
	}

	public void setLastSyncDt(String lastSyncDt) {
		this.lastSyncDt = lastSyncDt;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getFarms() {
		return farms;
	}

	public void setFarms(List<String> farms) {
		this.farms = farms;
	}
	
	
	
}
