package com.inapp.cms.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "surgery_illness",schema = "farm")
public class SurgeryIllnessEntity {

		@Id
		@Column(name="surg_ill_id")
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private Integer surgillid;
		
		@OneToOne
		@JoinColumn(name="surg_ill_cattle_id")
		private CattleEntity cattle;
		
		@OneToOne
		@JoinColumn(name="surg_ill_vet_id")
		private VetEntity vet;
		
		@Column(name="surg_ill_proc_dt")
		private Timestamp surgillprocdt;
		
		@Column(name="surg_ill_weight")
		private double surgillweight;
		
		@Column(name="surg_ill_temp")
		private String surgilltemp;
		
		@Column(name="surg_ill_symp")
		private String surgillsymp;
		
		@Column(name="surg_ill_assess")
		private String surgillassess;
		
		@Column(name="surg_ill_vet_sign")
		private String surgillvetsign;
		
		@Column(name="surg_ill_surgery_id")
		private Integer surgillsurgeryid;

		@Column(name="created_dt")
	    private Timestamp createddt;
		
		@Column(name="updated_dt")
	    private Timestamp updateddt;
		
		@Column(name="deleted_dt")
	    private Timestamp deleteddt;
		
		@Column(name="unique_sync_key")
	    private String uniquesynckey;
		
		@Transient
		private List<SurgeryIllnessDetailEntity> listDetails;
		
		public Integer getSurgillid() {
			return surgillid;
		}

		public void setSurgillid(Integer surgillid) {
			this.surgillid = surgillid;
		}

		public CattleEntity getCattle() {
			return cattle;
		}

		public void setCattle(CattleEntity cattle) {
			this.cattle = cattle;
		}

		public VetEntity getVet() {
			return vet;
		}

		public void setVet(VetEntity vet) {
			this.vet = vet;
		}

		public Timestamp getSurgillprocdt() {
			return surgillprocdt;
		}

		public void setSurgillprocdt(Timestamp surgillprocdt) {
			this.surgillprocdt = surgillprocdt;
		}

		public double getSurgillweight() {
			return surgillweight;
		}

		public void setSurgillweight(double surgillweight) {
			this.surgillweight = surgillweight;
		}

		public String getSurgilltemp() {
			return surgilltemp;
		}

		public void setSurgilltemp(String surgilltemp) {
			this.surgilltemp = surgilltemp;
		}

		public String getSurgillsymp() {
			return surgillsymp;
		}

		public void setSurgillsymp(String surgillsymp) {
			this.surgillsymp = surgillsymp;
		}

		public String getSurgillassess() {
			return surgillassess;
		}

		public void setSurgillassess(String surgillassess) {
			this.surgillassess = surgillassess;
		}

		public String getSurgillvetsign() {
			return surgillvetsign;
		}

		public void setSurgillvetsign(String surgillvetsign) {
			this.surgillvetsign = surgillvetsign;
		}

		public int getSurgillsurgeryid() {
			return surgillsurgeryid;
		}

		public void setSurgillsurgeryid(int surgillsurgeryid) {
			this.surgillsurgeryid = surgillsurgeryid;
		}

		public List<SurgeryIllnessDetailEntity> getListDetails() {
			return listDetails;
		}

		public void setListDetails(List<SurgeryIllnessDetailEntity> listDetails) {
			this.listDetails = listDetails;
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

		public void setSurgillsurgeryid(Integer surgillsurgeryid) {
			this.surgillsurgeryid = surgillsurgeryid;
		}
		
}
