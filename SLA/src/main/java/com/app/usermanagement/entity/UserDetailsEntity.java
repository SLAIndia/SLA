package com.app.usermanagement.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.app.master.entity.CountryEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "tbl_user_details", schema = "usermanagement")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetailsEntity {

	@Id
	@Column(name = "pki_user_det_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "fki_user_id")
	private UserEntity user;

	@ManyToOne
	@JoinColumn(name = "fki_country_id")
	private CountryEntity country;

	@Column(name = "vc_f_name")
	private String fname;

	@Column(name = "vc_l_name")
	private String lname;

	@Column(name = "uvc_p_email_address")
	private String pEmail;

	@Column(name = "vc_s_email_address")
	private String sEmail;

	@Column(name = "vc_phone_1")
	private String phone1;

	@Column(name = "vc_phone_2")
	private String phone2;

	@Column(name = "vc_phone_3")
	private String phone3;

	@Column(name = "vc_device_token")
	private String deviceToken;

	@Column(name = "i_device_type")
	private Integer deviceType;

	@Column(name = "dt_created_date")
	private Timestamp createdDt;

	@Column(name = "dt_updated_dt")
	private Timestamp updatedDt;

	@Transient
	private List<UserAddressEntity> addresses;

	@JsonIgnore
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public CountryEntity getCountry() {
		return country;
	}

	public void setCountry(CountryEntity country) {
		this.country = country;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getpEmail() {
		return pEmail;
	}

	public void setpEmail(String pEmail) {
		this.pEmail = pEmail;
	}

	public String getsEmail() {
		return sEmail;
	}

	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	@JsonIgnore
	public Timestamp getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Timestamp createdDt) {
		this.createdDt = createdDt;
	}

	@JsonIgnore
	public Timestamp getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(Timestamp updatedDt) {
		this.updatedDt = updatedDt;
	}

	public List<UserAddressEntity> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<UserAddressEntity> addresses) {
		this.addresses = addresses;
	}

}
