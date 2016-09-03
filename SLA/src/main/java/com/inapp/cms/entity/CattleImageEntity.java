package com.inapp.cms.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "cattle_image",schema = "farm")
public class CattleImageEntity {

		@Id
		@Column(name="cattle_image_id")
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private int cattleimageid;
		
		@Column(name="cattle_image_url")
		private String cattleimageurl;
		
		@Column(name="cattle_image_is_primary")
		private boolean cattleimageisprimary;
		
		@Column(name="cattle_image_cattle_id")
		private int cattleimagecattleid;
		
		@Column(name="created_dt")
	    private Timestamp createddt;
		
		@Column(name="updated_dt")
	    private Timestamp updateddt;
		
		@Column(name="deleted_dt")
	    private Timestamp deleteddt;
		
		@Column(name="unique_sync_key")
	    private String uniquesynckey;
		
		@Column(name="img_status")
		private int imgstatus = 1; 

		@Transient
		private String cattle_ear_tag;
		
		public int getCattleimageid() {
			return cattleimageid;
		}

		public void setCattleimageid(int cattleimageid) {
			this.cattleimageid = cattleimageid;
		}

		public String getCattleimageurl() {
			return cattleimageurl;
		}

		public void setCattleimageurl(String cattleimageurl) {
			this.cattleimageurl = cattleimageurl;
		}

		public boolean isCattleimageisprimary() {
			return cattleimageisprimary;
		}

		public void setCattleimageisprimary(boolean cattleimageisprimary) {
			this.cattleimageisprimary = cattleimageisprimary;
		}

		public int getCattleimagecattleid() {
			return cattleimagecattleid;
		}

		public void setCattleimagecattleid(int cattleimagecattleid) {
			this.cattleimagecattleid = cattleimagecattleid;
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

		public String getCattle_ear_tag() {
			return cattle_ear_tag;
		}

		public void setCattle_ear_tag(String cattle_ear_tag) {
			this.cattle_ear_tag = cattle_ear_tag;
		}

		public int getImgstatus() {
			return imgstatus;
		}

		public void setImgstatus(int imgstatus) {
			this.imgstatus = imgstatus;
		}
	
}
