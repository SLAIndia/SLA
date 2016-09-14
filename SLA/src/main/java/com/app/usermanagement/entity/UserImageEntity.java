package com.app.usermanagement.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class UserImageEntity {

	@Id
	@Column(name = "pki_image_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "fki_user_id")
	private UserEntity user;

	@Column(name = "vc_image_url")
	private String imgUrl;

	@Column(name = "b_is_primary")
	private boolean isPrimary;

	@Column(name = "i_image_order")
	private boolean imgOrder;

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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public boolean isImgOrder() {
		return imgOrder;
	}

	public void setImgOrder(boolean imgOrder) {
		this.imgOrder = imgOrder;
	}

}
