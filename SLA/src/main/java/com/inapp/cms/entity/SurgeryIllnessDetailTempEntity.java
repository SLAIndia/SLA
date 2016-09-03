package com.inapp.cms.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "surg_ill_det_temp",schema = "farm")
public class SurgeryIllnessDetailTempEntity{

		@Id
		@Column(name="surg_ill_det_id")
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private Integer surgilldetid;
		
		@OneToOne
		@JoinColumn(name="surg_ill_det_surg_ill_id")
		private SurgeryIllnessEntity surgIllness;
		
		@Column(name="surg_ill_det_name")
		private String surgilldetname;
		
		@Column(name="surg_ill_det_dose")
		private double surgilldetdose;
		
		@Column(name="surg_ill_det_dose_unit")
		private String surgilldetdoseunit;
		
		@Column(name="surg_ill_det_treat")
		private String surgilldettreat;

		@Column(name="created_dt")
	    private Timestamp createddt;
		
		@Column(name="updated_dt")
	    private Timestamp updateddt;
		
		@Column(name="deleted_dt")
	    private Timestamp deleteddt;
		
		@Column(name="unique_sync_key")
	    private String uniquesynckey;
		
		public Integer getSurgilldetid() {
			return surgilldetid;
		}

		public void setSurgilldetid(Integer surgilldetid) {
			this.surgilldetid = surgilldetid;
		}

		public SurgeryIllnessEntity getSurgIllness() {
			return surgIllness;
		}

		public void setSurgIllness(SurgeryIllnessEntity surgIllness) {
			this.surgIllness = surgIllness;
		}

		public String getSurgilldetname() {
			return surgilldetname;
		}

		public void setSurgilldetname(String surgilldetname) {
			this.surgilldetname = surgilldetname;
		}

		public double getSurgilldetdose() {
			return surgilldetdose;
		}

		public void setSurgilldetdose(double surgilldetdose) {
			this.surgilldetdose = surgilldetdose;
		}

		public String getSurgilldetdoseunit() {
			return surgilldetdoseunit;
		}

		public void setSurgilldetdoseunit(String surgilldetdoseunit) {
			this.surgilldetdoseunit = surgilldetdoseunit;
		}

		public String getSurgilldettreat() {
			return surgilldettreat;
		}

		public void setSurgilldettreat(String surgilldettreat) {
			this.surgilldettreat = surgilldettreat;
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
		
}
