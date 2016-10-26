package com.app.usermanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.app.master.entity.CountryEntity;
import com.app.master.entity.StateEntity;

@Entity
@Table(name = "tbl_address", schema = "usermanagement")
public class UserAddressEntity {

	@Id
	@Column(name = "pki_address_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "fki_user_id")
	private UserEntity user;

	@Column(name = "vc_location")
	private String location;

	@Column(name = "vc_location_lattitude")
	private String lattitude;

	@Column(name = "vc_location_longitude")
	private String longitude;

	@Column(name = "i_location_buffer")
	private Integer locationBuffer;

	@Column(name = "vc_pin_code")
	private String pinCode;

	@Column(name = "i_is_primary")
	private Integer isPrimary;

	@ManyToOne
	@JoinColumn(name = "fki_state_id")
	private StateEntity state;

	@ManyToOne
	@JoinColumn(name = "fki_country_id")
	private CountryEntity country;

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLattitude() {
		return lattitude;
	}

	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Integer getLocationBuffer() {
		return locationBuffer;
	}

	public void setLocationBuffer(Integer locationBuffer) {
		this.locationBuffer = locationBuffer;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public Integer getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(Integer isPrimary) {
		this.isPrimary = isPrimary;
	}

	public StateEntity getState() {
		return state;
	}

	public void setState(StateEntity state) {
		this.state = state;
	}

	public CountryEntity getCountry() {
		return country;
	}

	public void setCountry(CountryEntity country) {
		this.country = country;
	}

}
