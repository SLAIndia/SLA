package com.app.json;

import java.sql.Date;
import java.sql.Time;

public class LocationHistoryJson {

	
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getClientuserid() {
		return clientuserid;
	}
	public void setClientuserid(int clientuserid) {
		this.clientuserid = clientuserid;
	}
	public double getLattitude() {
		return lattitude;
	}
	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Time getTrackedtime() {
		return trackedtime;
	}
	public void setTrackedtime(Time trackedtime) {
		this.trackedtime = trackedtime;
	}
	public Date getTrackeddate() {
		return trackeddate;
	}
	public void setTrackeddate(Date trackeddate) {
		this.trackeddate = trackeddate;
	}
	public String getTrackeddatetime() {
		return trackeddatetime;
	}
	public void setTrackeddatetime(String trackeddatetime) {
		this.trackeddatetime = trackeddatetime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}

	// client user id is the client id of user from client table
	private int clientuserid;
	private double lattitude;
	private double longitude;
	private Time trackedtime;
	private Date trackeddate;
	private String trackeddatetime;
	private String description;
	//user id of the user details.
	private int userid;
	private String signature;

}