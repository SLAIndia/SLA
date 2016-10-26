package com.app.usermanagement.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tbl_user", schema = "usermanagement")
public class UserEntity {

	@Id
	@Column(name = "pki_user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "fki_parent_user_id")
	private UserEntity userEntity;

	@OneToOne
	@JoinColumn(name = "fki_role_id")
	private RoleEntity role;

	@OneToOne
	@JoinColumn(name = "fki_user_type_id")
	private UserTypeEntity userType;

	@Column(name = "uvc_username")
	private String username;

	@Column(name = "vc_password")

	private String password;

	@Column(name = "dt_tem_password_dt")
	private Timestamp tempPasswordDt;

	@Column(name = "i_user_status")
	private int userStatus;

	@Column(name = "dt_created_date")
	private Timestamp createdDt;

	@Column(name = "dt_updated_dt")
	private Timestamp updatedDt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	public UserTypeEntity getUserType() {
		return userType;
	}

	public void setUserType(UserTypeEntity userType) {
		this.userType = userType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public Timestamp getTempPasswordDt() {
		return tempPasswordDt;
	}

	public void setTempPasswordDt(Timestamp tempPasswordDt) {
		this.tempPasswordDt = tempPasswordDt;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
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

}