package com.inapp.cms.entity;

import java.sql.Timestamp;

public class ReportEntity {
	
	
	private Timestamp fromDt;
	private Timestamp toDt;
	private int cattleId;
	private int vaccineType;
	private int farmId;
	private int userId;
	private String type;
	
	public Timestamp getFromDt() {
		return fromDt;
	}
	public void setFromDt(Timestamp fromDt) {
		this.fromDt = fromDt;
	}
	public Timestamp getToDt() {
		return toDt;
	}
	public void setToDt(Timestamp toDt) {
		this.toDt = toDt;
	}
	public int getCattleId() {
		return cattleId;
	}
	public void setCattleId(int cattleId) {
		this.cattleId = cattleId;
	}
	public int getVaccineType() {
		return vaccineType;
	}
	public void setVaccineType(int vaccineType) {
		this.vaccineType = vaccineType;
	}
	public int getFarmId() {
		return farmId;
	}
	public void setFarmId(int farmId) {
		this.farmId = farmId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
