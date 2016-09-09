package com.app.hospital.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.app.usermanagement.entity.RoleEntity;
/**
 * @author Jinesh George
 */
@Entity
@Table(name = "user",schema = "usermanagement")  
public class HospitalEntity {
    
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name="user_name")
    private String username;
 
    @Column(name="user_password")
    private String password;

	@Column(name="user_isactive")
    private boolean isactive = true;
	
	
	@Column(name="user_created_dt")
    private Timestamp usercreateddt;
	
	@Column(name="user_temp_password")
    private String temppassword;
	
	@Column(name="user_fname")
    private String userfname;
	
	
	@Column(name="user_lname")
    private String userlname;
	
	@Column(name="user_location")
    private String userlocation;
	
	@Column(name="user_email")
    private String useremail;
	
	@Column(name="user_address")
    private String useraddress;
	
	@Column(name="user_phone")
    private String userphone;
	
	@OneToOne
	@JoinColumn(name = "user_role_id")
	private RoleEntity objRoleEntity;
	
	@Column(name="created_dt")
    private Timestamp createddt;
	
	@Column(name="updated_dt")
    private Timestamp updateddt;
	
	@Column(name="deleted_dt")
    private Timestamp deleteddt;
	
	@Column(name="unique_sync_key")
    private String uniquesynckey;
	
	@Transient 
	private String role_name;
	
	@Transient 
	private String newpassword;
	
	@Transient
	private String platform = "web";
	
	
	public Timestamp getUser_updated_dt() {
		return user_updated_dt;
	}

	public void setUser_updated_dt(Timestamp user_updated_dt) {
		this.user_updated_dt = user_updated_dt;
	}

	@Column(name="user_updated_dt")
    private Timestamp user_updated_dt;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isIsactive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}
	
	
	public RoleEntity getObjRoleEntity() {
		return objRoleEntity;
	}


	public void setObjRoleEntity(RoleEntity objRoleEntity) {
		this.objRoleEntity = objRoleEntity;
	}
	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getTemppassword() {
		return temppassword;
	}

	public void setTemppassword(String temppassword) {
		this.temppassword = temppassword;
	}

	public Timestamp getUsercreateddt() {
		return usercreateddt;
	}

	public void setUsercreateddt(Timestamp usercreateddt) {
		this.usercreateddt = usercreateddt;
	}

	public String getUserfname() {
		return userfname;
	}

	public void setUserfname(String userfname) {
		this.userfname = userfname;
	}

	public String getUserlname() {
		return userlname;
	}

	public void setUserlname(String userlname) {
		this.userlname = userlname;
	}

	public String getUserlocation() {
		return userlocation;
	}

	public void setUserlocation(String userlocation) {
		this.userlocation = userlocation;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public String getUseraddress() {
		return useraddress;
	}

	public void setUseraddress(String useraddress) {
		this.useraddress = useraddress;
	}

	public String getUserphone() {
		return userphone;
	}

	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
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

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
}